package main.java.librinno.model;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

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
    private static Database db = new Database();

    public void Librarian(String name, String phone_number, String adress, int card_number) {
        this.name = name;
        this.Phone_Number = phone_number;
        this.adress = adress;
        this.card_number = card_number;
    }

    public void add_user() {
        User user = new User();
        db.user_creation(user);
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library SET Type='User' WHERE Card_number=" + user.card_number);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while creating user");
        }
    }

    public static boolean checkOutBook(User user,int idOfBook){
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfBook + " AND Status= 'In library' LIMIT 1 ");
            Book book = bookByID(idOfBook);
            if (!book.isReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.get_type().equals("Student") && book.isIs_bestseller()) {
                    pr.setInt(2, 14);//если студент и книга бестселлер, то ставим 14 дней
                    LocalDate returnDate =LocalDate.now().plusDays(14);
                    pr.setDate(4,java.sql.Date.valueOf(returnDate));
                }else if (user.get_type().equals("Student") && !book.isIs_bestseller()) {
                    pr.setInt(2, 21);//если студент и книга бестселлер, то ставим 21 дней
                    LocalDate returnDate =LocalDate.now().plusDays(21);
                    pr.setDate(4,java.sql.Date.valueOf(returnDate));
                }else {
                    pr.setInt(2, 28);//это если факулти
                    LocalDate returnDate =LocalDate.now().plusDays(28);
                    pr.setDate(4,java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3,"Issued");
                pr.executeUpdate();
                return true;
            }else
                return false;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean returnBook(int idOfCopyOfBook){
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setInt(1,0);
            pr.setInt(2,999);
            pr.setString(3, "In library");
            pr.setDate(4, java.sql.Date.valueOf(LocalDate.of(9999, 1, 1)));
            pr.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean requestReturnBook(int idOfCopyOfBook){
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Status=? WHERE Id_of_copy= " + idOfCopyOfBook);
            pr.setString(1, "Returning");
            pr.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Book bookByID(int id){
        try{
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");
            while (rs.next()) {
                if (rs.getInt(1)==id){
                    Book book = new Book(id,rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9),rs.getInt(10),1);
                    return book;
                }

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete_user_by_id(int user_id) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("user with such id didn't found");
        }
        //есть еще идея удалять по именам,но чтобы библиотекарю вылезло уведомление,мол,может удалиться более 1 юзера
    }

    public static void modify_user(User user) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library " +
                    "SET Name=?,Address=?,Phone_number=?,Type=?,Password=? where Card_number=" + user.get_card_number());
            pr.setString(1, user.get_name());
            pr.setString(2, user.get_adress());
            pr.setString(3, user.get_number());
            pr.setString(4, user.get_type());
            pr.setString(5, user.get_password());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add_CopiesOfMaterial(int id, int number) {
        try {
            if (number > 0) {
                Statement stmt = db.con.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT Id_of_original FROM Copy");
                while (rs.next()) {
                    if (rs.getInt(1) == id) {
                        for (int i = 0; i < number; i++) {
                            PreparedStatement prst = db.con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
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

    public static void add_book(String title, String author, String publisher, String edition, int price, String keyWords, Boolean is_bestseller, boolean reference, int year, int amount) throws SQLException {
        /*
         * To add book, you need to send all information about book
         * ID will be created in DB with auto_increment
         * time_left will be 999 and owner 0 - because only librarian(Id =0) can add books
         * */
        Book book = new Book(title, author, publisher, edition, price, keyWords, is_bestseller, reference, year, "In library");
        db.book_creation(book);
        ArrayList<Integer> arrayList = db.isBookAlreadyExist(book);
        add_CopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    public void add_article(String title, String author, int price, String keyWords, Boolean is_bestseller,
                            boolean reference, String journal, String editor, int yearOfDate, int monthOfDate, int dayOfDate, int amount) throws SQLException {
        /*
         * To add article, you need to send all information about article
         * ID will be created in DB with auto_increment
         * time_left will be 999 and owner 0 - because only librarian(Id =0) can add articles
         * */
        Article article = new Article(title, author, price, keyWords, reference, journal, editor, yearOfDate, monthOfDate, dayOfDate, "In library");
        db.article_creation(article);
        ArrayList<Integer> arrayList = db.isArticleAlreadyExist(article);
        add_CopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    public void add_AV(String title, String author, int price, String keyWords, Boolean is_bestseller, boolean reference, int amount) throws SQLException {
        AV av = new AV(title, author, price, keyWords, "In library");
        db.av_creation(av);
        ArrayList<Integer> arrayList = db.isAVAlreadyExist(av);
        add_CopiesOfMaterial(arrayList.get(1), amount - 1);
    }

    public void delete_AV_by_id(int id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from AV WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AV with such id didn't found");
        }
    }

    public static void deleteCopy(int id){
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_copy=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete_book_by_id(int id) {

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Books WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("book with such id didn't found");
        }
    }

    public void delete_article_by_id(int id) {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Articles WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("article with such id didn't found");
        }
    }

    public void modify_AV(AV av) {

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

    public void modify_article(Article article) {
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

    public static void modify_book(Book book) {
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
            pr.setBoolean(7, book.isIs_bestseller());
            pr.setBoolean(8, book.isReference());
            pr.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Material> get_all_AV() {
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
                AV av = new AV(name, author, price, keyWord);
                avs.add(av);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avs;
    }

    public static ArrayList<Material> get_all_articles() {
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

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                int price = rs.getInt("Price");
                String status = rs.getString("Status");
                String keyWord = rs.getString("Keywords");
                boolean is_reference = rs.getBoolean("is_reference");
                String journal = rs.getString("Journal");
                String editor = rs.getString("Editor");
                int yearDate = rs.getDate("Date").getYear();
                int monthDate = rs.getDate("Date").getMonth();
                int dayDate = rs.getDate("Date").getDay();
                Article article = new Article(name, author, price, keyWord, is_reference, journal, editor, yearDate, monthDate, dayDate, status);
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public static ArrayList<Material> get_all_books() {
        /*
         * Возвращает два ArrayList'а в ArrayList'е, в первом элементе лист с объетками документа
         * во втором элементе количество копий этого документа
         * так и в get_all_articles() и get_all_AV()
         * тип так: |0    ||1 |
         *          ___________
         *       1: |book1|| 4|
         *      2: |book2|| 7|
         *      3: |book3|| 2|
         * */

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
                Book book = new Book(id, name, author, publisher, edition, price, keyWord, is_bestseller, is_reference, year, get_number_of_copies_of_book(id), get_number_of_copies_of_book_with_taken(id));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i <bookWithNumber.get(0).size() ; i++)
//            System.out.println(bookWithNumber.get(0).get(i)+" "+bookWithNumber.get(1).get(i));
        return books;
    }

    public static LinkedList<User> get_all_users() {
        LinkedList<User> users = new LinkedList<User>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Type='Student' or Type='Faculty'");

            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String Phonenumber = rs.getString("Phone_number");
                int id = rs.getInt("Card_number");
                String type = rs.getString("Type");
                String password = rs.getString("Password");
                User user = new User(name, address, Phonenumber, id, type, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //вам для удобства,чтобы видели,что брать
        //for(int i=0;i<users.size();i++){
        // System.out.println(users.get(i).get_name()+" "+users.get(i).get_adress()+
        // " "+users.get(i).get_number()+" "+users.get(i).get_another_card_number()+" "+users.get(i).get_type()+" "+users.get(i).get_password());
        // }


        return users;
    }

    public static int get_number_of_all_copies_taken_by_user(int user_id) {
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

    public static LinkedList<Material> get_all_copies_taken_by_user(int user_id) {
        LinkedList<Material> copies = new LinkedList();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner=" + user_id);
            while (rs.next()) {
                int original_id = rs.getInt("Id_of_original");
                int copy_id = rs.getInt("Id_of_copy");
                String status=rs.getString("Status");
                LocalDate date = rs.getDate("Return_date").toLocalDate();
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
                    Article article = new Article("Article",copy_id, name, author, price, keywords, is_reference, journal, editor, date,status,user_id);
                    copies.add((Material)article);
                }
                Statement AV_stmt = db.con.createStatement();
                ResultSet AV_rs = AV_stmt.executeQuery("SELECT * FROM AV where id=" + original_id);
                while (AV_rs.next()) {
                    String name = AV_rs.getString("Name");
                    String author = AV_rs.getString("Author");
                    int price = AV_rs.getInt("Price");
                    String keywords = AV_rs.getString("Keywords");
                    AV av = new AV("AV",copy_id, name, author, price, keywords,date,status,user_id);
                    copies.add((Material)av);
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
                    Book book = new Book("Book",copy_id, name, author, publisher, edition, price, keywords, is_bestseller, is_reference, year, 0,date,status,user_id);
                    copies.add((Material)book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }


    public static int get_number_of_copies_of_book(int book_id) {
        int copies = 0;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Id_of_original=" + book_id+" and Owner="+0);
            while (rs.next()) {
                copies++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }
    public static int get_number_of_copies_of_book_with_taken(int book_id) {
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
    public static LinkedList<Material> get_all_copies() {
        LinkedList<Material> copies = new LinkedList();
        LinkedList<Integer> attended_id = new LinkedList<>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy");
            while (rs.next()) {
                int owner_id = rs.getInt("Owner");
                attended_id.add(owner_id);
                if (number_of_meetings(attended_id, owner_id)) {
                    LinkedList<Material> owner_copies = get_all_copies_taken_by_user(owner_id);
                    copies.addAll(owner_copies);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }

    private static boolean number_of_meetings(LinkedList<Integer> l, int id) {
        int count = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == id)
                count++;
        }
        if (count > 1) return false;
        else return true;
    }
}