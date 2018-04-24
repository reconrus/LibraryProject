package main.java.librinno.model;

import javafx.scene.control.Alert;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.xml.crypto.Data;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * librarian of current library
 */
public class Librarian extends User {
    //include logger for maintenance of logs
    public static final Logger LOGGER = Logger.getLogger("GLOBAL");
    //librarian privileges
    public static final String lev1 = "Priv1";
    public static final String lev2 = "Priv2";
    public static final String lev3 = "Priv3";
    private String privileges;
    /**
     * setter
     *
     * @param name - name of librarian
     * @param phone_number - number of librarian
     * @param adress - where librarian lives
     * @param card_number - id of librarian
     */
    //creating database for working
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
    public Librarian(String name, String address, String number, int cardnumber, String type, String password, String email) {
        super(name, address, number, cardnumber, type, password, email);
        String[] typeArr = type.split(" ");
        setType(typeArr[0]);
        setPrivileges(typeArr[1]);
    }

    //getters and setters of privileges
    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getPrivileges() {
        return privileges;
    }

    /**
     * all parameters are the same, but not with full information
     * because it will not be needed
     */
    public void Librarian(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.phoneNumber = phone_number;
        this.adress = adress;
        this.card_number = card_number;
    }

    /**
     * sending email to people who took material with request to return it
     * and to people who was in queue with request that material is not available anymore
     * also deleting queue for this material
     *
     * @param idOfMaterial id of material
     */
    public void outstandingRequest(int idOfMaterial) {
        if(getPrivileges().equals("Priv2") || getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_original= " + idOfMaterial + " AND Status = 'Issued'");
                pr.setInt(1, 0);
                pr.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                pr.setBoolean(3, false);
                pr.executeUpdate();

                ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_original= " + idOfMaterial);
                int owner;
                while (rs.next()) {
                    owner = rs.getInt("Owner");
                    Statement stmt = db.con.createStatement();
                    ResultSet rs2 = stmt.executeQuery("SELECT  * FROM users_of_the_library WHERE Card_number= " + owner);
                    String email = null;
                    while (rs2.next()) {
                        email = rs2.getString("Email");
                    }
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendToOne(email, "Return book.", "Urgently return the book throughout the day! Because of outstanding request.");
                }
                rs = pr.executeQuery("SELECT * FROM queue_on_" + idOfMaterial);
                while (rs.next()) {
                    String email = rs.getString("Email");
                    SendEmail send = new SendEmail();
                    send.sendToOne(email, "Material is not available", "Material,which you reserved,now is not available.You are removed from waiting list");
                }
                pr.executeUpdate("DROP TABLE IF EXISTS queue_on_" + idOfMaterial);
                LOGGER.trace("Librarian with id "+Database.isUserAlreadyExist(this).get(1)+" made an outstanding request on "+idOfMaterial);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ArrayList<String>> outstandingRequestWithDate(int idOfMaterial, LocalDate date) {
        if(getPrivileges().equals("Priv2") || getPrivileges().equals("Priv3")) {
            ArrayList<String> emails = new ArrayList<>();
            ArrayList<String> book_no_available = new ArrayList<>();
            try {
                PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_original= " + idOfMaterial + " AND Status = 'Issued'");
                pr.setInt(1, 0);
                pr.setDate(2, java.sql.Date.valueOf(date));
                pr.setBoolean(3, false);
                pr.executeUpdate();

                ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_original= " + idOfMaterial);
                int owner;
                while (rs.next() && rs.getInt("Owner") != 0) {
                    owner = rs.getInt("Owner");
                    Statement stmt = db.con.createStatement();
                    ResultSet rs2 = stmt.executeQuery("SELECT  * FROM users_of_the_library WHERE Card_number= " + owner);
                    String email = "";
                    while (rs2.next()) {
                        email = rs2.getString("Email");
                        emails.add(email);
                    }
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendToOne(email, "Return book.", "Urgently return the book throughout the day! Because of outstanding request.");
                }
                try {
                    rs = pr.executeQuery("SELECT * FROM queue_on_" + idOfMaterial);
                    while (rs.next()) {
                        String email = rs.getString("Email");
                        book_no_available.add(email);
                        SendEmail send = new SendEmail();
                        send.sendToOne(email, "Material is not available", "Material,which you reserved,now is not available.You are removed from waiting list");
                    }
                } catch (SQLException e) {
                }
                pr.executeUpdate("DROP TABLE IF EXISTS queue_on_" + idOfMaterial);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayList<ArrayList<String>> all_emails = new ArrayList<>();
            all_emails.add(emails);
            all_emails.add(book_no_available);
            return all_emails;
        }
        return null;
    }

    public static boolean renewWithDate(User user, int idOfRenewCopy, LocalDate date) {
        boolean oldCanRenew = false;
        int idOfOriginal = 0;

        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_copy= " + idOfRenewCopy + " AND CanRenew=" + true);
            ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_copy= " + idOfRenewCopy);
            while (rs.next()) {
                oldCanRenew = rs.getBoolean("CanRenew");
                idOfOriginal = rs.getInt("Id_of_original");
            }

            if (user.getType().equals("Visiting Professor")) {
                //если визитинг профессор, то он всегда все берет только на одну неделю
                pr.setInt(1, 7);
                pr.setDate(2, java.sql.Date.valueOf(date.plusDays(7)));
                pr.setBoolean(3, true);
                pr.executeUpdate();
                return true;

            } else if (oldCanRenew) {//если это не ВизПроф, то чекаем обновлял ли он до этого(если обновлял то false, если может обновить, то true)
                Statement stmt = db.con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idOfOriginal);
                if (rs.next()) {
                    //если книга, то уже смотрим на тип юзера и книгу
                    if (user.getType().equals("Student") && rs.getBoolean("is_bestseller")) {
                        pr.setInt(1, 14);
                        pr.setDate(2, java.sql.Date.valueOf(date.plusDays(14)));
                        pr.setBoolean(3, false);
                        pr.executeUpdate();
                        return true;

                    } else if (user.getType().equals("Student") && !rs.getBoolean("is_bestseller")) {
                        pr.setInt(1, 21);
                        pr.setDate(2, java.sql.Date.valueOf(date.plusDays(21)));
                        pr.setBoolean(3, false);
                        pr.executeUpdate();
                        return true;
                    } else {
                        pr.setInt(1, 28);
                        pr.setDate(2, java.sql.Date.valueOf(date.plusDays(28)));
                        pr.setBoolean(3, false);
                        pr.executeUpdate();
                        return true;
                    }
                } else {
                    pr.setInt(1, 14);
                    pr.setDate(2, java.sql.Date.valueOf(date.plusDays(14)));
                    pr.setBoolean(3, false);
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

    /**
     * renew of information of material
     *
     * @param user          which user renews information
     * @param idOfRenewCopy current copy of material
     * @return if renew was successful or not
     */
    public static boolean renew(User user, int idOfRenewCopy) {
        PropertyConfigurator.configure("log4j.properties");
        boolean oldCanRenew = false;
        int idOfOriginal = 0;
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Time_left=?,Return_date=?,CanRenew=? WHERE Id_of_copy= " + idOfRenewCopy + " AND CanRenew=" + true);
            ResultSet rs = pr.executeQuery("SELECT  * FROM Copy WHERE Id_of_copy= " + idOfRenewCopy);
            while (rs.next()) {
                oldCanRenew = rs.getBoolean("CanRenew");
                idOfOriginal = rs.getInt("Id_of_original");

            }
            if (user.getType().equals("Visiting Professor")) {
                pr.setInt(1, 7);
                pr.setDate(2, java.sql.Date.valueOf(LocalDate.now().plusDays(7)));
                pr.setBoolean(3, true);
                pr.executeUpdate();
                return true;

            } else if (oldCanRenew) {
                Statement stmt = db.con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idOfOriginal);
                if (rs.next()) {
                    if (user.getType().equals("Student") && rs.getBoolean("is_bestseller")) {
                        pr.setInt(1, 14);
                        pr.setDate(2, java.sql.Date.valueOf(LocalDate.now().plusDays(14)));
                        pr.setBoolean(3, false);
                        pr.executeUpdate();
                        LOGGER.trace("user with id " + Database.isUserAlreadyExist(user).get(1) + " renewed copy with id " + idOfRenewCopy);
                        return true;

                    } else if (user.getType().equals("Student") && !rs.getBoolean("is_bestseller")) {
                        pr.setInt(1, 21);
                        pr.setDate(2, java.sql.Date.valueOf(LocalDate.now().plusDays(21)));
                        pr.setBoolean(3, false);
                        LOGGER.trace("user with id " + Database.isUserAlreadyExist(user).get(1) + " renewed copy with id " + idOfRenewCopy);
                        pr.executeUpdate();
                        return true;
                    } else {
                        pr.setInt(1, 28);
                        pr.setDate(2, java.sql.Date.valueOf(LocalDate.now().plusDays(28)));
                        pr.setBoolean(3, false);
                        pr.executeUpdate();
                        LOGGER.trace("user with id " + Database.isUserAlreadyExist(user).get(1) + " renewed copy with id " + idOfRenewCopy);
                        return true;
                    }
                } else {
                    pr.setInt(1, 14);
                    pr.setDate(2, java.sql.Date.valueOf(LocalDate.now().plusDays(14)));
                    pr.setBoolean(3, false);
                    pr.executeUpdate();
                    LOGGER.trace("user with id " + Database.isUserAlreadyExist(user).get(1) + " renewed copy with id " + idOfRenewCopy);
                    return true;
                }
            }
            LOGGER.trace("user with id " + Database.isUserAlreadyExist(user).get(1) + "didn't renew copy with id " + idOfRenewCopy);
            return false;

        } catch (SQLException e) {
            LOGGER.error("error in renew operation");
            return false;
        }
    }

    /**
     * fine for expiration of material
     *
     * @param idOfCopy current copy
     * @return current amount of money of expiration
     */
    public static int fine(int idOfCopy) {
        PropertyConfigurator.configure("log4j.properties");
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
        } catch (SQLException e) {
            LOGGER.error("Error in fine operation");
        }
        return 0;
    }

    public static int fineWithDate(int idOfCopy, LocalDate date) {
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
        } catch (SQLException e) {
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
        PropertyConfigurator.configure("log4j.properties");
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
                LOGGER.trace("Copy of book with id " + idOfBook + " was given to user with id " + Database.isUserAlreadyExist(user).get(1));
                return true;
            } else {
                LOGGER.trace("Copy of book with id " + idOfBook + " wasn't given to user with id " + Database.isUserAlreadyExist(user).get(1));
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Error in checking out book");
            return false;
        }
    }

    public static boolean checkOutBookWithData(User user, int idOfBook, LocalDate date) {
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
     * method for checking out AVs
     *
     * @param user   who gets the AV
     * @param idOfAV which AV give to user
     * @return boolean value - success or not success of checking out
     */
    public static boolean checkOutAV(User user, int idOfAV) {
        PropertyConfigurator.configure("log4j.properties");
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
            LOGGER.trace("Copy of av with id " + idOfAV + " was given to user with id " + Database.isUserAlreadyExist(user).get(1));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                LOGGER.trace("Copy of av with id " + idOfAV + " wasn't given to user with id " + Database.isUserAlreadyExist(user).get(1));
            } catch (SQLException e1) {
            }
            return false;
        }
    }

    public static boolean checkOutAVWithData(User user, int idOfAV, LocalDate date) {
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
     * method for checking out articles
     *
     * @param user        who gets the articles
     * @param idOfArticle which article to give to user
     * @return boolean value - success or not on checking out
     */
    public static boolean checkOutArticle(User user, int idOfArticle) {
        PropertyConfigurator.configure("log4j.properties");
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
                LOGGER.trace("Article with id " + idOfArticle + " was checked out to user with id " + user.getCard_number());
                return true;
            } else {
                LOGGER.trace("Article with id " + idOfArticle + " wasn't checked out to user with id " + user.getCard_number());
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("Error during checking out Article");
            return false;
        }
    }

    public static boolean checkOutArticleWithData(User user, int idOfArticle, LocalDate date) {
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

    /**
     * checking for availability of copies of material
     *
     * @param idMaterial which copy of material to find
     * @return if was found or not
     */
    private static boolean isFreeCopyExist(int idMaterial) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Status = 'In library' AND Id_of_original= " + idMaterial);
            rs.last();
            if (rs.getRow() != 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * check out for user,combination of all check out methods
     *
     * @param user       who check outs for material
     * @param idMaterial what material to check out
     * @return -1 error, 0 - user added in the queue, 1 - book is checked out
     */
    public static int checkOut(User user, int idMaterial) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            db.queue_on_material(idMaterial, user.getCard_number());
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
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutBook(user, idMaterial) ? 1 : 0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM AV WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutAV(user, idMaterial) ? 1 : 0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM Articles WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutArticle(user, idMaterial) ? 1 : 0;
                        else success = 0;
                    }
                    if (success == 1) {
                        if (getNumberOfCopiesOfBook(idMaterial) >= 0) {
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
                            LOGGER.trace("There is no people awaiting for material " + idMaterial + ". Queue is deleted.");
                        }
                    }
                } else {
                    LOGGER.trace("To early for user with id " + Database.isUserAlreadyExist(user).get(1) + " to get material with id " + idMaterial);
                }
            } catch (SQLException e) {
                LOGGER.error("Error in checking out of " + idMaterial);
            }
            if (success == 1)
                LOGGER.trace("Successful checking out of " + idMaterial + " to user " + Database.isUserAlreadyExist(user).get(1));
            if (success == 0)
                LOGGER.trace("User with id " + Database.isUserAlreadyExist(user).get(1) + " joining a queue on material with id " + idMaterial);
            return success;
        } catch (Exception e) {
            LOGGER.error("Error in checking out");
            return -1;
        }
    }

    public static int checkOutWithData(User user, int idMaterial, LocalDate date) {
        try {
            db.queue_on_material(idMaterial, user.getCard_number());
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
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutBookWithData(user, idMaterial, date) ? 1 : 0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM AV WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutAVWithData(user, idMaterial, date) ? 1 : 0;
                        else success = 0;
                    }
                    rs = stmt.executeQuery("SELECT * FROM Articles WHERE id =" + idMaterial);
                    if (rs.next()) {
                        if (isFreeCopyExist(idMaterial))
                            success = checkOutArticleWithData(user, idMaterial, date) ? 1 : 0;
                        else success = 0;
                    }
                    if (success == 1) {
                        if (getNumberOfCopiesOfBook(idMaterial) >= 0) {
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
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * method for returning book
     *
     * @param idOfCopyOfBook which book to return
     * @return boolean value - is operation successful or not
     */
    public static boolean returnBook(int idOfCopyOfBook) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=?,CanRenew=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setInt(1, 0);
            pr.setInt(2, 999);
            pr.setString(3, "In library");
            pr.setDate(4, java.sql.Date.valueOf(LocalDate.of(9999, 1, 1)));
            pr.setBoolean(5, true);
            pr.executeUpdate();
            LOGGER.trace("Material with copy id " + idOfCopyOfBook + " is returned");
            return true;
        } catch (SQLException e) {
            LOGGER.error("Error of returning material " + idOfCopyOfBook);
            return false;
        }
    }

    /**
     * method for librarian
     * librarian see's who wants to take a book
     *
     * @param idOfCopyOfBook
     * @return if request was successful or not
     */
    public static boolean requestReturnBook(int idOfCopyOfBook) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Status=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setString(1, "Returning");
            pr.executeUpdate();
            LOGGER.trace("Successful request on " + idOfCopyOfBook);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Error of request");
            return false;
        }
    }

    /**
     * get information about user by it's id
     *
     * @param id user's id
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

    /**
     * get information about the book by it's id
     *
     * @param id id of book
     */
    public static Book bookByID(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books where id=" + id);
            while (rs.next()) {
                return new Book(id, rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6),
                        rs.getString(7), rs.getBoolean(8),
                        rs.getBoolean(9), rs.getInt(10), getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get information about the AV by it's id
     *
     * @param id id of AV
     */
    public static AV avById(int id) {
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AV where id=" + id);
            while (rs.next()) {
                AV av = new AV(id, rs.getString("Name"),
                        rs.getString("Author"), rs.getInt("Price"),
                        rs.getString("Keywords"),getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id));
                return av;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get information about the article by it's id
     *
     * @param id id of article
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
                        rs.getString("Date"),getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * deletion of the user
     *
     * @param user_id user's id for deletion
     */
    public void deleteUserById(int user_id) {
        if(getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                Database db = new Database();
                Statement stmt = db.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner=" + user_id);
                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Deletion");
                    alert.setContentText("User has taken materials");
                    alert.show();
                    LOGGER.error("Attempt to delete user with id " + user_id + ".Reason of not deleting: he has taken materials");
                } else {
                    PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id);
                    LOGGER.trace("Successful deletion of user with id " + user_id);
                    pr.executeUpdate();
                }
            } catch (SQLException e) {
                LOGGER.error("user with id " + user_id + "isn't found");
            }
        }
    }

    /**
     * change information of the user
     *
     * @param user whose information to change,search by id
     */
    public static void modifyUser(User user) {
        PropertyConfigurator.configure("log4j.properties");
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
            LOGGER.trace("Successful modification of user with id " + Database.isUserAlreadyExist(user).get(1));
        } catch (SQLException e) {
            LOGGER.error("Error in modification of user");
        }
    }

    /**
     * adds copies of material
     *
     * @param id     of material
     * @param number how many copies to add
     */
    public void addCopiesOfMaterial(int id, int number) {
        if (getPrivileges().equals("Priv2")||getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                if (number > 0) {
                    boolean isMaterialInDB = false;
                    Statement stmt = db.con.createStatement();
                    ResultSet rs;
                    rs = stmt.executeQuery("SELECT id FROM books");

                    while (rs.next())
                        if (rs.getInt(1) == id)
                            isMaterialInDB = true;

                    rs = stmt.executeQuery("SELECT id FROM articles");

                    while (rs.next())
                        if (rs.getInt(1) == id)
                            isMaterialInDB = true;

                    rs = stmt.executeQuery("SELECT id FROM av");

                    while (rs.next())
                        if (rs.getInt(1) == id)
                            isMaterialInDB = true;


                    if(isMaterialInDB) {
                        for (int i = 0; i < number; i++) {
                            PreparedStatement prst = db.con.prepareStatement("INSERT INTO Copy (id_of_original,Owner,Time_left) VALUES(?,?,?)");
                            prst.setInt(1, id);
                            prst.setInt(2, 0);
                            prst.setInt(3, 999);
                            prst.executeUpdate();
                        }
                        LOGGER.trace("Librarian "+ Database.isUserAlreadyExist(this).get(1)+ " added " + number + " copies of material " + id);
                        return;
                    }
                }
            } catch (SQLException e) {
                LOGGER.error("Error in adding copies");
            }
        }
    }

    /**
     * Adding new patron
     * @param user - user that we want to add
     */
    public void addUser(User user){
        if(getPrivileges().equals("Priv2")|| getPrivileges().equals("Priv3"))
            try {
                Database.userCreation(user);
                if (!user.getType().equals("Admin")) LOGGER.trace("Librarian "+Database.isUserAlreadyExist(this).get(1)+ " created user with ID " + Database.isUserAlreadyExist(user).get(1));
            } catch (SQLException e) {
                LOGGER.error("Error in adding user");
            }
    }

    /**
     * adding book to the library,with all parameters
     *
     * @param title         of book
     * @param author        of book
     * @param publisher     of book
     * @param edition       of book
     * @param price         of book
     * @param keyWords      of book
     * @param is_bestseller of book
     * @param reference     of book
     * @param year          of book
     * @param amount        of book
     */
    public void addBook(String title, String author, String publisher, String edition, int price, String keyWords, Boolean is_bestseller, boolean reference, int year, int amount) {
        if(getPrivileges().equals("Priv2")|| getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                Book book = new Book(title, author, publisher, edition, price, keyWords, is_bestseller, reference, year, "In library");
                db.bookCreation(book);
                ArrayList<Integer> arrayList = db.isBookAlreadyExist(book);
                LOGGER.trace("Librarian "+Database.isUserAlreadyExist(this).get(1)+ " created book with id "+arrayList.get(1));
                addCopiesOfMaterial(arrayList.get(1), amount);
            } catch (SQLException e) {
                LOGGER.error("Error in creating book");
            }
        }
    }

    /**
     * adding article to the library,with all parameters
     *
     * @param title     of article
     * @param author    of article
     * @param price     of article
     * @param keyWords  of article
     * @param reference of article
     * @param journal   of article
     * @param editor    of article
     * @param date      of article
     * @param amount    of article
     */
    public void addArticle(String title, String author, int price, String keyWords,
                                  boolean reference, String journal, String editor, String date, int amount) {
        if(getPrivileges().equals("Priv2")|| getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                Article article = new Article(title, author, price, keyWords, reference, journal, editor, date, "In library");
                db.articleCreation(article);
                ArrayList<Integer> arrayList = db.isArticleAlreadyExist(article);
                LOGGER.trace("Librarian "+Database.isUserAlreadyExist(this)+ " created article with id "+arrayList.get(1));
                addCopiesOfMaterial(arrayList.get(1), amount);
            } catch (SQLException e) {
                LOGGER.error("Error in adding article");
            }
        }
    }

    /**
     * adding AV to the library,with all parameters
     *
     * @param title    of AV
     * @param author   of AV
     * @param price    of AV
     * @param keyWords of AV
     * @param amount   of AV
     */
    public void addAV(String title, String author, int price, String keyWords, int amount) {
        if(getPrivileges().equals("Priv2")|| getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                AV av = new AV(title, author, price, keyWords, "In library");
                db.avCreation(av);
                ArrayList<Integer> arrayList = db.isAVAlreadyExist(av);
                LOGGER.trace("Librarian "+Database.isUserAlreadyExist(this)+ " created AV material with id "+arrayList.get(1));
                addCopiesOfMaterial(arrayList.get(1), amount);
            } catch (SQLException e) {
                LOGGER.error("Error in creating av");
            }
        }
    }

    /**
     * deletion of AV
     *
     * @param id unique key of AV for deletion
     */
    public void deleteAVById(int id) {
        if(getPrivileges().equals("Priv3")){
            PropertyConfigurator.configure("log4j.properties");
            try {
                PreparedStatement pr = db.con.prepareStatement("DELETE from AV WHERE id=" + id);
                pr.executeUpdate();
                pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
                pr.executeUpdate();
                LOGGER.trace("Successful deletion of AV with id " + id);
            } catch (SQLException e) {
                LOGGER.error("AV with id " + id + " isn't found");
            }
        }
    }

    /**
     * Deletion of 1 copy
     *
     * @param id unique key of copy
     */
    public void deleteOneCopy(int id) {
        if(getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_copy=" + id + " LIMIT 1");
                pr.executeUpdate();
                LOGGER.trace("Successful deletion of Copy with id " + id);
            } catch (SQLException e) {
                LOGGER.error("Copy with id " + id + " isn't found");
            }
        }
    }

    /**
     * deletion of book
     *
     * @param id unique key of book
     */
    public void deleteBookById(int id) {
        if(getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");

            try {
                PreparedStatement pr = db.con.prepareStatement("DELETE from Books WHERE id=" + id);
                pr.executeUpdate();
                pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
                pr.executeUpdate();
                LOGGER.trace("Successful deletion of Book with id " + id);
            } catch (SQLException e) {
                LOGGER.error("Book with id " + id + " isn't found");
            }
        }
    }

    /**
     * deletion of article
     *
     * @param id unique key of article
     */
    public void deleteArticleById(int id) {
        if(getPrivileges().equals("Priv3")) {
            PropertyConfigurator.configure("log4j.properties");
            try {
                PreparedStatement pr = db.con.prepareStatement("DELETE from Articles WHERE id=" + id);
                pr.executeUpdate();
                pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
                pr.executeUpdate();
                LOGGER.trace("Successful deletion of Article with id " + id);
            } catch (SQLException e) {
                LOGGER.error("Article with id " + id + " isn't found");
            }
        }
    }

    /**
     * change information of AV
     *
     * @param av which AV to change, search by id
     */
    public static void modifyAV(AV av) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE AV " +
                    "SET Name=?,Author=?,Price=?,Keywords=? where id=" + av.getId());
            ArrayList<Integer> arrayList = db.isAVAlreadyExist(av);
            pr.setString(1, av.getTitle());
            pr.setString(2, av.getAuthor());
            pr.setInt(3, av.getPrice());
            pr.setString(4, av.getKeyWords());
            pr.executeUpdate();
            LOGGER.trace("Modification of AV with id " +arrayList.get(1));
        } catch (SQLException e) {
            LOGGER.error("AV with id " + av.getId() + " isn't found");
        }
    }

    /**
     * change information of the article
     *
     * @param article what article to change,search by id
     */
    public static void modifyArticle(Article article) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Articles " +
                    "SET Name=?,Author=?,Price=?,Keywords=?,is_reference=?,Journal=?,Editor=?,Date=? where id=" + article.getId());
            ArrayList<Integer> arrayList = db.isArticleAlreadyExist(article);
            pr.setString(1, article.getTitle());
            pr.setString(2, article.getAuthor());
            pr.setInt(3, article.getPrice());
            pr.setString(4, article.getKeyWords());
            pr.setBoolean(5, article.getReference());
            pr.setString(6, article.getJournal());
            pr.setString(7, article.getEditor());
            pr.setDate(8, java.sql.Date.valueOf(article.getDate()));
            pr.executeUpdate();
            LOGGER.trace("Modification of Article with id " + arrayList.get(1));
        } catch (SQLException e) {
            LOGGER.error("AV with isn't found");
        }
    }

    /**
     * change information of the book
     *
     * @param book what book to change,search by id
     */
    public static void modifyBook(Book book) {
        PropertyConfigurator.configure("log4j.properties");
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
            LOGGER.trace("Modification of Book with id " + arrayList.get(1));
        } catch (SQLException e) {
            LOGGER.error("Book isn't found");
        }
    }


    /**
     * gets all AV in system
     *
     * @return all AV's
     */
    public static ArrayList<Material> getAllAV() {
        ArrayList<Material> avs = new ArrayList<Material>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AV");
            while (rs.next()) {

                int id = rs.getInt("id");

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
     * gets all articles in system
     *
     * @return all articles
     */
    public static ArrayList<Material> getAllArticles() {
        ArrayList<Material> articles = new ArrayList<Material>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Articles");
            while (rs.next()) {
                int id = rs.getInt("id");

                articles.add(new Article(id, rs.getString("Name"), rs.getString("Author"),
                        rs.getInt("Price"), rs.getString("Keywords"), rs.getBoolean("is_reference"),
                        rs.getString("Journal"), rs.getString("Editor"), rs.getString("Date"),
                        getNumberOfCopiesOfBook(id), getNumberOfCopiesOfWithTaken(id)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * gets all books in system
     *
     * @return all books
     */
    public static ArrayList<Material> getAllBooks() {
        ArrayList<Material> books = new ArrayList<Material>();

        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");
            while (rs.next()) {

                int id = rs.getInt("id");

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
     * gets all users in system
     *
     * @return all users
     */
    public static LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<User>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Type<>'Librarian Priv1' AND Type <>'Librarian Priv2' AND Type <>'Librarian Priv3' AND Type <>'Admin'");

            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String Phonenumber = rs.getString("Phone_number");
                int id = rs.getInt("Card_number");
                String type = rs.getString("Type");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                User user = new User(name, address, Phonenumber, id, type, password, email);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * gets all librarians  in system
     *
     * @return all librarians
     */
    public static LinkedList<Librarian> getAllLibrarians() {
        LinkedList<Librarian> librarians = new LinkedList<Librarian>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Type = 'Librarian Priv1' or Type = 'Librarian Priv2' or Type = 'Librarian Priv3'");

            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String Phonenumber = rs.getString("Phone_number");
                int id = rs.getInt("Card_number");
                String type = rs.getString("Type");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                librarians.add(new Librarian(name, address, Phonenumber, id, type, password, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarians;
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
     * return all copies of materials, taken by user
     *
     * @param user_id which user has taken materials
     * @return all taken copies
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


    /**
     * showing the queue of users for current material
     *
     * @param materialID on which material show the queue
     * @return list of awaiting users
     */
    public static ArrayList<User> getQueue(int materialID) {
        ArrayList<User> queue = new ArrayList<>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM queue_on_" + materialID);
            while (rs.next()) {
                int id = rs.getInt("Card_number");
                String resTime = rs.getString("Reserving_time");
                Boolean isNotified = rs.getBoolean("is_sent");
                User user = new User(id, resTime, isNotified);
                queue.add(user);
            }

            for (int i = 0; i < queue.size(); i++) {
                rs = stmt.executeQuery("SELECT * FROM users_of_the_library WHERE Card_number=" + queue.get(i).getCard_Number());

                while (rs.next()) {
                    String name = rs.getString("Name");
                    queue.get(i).setName(name);
                }
            }

        } catch (SQLException e) {
            return queue;
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
    public void deleteDoc(Material material) {
        if(getPrivileges().equals("Priv3"))
            if (material.getType().equals("Book")) deleteBookById(material.getId());
            else if (material.getType().equals("AV")) deleteAVById(material.getId());
                 else deleteArticleById(material.getId());
    }
}