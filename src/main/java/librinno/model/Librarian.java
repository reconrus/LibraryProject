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

    public static void delete_user_by_id(int user_id) {
        try {
            Database db= new Database();
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

    public static void add_CopiesOfMaterial(int id,int number){
        try {if (number>0) {
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add_book(String title, String author, String publisher, int edition, int price, String keyWords, Boolean is_bestseller,boolean reference,int year,int amount) throws SQLException {
        /*
        * To add book, you need to send all information about book
        * ID will be created in DB with auto_increment
        * time_left will be 999 and owner 0 - because only librarian(Id =0) can add books
        * */
        Book book = new Book(title,author,publisher,edition,price,keyWords,is_bestseller,reference,year);
        db.book_creation(book);
        ArrayList<Integer> arrayList=db.isBookAlreadyExist(book);
        add_CopiesOfMaterial(arrayList.get(1),amount-1);
    }
    public void add_article(String title,String author,int price, String keyWords, Boolean is_bestseller,
                            boolean reference,String journal,String editor,int yearOfDate,int monthOfDate,int dayOfDate,int amount) throws SQLException {
        /*
        * To add article, you need to send all information about article
        * ID will be created in DB with auto_increment
        * time_left will be 999 and owner 0 - because only librarian(Id =0) can add articles
        * */
        Article article = new Article(title,author,price,keyWords,is_bestseller,reference,journal,editor,yearOfDate,monthOfDate,dayOfDate);
        db.article_creation(article);
        ArrayList<Integer> arrayList=db.isArticleAlreadyExist(article);
        add_CopiesOfMaterial(arrayList.get(1),amount-1);
    }

    public void add_AV(String title,String author,int price, String keyWords, Boolean is_bestseller,boolean reference,int amount)throws SQLException{
        AV av = new AV(title,author,price,keyWords,is_bestseller,reference);
        db.av_creation(av);
        ArrayList<Integer> arrayList=db.isAVAlreadyExist(av);
        add_CopiesOfMaterial(arrayList.get(1),amount-1);
    }

    public void delete_AV_by_id(int id){
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from AV WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AV with such id didn't found");
        }
    }
    public void delete_book_by_id(int id) {

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Books WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("book with such id didn't found");
        }
    }
    public void delete_article_by_id(int id){
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Articles WHERE id=" + id);
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Copy WHERE Id_of_original=" + id);
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("article with such id didn't found");
        }
    }

    public void modify_AV(AV av){

        try{
            PreparedStatement pr = db.con.prepareStatement("UPDATE AV " +
                    "SET Name=?,Author=?,Price=?,Keywords=?,is_bestseller=?,is_reference=? where id="+ av.getId());
            pr.setString(1, av.getTitle());
            pr.setString(2, av.getAuthor());
            pr.setInt(3, av.getPrice());
            pr.setString(4, av.getKeyWords());
            pr.setBoolean(5, av.isIs_bestseller());
            pr.setBoolean(6,av.isReference());
            pr.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void modify_article(Article article){
        try{
            PreparedStatement pr = db.con.prepareStatement("UPDATE Articles " +
                    "SET Name=?,Author=?,Price=?,Keywords=?,is_bestseller=?,is_reference=?,Journal=?,Editor=?,Date=? where id=" + article.getId());
            pr.setString(1, article.getTitle());
            pr.setString(2, article.getAuthor());
            pr.setInt(3, article.getPrice());
            pr.setString(4, article.getKeyWords());
            pr.setBoolean(5, article.isIs_bestseller());
            pr.setBoolean(6,article.isReference());
            pr.setString(7,article.getJournal());
            pr.setString(8,article.getEditor());
            pr.setDate(9,java.sql.Date.valueOf(article.getDate()));
            pr.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void modify_book(Book book) {
        try {
            PreparedStatement pr = db.con.prepareStatement("UPDATE Books " +
                    "SET Name=?,Author=?,Publisher=?,Edition=?,Price=?,Keywords=?,is_bestseller=?,is_reference=? where id="+book.getId());
            pr.setString(1, book.getTitle());
            pr.setString(2, book.getAuthor());
            pr.setString(3, book.getPublisher());
            pr.setInt(4, book.getEdition());
            pr.setInt(5, book.getPrice());
            pr.setString(6, book.getKeyWords());
            pr.setBoolean(7, book.isIs_bestseller());
            pr.setBoolean(8, book.isReference());
            pr.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public ArrayList get_all_AV(){
        ArrayList<ArrayList> AVWithNumber = new ArrayList<ArrayList>();
        ArrayList<AV> avs = new ArrayList<AV>();
        ArrayList<Integer> numberOfAV = new ArrayList<Integer>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AV");
            while (rs.next()){

                int number=0;
                int id=rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()){
                    if (rs2.getInt("id_of_original") == id){
                        number++;
                    }
                }

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                int price =rs.getInt("Price");
                String keyWord = rs.getString("Keywords");
                boolean is_bestseller = rs.getBoolean("is_bestseller");
                boolean is_reference = rs.getBoolean("is_reference");
                AV av = new AV(name,author,price,keyWord,is_bestseller,is_reference);
                avs.add(av);
                numberOfAV.add(number);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        AVWithNumber.add(avs);
        AVWithNumber.add(numberOfAV);
        return AVWithNumber;
    }
    public ArrayList get_all_articles(){
        ArrayList<ArrayList> articleWithNumber = new ArrayList();
        ArrayList<Article> articles = new ArrayList<Article>();
        ArrayList<Integer> numberOfArticle = new ArrayList<Integer>();
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Articles");
            while (rs.next()){
                int number=0;
                int id=rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()){
                    if (rs2.getInt("id_of_original") == id){
                        number++;
                    }
                }

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                int price =rs.getInt("Price");
                String keyWord = rs.getString("Keywords");
                boolean is_bestseller = rs.getBoolean("is_bestseller");
                boolean is_reference = rs.getBoolean("is_reference");
                String journal = rs.getString("Journal");
                String editor = rs.getString("Editor");
                int yearDate =rs.getDate("Date").getYear();
                int monthDate =rs.getDate("Date").getMonth();
                int dayDate =rs.getDate("Date").getDay();
                Article article = new Article(name,author,price,keyWord,is_bestseller,is_reference,journal,editor,yearDate,monthDate,dayDate);
                articles.add(article);
                numberOfArticle.add(number);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        articleWithNumber.add(articles);
        articleWithNumber.add(numberOfArticle);
        return articleWithNumber;
    }
    public static ArrayList<Book> get_all_books(){
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

        ArrayList<Book> books = new ArrayList<Book>();

        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");
            while (rs.next()){

                int number=0;
                int id=rs.getInt("id");
                Statement stmt2 = db.con.createStatement();
                ResultSet rs2;
                rs2 = stmt2.executeQuery("SELECT * FROM Copy");
                while (rs2.next()){
                    if (rs2.getInt("id_of_original") == id){
                        number++;
                    }
                }

                String name = rs.getString("Name");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                int edition = rs.getInt("Edition");
                int price =rs.getInt("Price");
                String keyWord = rs.getString("Keywords");
                boolean is_bestseller = rs.getBoolean("is_bestseller");
                boolean is_reference = rs.getBoolean("is_reference");
                int year = rs.getInt("Year");
                Book book = new Book(id,name,author,publisher,edition,price,keyWord,is_bestseller,is_reference,year);
                books.add(book);
            }
        }catch (SQLException e) {
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
        int copies=0;
        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Copy where Owner="+user_id);
            while (rs.next()){
                copies++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }
}
