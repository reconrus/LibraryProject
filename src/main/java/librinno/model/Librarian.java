package main.java.librinno.model;

import javafx.scene.control.Alert;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * librarian of library
 */
public class Librarian extends User {
    /**
     * setter
     *
     * @param name - name of librarian
     * @param phone_number - number os librarian
     * @param adress - where librarian lives(librarian is user too)
     * @param card_number - id of librarian
     */
    //creating database for working with it
    private static Database db = new Database();

    /**
     * librarian extends user so it is user too
     *
     * @param name       librarian name
     * @param address    it's address
     * @param number     it's number
     * @param cardnumber it's id
     * @param type       it's type
     * @param password   it's password
     */
    public Librarian(String name, String address, String number, int cardnumber, String type, String password,String email) {
        super(name, address, number, cardnumber, type, password,email);
    }

    /**
     * all parametres are the same, but not with full information
     * because it will not be needed
     */
    public void Librarian(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.phoneNumber = phone_number;
        this.adress = adress;
        this.card_number = card_number;
    }

    public static void outstandingRequest(int idOfMaterial){

        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_original= " + idOfMaterial + " AND Status = 'Issued'");
            pr.setInt(1,0);
            pr.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
            pr.setBoolean(3,false);
            pr.executeUpdate();

            ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_original= " + idOfMaterial);
            int owner;
            while (rs.next()){
                owner = rs.getInt("Owner");
                Statement stmt = db.con.createStatement();
                ResultSet rs2 = stmt.executeQuery("SELECT  * FROM users_of_the_library WHERE Card_number= " + owner);
                String email = null;
                while (rs2.next()){
                    email=rs2.getString("Email");
                }
                SendEmail sendEmail = new SendEmail();
                sendEmail.sendToOne(email,"Return book.","Urgently return the book throughout the day! Because of outstanding request.");
            }

            pr.executeUpdate("DROP TABLE IF EXISTS queue_on_" + idOfMaterial);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean renew(User user,int idOfRenewCopy){
        int oldTimeLeft = 0;
        LocalDate oldReturnDate = null;
        boolean oldCanRenew=false;

        try{
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_copy= " + idOfRenewCopy + " AND CanRenew=" + true);
            ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_copy= " + idOfRenewCopy);
            while (rs.next()) {
                oldTimeLeft = rs.getInt("Time_left");
                oldReturnDate = rs.getDate("Return_date").toLocalDate();
                oldCanRenew = rs.getBoolean("CanRenew");
            }

            if (user.getType().equals("Visiting Professor")) {
                //если визитинг профессор, то он всегда все берет только на одну неделю
                pr.setInt(1,oldTimeLeft+7);
                pr.setDate(2,java.sql.Date.valueOf(oldReturnDate.plusDays(7)));
                pr.setBoolean(3,true);
                pr.executeUpdate();
                return true;

            } else if (oldCanRenew){//если это не ВизПроф, то чекаем обновлял ли он до этого(если обновлял то false, если может обновить, то true)
                Statement stmt = db.con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idOfRenewCopy);
                if (rs.next()) {
                    //если книга, то уже смотрим на тип юзера и книгу
                    if (user.getType().equals("Student") && rs.getBoolean("is_bestseller")){
                        pr.setInt(1,oldTimeLeft+14);
                        pr.setDate(2,java.sql.Date.valueOf(oldReturnDate.plusDays(14)));
                        pr.setBoolean(3,false);
                        pr.executeUpdate();
                        return true;

                    } else if(user.getType().equals("Student") && !rs.getBoolean("is_bestseller")){
                        pr.setInt(1,oldTimeLeft+21);
                        pr.setDate(2,java.sql.Date.valueOf(oldReturnDate.plusDays(21)));
                        pr.setBoolean(3,false);
                        pr.executeUpdate();
                        return true;
                    } else {
                        pr.setInt(1,oldTimeLeft+28);
                        pr.setDate(2,java.sql.Date.valueOf(oldReturnDate.plusDays(28)));
                        pr.setBoolean(3,false);
                        pr.executeUpdate();
                        return true;
                    }
                } else {
                    pr.setInt(1,oldTimeLeft+14);
                    pr.setDate(2,java.sql.Date.valueOf(oldReturnDate.plusDays(14)));
                    pr.setBoolean(3,false);
                    pr.executeUpdate();
                    return true;
                }


            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int fine(int idOfCopy){
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM Copy WHERE Id_of_copy= " + idOfCopy);
            while (rs.next()) {
                if (LocalDate.now().isAfter(rs.getDate("Return_date").toLocalDate())) {
                    int penaltyDays = (int) DAYS.between(rs.getDate("Return_date").toLocalDate(), LocalDate.now());
                    int fine = 0;
                    int price = 0;
                    int id_of_original_material = rs.getInt("Id_of_original");
                    ResultSet rs2 = stmt.executeQuery("SELECT * FROM Books WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    rs2 = stmt.executeQuery("SELECT * FROM av WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    rs2 = stmt.executeQuery("SELECT * FROM articles WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    while (fine < price && penaltyDays > 0) {
                        if (fine + 100 > price) {
                            fine = price;
                        } else
                            fine += 100;
                        penaltyDays--;
                    }

                    return fine;
                }
            }
            return 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int fineWithDate(int idOfCopy,LocalDate date){
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT  * FROM Copy WHERE Id_of_copy= " + idOfCopy);
            while (rs.next()) {
                if (date.isAfter(rs.getDate("Return_date").toLocalDate())) {
                    int penaltyDays = (int) DAYS.between(rs.getDate("Return_date").toLocalDate(), date);
                    int fine = 0;
                    int price = 0;
                    int id_of_original_material = rs.getInt("Id_of_original");
                    ResultSet rs2 = stmt.executeQuery("SELECT * FROM Books WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    rs2 = stmt.executeQuery("SELECT * FROM av WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    rs2 = stmt.executeQuery("SELECT * FROM articles WHERE id =" + id_of_original_material);
                    if (rs2.next()) {
                        price = rs2.getInt("Price");
                    }
                    while (fine < price && penaltyDays > 0) {
                        if (fine + 100 > price) {
                            fine = price;
                        } else
                            fine += 100;
                        penaltyDays--;
                    }

                    return fine;
                }
            }
            return 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * method for checking out books
     *
     * @param user     to whom give a book
     * @param idOfBook which book to give
     * @return boolean value which will depend on success of checking out
     */
    public static boolean checkOutBook(User user, int idOfBook) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfBook + " AND Status= 'In library' LIMIT 1 ");
            Book book = bookByID(idOfBook);
            if (!book.getReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.getType().equals("Student") && book.getBestseller()) {
                    pr.setInt(2, 14);//если студент и книга бестселлер, то ставим 14 дней
                    LocalDate returnDate = LocalDate.now().plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.getType().equals("Student") && !book.getBestseller()) {
                    pr.setInt(2, 21);//если студент и книга бестселлер, то ставим 21 дней
                    LocalDate returnDate = LocalDate.now().plusDays(21);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.getType().equals("Visiting Professor")) {
                    pr.setInt(2, 7);
                    LocalDate returnDate = LocalDate.now().plusDays(7);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else {
                    pr.setInt(2, 28);//это если факулти
                    LocalDate returnDate = LocalDate.now().plusDays(28);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkOutBookWithData(User user, int idOfBook,LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfBook + " AND Status= 'In library' LIMIT 1 ");
            Book book = bookByID(idOfBook);
            if (!book.getReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.getType().equals("Student") && book.getBestseller()) {
                    pr.setInt(2, 14);//если студент и книга бестселлер, то ставим 14 дней
                    LocalDate returnDate = date.plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.getType().equals("Student") && !book.getBestseller()) {
                    pr.setInt(2, 21);//если студент и книга бестселлер, то ставим 21 дней
                    LocalDate returnDate = date.plusDays(21);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.getType().equals("Visiting Professor")) {
                    pr.setInt(2, 7);
                    LocalDate returnDate = date.plusDays(7);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else {
                    pr.setInt(2, 28);//это если факулти
                    LocalDate returnDate = date.plusDays(28);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * the same operation, but for AV materials
     *
     * @param user   who gets the AV
     * @param idOfAV which AV give to user
     * @return boolean value - success or not success of checking out
     */
    public static boolean checkOutAV(User user, int idOfAV) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfAV + " AND Status= 'In library' LIMIT 1 ");
            AV av = avById(idOfAV);
            pr.setInt(1, user.getCard_number());
            if (user.getType().equals("Visiting Professor")) {

                pr.setInt(2, 7);
                LocalDate returnDate = LocalDate.now().plusDays(7);
                pr.setDate(4, java.sql.Date.valueOf(returnDate));

            } else {
                pr.setInt(2, 14);
                LocalDate returnDate = LocalDate.now().plusDays(14);
                pr.setDate(4, java.sql.Date.valueOf(returnDate));
            }
            pr.setString(3, "Issued");
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkOutAVWithData(User user, int idOfAV,LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfAV + " AND Status= 'In library' LIMIT 1 ");
            AV av = avById(idOfAV);
            pr.setInt(1, user.getCard_number());
            if (user.getType().equals("Visiting Professor")) {

                pr.setInt(2, 7);
                LocalDate returnDate = date.plusDays(7);
                pr.setDate(4, java.sql.Date.valueOf(returnDate));

            } else {
                pr.setInt(2, 14);
                LocalDate returnDate = date.plusDays(14);
                pr.setDate(4, java.sql.Date.valueOf(returnDate));
            }
            pr.setString(3, "Issued");
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * the same operation for Articles
     *
     * @param user        who gets the Article
     * @param idOfArticle which article to give to user
     * @return boolean value - success or not on checking out
     */
    public static boolean checkOutArticle(User user, int idOfArticle) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfArticle + " AND Status= 'In library' LIMIT 1 ");
            Article article = articleById(idOfArticle);
            if (!article.getReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.getType().equals("Visiting Professor")) {

                    pr.setInt(2, 7);
                    LocalDate returnDate = LocalDate.now().plusDays(7);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));

                } else {
                    pr.setInt(2, 14);
                    LocalDate returnDate = LocalDate.now().plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkOutArticleWithData(User user, int idOfArticle,LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfArticle + " AND Status= 'In library' LIMIT 1 ");
            Article article = articleById(idOfArticle);
            if (!article.getReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.getType().equals("Visiting Professor")) {

                    pr.setInt(2, 7);
                    LocalDate returnDate = date.plusDays(7);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));

                } else {
                    pr.setInt(2, 14);
                    LocalDate returnDate = date.plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean isFreeCopyExist(int idMaterial){
        try{
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Status = 'In library' AND Id_of_original= " + idMaterial);
            rs.last();
            if(rs.getRow() != 0) return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * check out for user,combination of all check out methods for user
     *
     * @param user       who check outs for material
     * @param idMaterial what material to check out
     * @return -1 error, 0 - user added in the queue, 1 - book is checked out
     */
    public static int checkOut(User user, int idMaterial) {
        try {
            db.queue_on_material(idMaterial,user.getCard_number());
            int success = -1;
            Statement stmt = null;
            ResultSet queue = null;
            try {
                stmt = db.con.createStatement();
                queue = stmt.executeQuery("SELECT * FROM queue_on_" + idMaterial + " LIMIT 1");
            } catch (SQLException e) {
                return -1;
            }
            try {
                stmt = db.con.createStatement();

                if (queue != null && queue.next() && queue.getInt("Card_number") == user.getCard_number()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idMaterial);
                    if (rs.next() ) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutBook(user, idMaterial)?1:0;
                        else success =  0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM AV WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutAV(user, idMaterial)?1:0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM Articles WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutArticle(user, idMaterial)?1:0;
                        else success =  0;
                    }
                    if (success == 1) {
                        if(getNumberOfCopiesOfBook(idMaterial)>=0) {
                            PreparedStatement pr = db.con.prepareStatement("DELETE from queue_on_" + idMaterial + " LIMIT 1");
                            pr.executeUpdate();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
                            Calendar cal = Calendar.getInstance();
                            pr = db.con.prepareStatement("UPDATE queue_on_" + idMaterial + " SET First_time=? LIMIT 1");
                            pr.setString(1, dateFormat.format(cal.getTime()));
                            pr.executeUpdate();
                        }
                        rs = stmt.executeQuery("SELECT * FROM queue_on_" + idMaterial);
                        rs.last();
                        if (rs.getRow() == 0) {
                            stmt.executeUpdate("DROP TABLE queue_on_" + idMaterial);
                        }
                    }
                } else {
                    //System.out.println("wait for your turn");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return success;
        }
        catch (Exception e){
            return -1;
        }
    }
    public static int checkOutWithData(User user, int idMaterial,LocalDate date) {
        try {
            db.queue_on_material(idMaterial,user.getCard_number());
            int success = -1;
            Statement stmt = null;
            ResultSet queue = null;
            try {
                stmt = db.con.createStatement();
                queue = stmt.executeQuery("SELECT * FROM queue_on_" + idMaterial + " LIMIT 1");
            } catch (SQLException e) {
                return -1;
            }
            try {
                stmt = db.con.createStatement();

                if (queue != null && queue.next() && queue.getInt("Card_number") == user.getCard_number()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idMaterial);
                    if (rs.next() ) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutBookWithData(user, idMaterial,date)?1:0;
                        else success =  0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM AV WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutAVWithData(user, idMaterial,date)?1:0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM Articles WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutArticleWithData(user, idMaterial,date)?1:0;
                        else success =  0;
                    }
                    if (success == 1) {
                        if(getNumberOfCopiesOfBook(idMaterial)>=0) {
                            PreparedStatement pr = db.con.prepareStatement("DELETE from queue_on_" + idMaterial + " LIMIT 1");
                            pr.executeUpdate();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
                            Calendar cal = Calendar.getInstance();
                            pr = db.con.prepareStatement("UPDATE queue_on_" + idMaterial + " SET First_time=? LIMIT 1");
                            pr.setString(1, dateFormat.format(cal.getTime()));
                            pr.executeUpdate();
                        }
                        rs = stmt.executeQuery("SELECT * FROM queue_on_" + idMaterial);
                        rs.last();
                        if (rs.getRow() == 0) {
                            stmt.executeUpdate("DROP TABLE queue_on_" + idMaterial);
                        }
                    }
                } else {
                    //System.out.println("wait for your turn");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return success;
        }
        catch (Exception e){
            return -1;
        }
    }
    /**
     * method for returning book
     *
     * @param idOfCopyOfBook what book to return
     * @return boolean value - is operation successfull or not
     */
    public static boolean returnBook(int idOfCopyOfBook) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=?,CanRenew=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setInt(1, 0);
            pr.setInt(2, 999);
            pr.setString(3, "In library");
            pr.setDate(4, java.sql.Date.valueOf(LocalDate.of(9999, 1, 1)));
            pr.setBoolean(5,true);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * method for librarian
     * librarian see's who wants to take a book
     *
     * @param idOfCopyOfBook
     * @return
     */
    public static boolean requestReturnBook(int idOfCopyOfBook) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Status=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setString(1, "Returning");
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get information about user by it's id
     *
     * @param id book's id
     */
    public static User UserById(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library where Card_number=" + id);
            while (rs.next()) {
                User user = new User(rs.getString("Name"),
                        rs.getString("Address"), rs.getString("Phone_number"),
                        rs.getInt("Card_number"),
                        rs.getString("Type"),
                        rs.getString("Password"),
                        rs.getString("Email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get information about material by it's id
     *
     * @param id book's id
     */
    public static Material MaterialByID(int id) {
        Material material = null;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books where id=" + id);
            if (rs.next()) {
                material = bookByID(id);
            } else {
                rs = stmt.executeQuery("SELECT * FROM AV where id=" + id);
                if (rs.next()) {
                    material = avById(id);
                } else {
                    rs = stmt.executeQuery("SELECT * FROM Articles where id=" + id);
                    if (rs.next()) {
                        material = articleById(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return material;
    }

    public static Book bookByID(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books where id=" + id);
            while (rs.next()) {
                return new Book(id, rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6),
                        rs.getString(7), rs.getBoolean(8),
                        rs.getBoolean(9), rs.getInt(10), 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the same method, but for AV
     *
     * @param id of AV
     */
    public static AV avById(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AV where id=" + id);
            while (rs.next()) {
                AV av = new AV(id, rs.getString("Name"),
                        rs.getString("Author"), rs.getInt("Price"),
                        rs.getString("Keywords"));
                return av;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id the same, but for articles
     */
    public static Article articleById(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Articles where id=" + id);
            while (rs.next()) {
                Article article = new Article(id, rs.getString("Name"),
                        rs.getString("Author"), rs.getInt("Price"),
                        rs.getString("Keywords"), rs.getBoolean("is_reference"),
                        rs.getString("Journal"), rs.getString("Editor"),
                        rs.getString("Date"));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * user deletion
     *
     * @param user_id user's id for deletion
     */
    public static void deleteUserById(int user_id) {
        try {
            Database db = new Database();
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner=" + user_id);
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Deletion");
                alert.setContentText("User has taken materials");
                alert.show();
            } else {
                PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id);
                pr.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("user with such id didn't found");
        }
        //есть еще идея удалять по именам,но чтобы библиотекарю вылезло уведомление,мол,может удалиться более 1 юзера
    }

    /**
     * change information of the user
     *
     * @param user whose information to change,search by id
     */
    public static void modifyUser(User user) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library " +
                    "SET Name=?,Address=?,Phone_number=?,Type=?,Password=?, Email=? where Card_number=" + user.getCard_Number());
            pr.setString(1, user.getName());
            pr.setString(2, user.getAdress());
            pr.setString(3, user.getPhoneNumber());
            pr.setString(4, user.getType());
            pr.setString(5, user.getPassword());
            pr.setString(6, user.getEmail());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adds copies of material
     *
     * @param id     of material
     * @param number how many copies to create
     */
    public static void addCopiesOfMaterial(int id, int number) {
        try {
            if (number > 0) {
                Statement stmt = db.con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Id_of_original FROM Copy");
                while (rs.next()) {
                    if (rs.getInt(1) == id) {
                        for (int i = 0; i < number; i++) {
                            PreparedStatement prst = db.con.prepareStatement("INSERT INTO Copy (id_of_original,Owner,Time_left) VALUES(?,?,?)");
                            prst.setInt(1, id);
                            prst.setInt(2, 0);
                            prst.setInt(3, 999);
                            prst.executeUpdate();


                        }
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * adding book to the library,with all parametres
     *
     * @param title
     * @param author
     * @param publisher
     * @param edition
     * @param price
     * @param keyWords
     * @param is_bestseller
     * @param reference
     * @param year
     * @param amount
     * @throws SQLException
     */
    public static void addBook(String title, String author, String publisher, String edition, int price, String keyWords, Boolean is_bestseller, boolean reference, int year, int amount) throws SQLException {
        /*
         * To add book, you need to send all information about book
         * ID will be created in DB with auto_increment
         * time_left will be 999 and owner 0 - because only librarian(Id =0) can add books
         * */
        Book book = new Book(title, author, publisher, edition, price, keyWords, is_bestseller, reference, year, "In library");
        db.bookCreation(book);
        ArrayList<Integer> arrayList = db.isBookAlreadyExist(book);
        addCopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    /**
     * the same method but for Article
     *
     * @param title
     * @param author
     * @param price
     * @param keyWords
     * @param reference
     * @param journal
     * @param editor
     * @param date
     * @param amount
     * @throws SQLException
     */
    public static void addArticle(String title, String author, int price, String keyWords,
                                  boolean reference, String journal, String editor, String date, int amount) throws SQLException {
        /*
         * To add article, you need to send all information about article
         * ID will be created in DB with auto_increment
         * time_left will be 999 and owner 0 - because only librarian(Id =0) can add articles
         * */
        Article article = new Article(title, author, price, keyWords, reference, journal, editor, date, "In library");
        db.articleCreation(article);
        ArrayList<Integer> arrayList = db.isArticleAlreadyExist(article);
        addCopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    /**
     * The same method, but for AV
     *
     * @param title
     * @param author
     * @param price
     * @param keyWords
     * @param amount
     * @throws SQLException
     */
    public static void addAV(String title, String author, int price, String keyWords, int amount) throws SQLException {
        AV av = new AV(title, author, price, keyWords, "In library");
        db.avCreation(av);
        ArrayList<Integer> arrayList = db.isAVAlreadyExist(av);
        addCopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    /**
     * deletion of AV
     *
     * @param id unique key of Av for deletion
     */
    public static void deleteAVById(int id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from AV WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AV with such id didn't found");
        }
    }

    /**
     * deletion of only 1 copy
     *
     * @param id unique key of copy
     */
    public static void deleteOneCopy(int id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_copy=" + id + " LIMIT 1");
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * the same method, but for books
     *
     * @param id unique key of book
     */
    public static void deleteBookById(int id) {

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Books WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("book with such id didn't found");
        }
    }

    /**
     * the same method, but for article
     *
     * @param id unique key of article
     */
    public static void deleteArticleById(int id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Articles WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("article with such id didn't found");
        }
    }

    /**
     * change information of AV
     *
     * @param av which av to change, search by id
     */
    public static void modifyAV(AV av) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE AV " +
                    "SET Name=?,Author=?,Price=?,Keywords=? where id=" + av.getId());
            pr.setString(1, av.getTitle());
            pr.setString(2, av.getAuthor());
            pr.setInt(3, av.getPrice());
            pr.setString(4, av.getKeyWords());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * the same method, but for Article
     *
     * @param article what article to change,search by id
     */
    public static void modifyArticle(Article article) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Articles " +
                    "SET Name=?,Author=?,Price=?,Keywords=?,is_reference=?,Journal=?,Editor=?,Date=? where id=" + article.getId());
            pr.setString(1, article.getTitle());
            pr.setString(2, article.getAuthor());
            pr.setInt(3, article.getPrice());
            pr.setString(4, article.getKeyWords());
            pr.setBoolean(5, article.getReference());
            pr.setString(6, article.getJournal());
            pr.setString(7, article.getEditor());
            pr.setDate(8, java.sql.Date.valueOf(article.getDate()));
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * the same method, but for books
     *
     * @param book what book to change,search by id
     */
    public static void modifyBook(Book book) {
        try {
            ArrayList arrayList = db.isBookAlreadyExist(book);
            PreparedStatement pr = db.con.prepareStatement("UPDATE Books " +
                    "SET Name=?,Author=?,Publisher=?,Edition=?,Price=?,Keywords=?,is_bestseller=?,is_reference=? where id=" + book.getId());
            pr.setString(1, book.getTitle());
            pr.setString(2, book.getAuthor());
            pr.setString(3, book.getPublisher());
            pr.setString(4, book.getEdition());
            pr.setInt(5, book.getPrice());
            pr.setString(6, book.getKeyWords());
            pr.setBoolean(7, book.getBestseller());
            pr.setBoolean(8, book.getReference());
            pr.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * gets all AV
     *
     * @return arraylist of AV's
     */
    public static ArrayList<Material> getAllAV() {
        ArrayList<Material> avs = new ArrayList<Material>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AV");
            while (rs.next()) {

                int number = 0;
                int id = rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()) {
                    if (rs2.getInt("id_of_original") == id) {
                        number++;
                    }
                }

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                int price = rs.getInt("Price");
                String keyWord = rs.getString("Keywords");
                AV av = new AV(id, name, author, price, keyWord, getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id));
                avs.add(av);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avs;
    }

    /**
     * the same method, bud for Articles
     *
     * @return arraylist of articles
     */
    public static ArrayList<Material> getAllArticles() {
        ArrayList<Material> articles = new ArrayList<Material>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Articles");
            while (rs.next()) {
                int number = 0;
                int id = rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()) {
                    if (rs2.getInt("id_of_original") == id) {
                        number++;
                    }
                }

                articles.add(new Article(id, rs.getString("Name"), rs.getString("Author"), rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"), rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"), getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * the same method, but for books
     *
     * @return arraylist of books
     */
    public static ArrayList<Material> getAllBooks() {
        ArrayList<Material> books = new ArrayList<Material>();

        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");
            while (rs.next()) {

                int number = 0;
                int id = rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()) {
                    if (rs2.getInt("id_of_original") == id) {
                        number++;
                    }
                }

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                String edition = rs.getString("Edition");
                int price = rs.getInt("Price");
                String keyWord = rs.getString("Keywords");
                boolean is_bestseller = rs.getBoolean("is_bestseller");
                boolean is_reference = rs.getBoolean("is_reference");
                int year = rs.getInt("Year");
                Book book = new Book(id, name, author, publisher, edition, price, keyWord, is_bestseller, is_reference, year, getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * the same method, but for users of the library
     *
     * @return linkedlist of users
     */
    public static LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<User>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Type<>'Librarian'");

            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String Phonenumber = rs.getString("Phone_number");
                int id = rs.getInt("Card_number");
                String type = rs.getString("Type");
                String password = rs.getString("Password");
                String email=rs.getString("Email");
                User user = new User(name, address, Phonenumber, id, type, password,email);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * gets the amount of taken materials by user
     *
     * @param user_id which user has taken materials
     * @return the amount of taken copies
     */
    public static int getNumberOfAllCopiesTakenByUser(int user_id) {
        int copies = 0;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner=" + user_id);
            while (rs.next()) {
                copies++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }

    /**
     * return all materials, taken by user
     *
     * @param user_id what user has taken materials
     * @return linkedlist of taken copies
     */
    public static LinkedList<Material> getAllCopiesTakenByUser(int user_id) {
        LinkedList<Material> copies = new LinkedList();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner=" + user_id);
            while (rs.next()) {
                int original_id = rs.getInt("Id_of_original");
                int copy_id = rs.getInt("Id_of_copy");
                String status = rs.getString("Status");
                LocalDate returnDate = rs.getDate("Return_date").toLocalDate();
                Statement articles_stmt = db.con.createStatement();
                ResultSet articles_rs = articles_stmt.executeQuery("SELECT * FROM Articles where id=" + original_id);
                while (articles_rs.next()) {
                    String name = articles_rs.getString("Name");
                    String author = articles_rs.getString("Author");
                    int price = articles_rs.getInt("Price");
                    String keywords = articles_rs.getString("Keywords");
                    boolean is_reference = articles_rs.getBoolean("is_reference");
                    String journal = articles_rs.getString("Journal");
                    String editor = articles_rs.getString("Editor");
                    Article article = new Article(copy_id, name, author, price, keywords, is_reference, journal, editor, returnDate, status, user_id);
                    article.setFine();
                    copies.add((Material) article);
                }
                Statement AV_stmt = db.con.createStatement();
                ResultSet AV_rs = AV_stmt.executeQuery("SELECT * FROM AV where id=" + original_id);
                while (AV_rs.next()) {
                    String name = AV_rs.getString("Name");
                    String author = AV_rs.getString("Author");
                    int price = AV_rs.getInt("Price");
                    String keywords = AV_rs.getString("Keywords");
                    AV av = new AV(copy_id, name, author, price, keywords, returnDate, status, user_id);
                    av.setFine();
                    copies.add((Material) av);
                }
                Statement books_stmt = db.con.createStatement();
                ResultSet books_rs = books_stmt.executeQuery("SELECT * FROM Books where id=" + original_id);
                while (books_rs.next()) {
                    String name = books_rs.getString("Name");
                    String author = books_rs.getString("Author");
                    String publisher = books_rs.getString("Publisher");
                    String edition = books_rs.getString("Edition");
                    int price = books_rs.getInt("Price");
                    String keywords = books_rs.getString("Keywords");
                    boolean is_bestseller = books_rs.getBoolean("is_bestseller");
                    boolean is_reference = books_rs.getBoolean("is_reference");
                    int year = books_rs.getInt("Year");
                    Book book = new Book(copy_id, name, author, publisher, edition, price, keywords, is_bestseller, is_reference, year, 0, returnDate, status, user_id);
                    book.setFine();
                    copies.add((Material) book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }


    public static ArrayList<User> getQueue(int materialID){
        ArrayList <User> queue = new ArrayList<>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM queue_on_" + materialID);
            while (rs.next()) {
                int id = rs.getInt("Card_number");
                String resTime = rs.getString("Reserving_time");
                Boolean isNotified = rs.getBoolean("is_sended");
                User user = new User(id, resTime, isNotified);

                queue.add(user);
            }

            for(int i = 0; i < queue.size(); i++) {
                rs = stmt.executeQuery("SELECT * FROM users_of_the_library WHERE Card_number=" + queue.get(i).getCard_Number());

                while (rs.next()) {
                    String name = rs.getString("Name");
                    queue.get(i).setName(name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queue;
    }


    /**
     * get the count of copies of the book
     *
     * @param book_id which book has copies
     * @return the amount of copies taken by nobody
     */
    public static int getNumberOfCopiesOfBook(int book_id) {
        int copies = 0;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Id_of_original=" + book_id + " and Owner=" + 0);
            while (rs.next()) {
                copies++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }

    /**
     * get the count of copies of the book
     *
     * @param book_id which book has copies
     * @return the amount of all copies
     */
    public static int getNumberOfCopiesOfWithTaken(int book_id) {
        int copies = 0;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Id_of_original=" + book_id);
            while (rs.next()) {
                copies++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }

    /**
     * @return all copies of material
     */
    public static LinkedList<Material> getAllCopies() {
        LinkedList<Material> copies = new LinkedList();
        LinkedList<Integer> attended_id = new LinkedList<>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy");
            while (rs.next()) {
                int owner_id = rs.getInt("Owner");
                attended_id.add(owner_id);
                if (numberOfMeetings(attended_id, owner_id)) {
                    LinkedList<Material> owner_copies = getAllCopiesTakenByUser(owner_id);
                    copies.addAll(owner_copies);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }

    /**
     * gets the information if material has been already met
     *
     * @param l  list of copies
     * @param id unique key of current material
     * @return boolean value - was met or not
     */
    private static boolean numberOfMeetings(LinkedList<Integer> l, int id) {
        int count = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == id)
                count++;
        }
        if (count > 1) return false;
        else return true;
    }

    /**
     * deletion of material
     *
     * @param material what material to delete
     */
    public static void deleteDoc(Material material) {
        if (material.getType().equals("Book")) deleteBookById(material.getId());
        else if (material.getType().equals("AV")) deleteAVById(material.getId());
        else deleteArticleById(material.getId());
    }
}