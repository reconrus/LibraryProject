package main.java.librinno.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * database with users in it
 */
public class Database {
    //for connection to database
    //typical
    public String url = "jdbc:mysql://triniti.ru-hoster.com/dmitrDbK";
    public String login = "dmitrDbK";
    public String password = "eQ1a5mg0Z7";
    public static PreparedStatement prst;
    public static Connection con;
    public static boolean is_best_seller;
    /**
     * connecting to mysql database
     */
    public Database() {
        prst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getting users of database to project
     * @param user - user of library
     */
    public static void user_creation(User user) {
        try {
            //get all needed information
            prst = con.prepareStatement("insert into Users_of_the_library(Name, Address, Phone_number,Type,Password) values(?, ?, ?,?,?)");
            prst.setString(1, user.get_name());
            prst.setString(2, user.get_adress());
            prst.setString(3, user.get_number());
            //prst.setInt(4, user.get_card_number());
            prst.setString(4,user.get_type());
            prst.setString(5,user.get_password());
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void av_creation(AV av)throws SQLException{
        ArrayList <Integer>arrayList =isAVAlreadyExist(av);
        if (arrayList.get(0)==0) {
            prst = con.prepareStatement("insert into AV(Name,Author,Price,Keywords) values(?,?,?,?)");
            prst.setString(1, av.getTitle());
            prst.setString(2, av.getAuthor());
            prst.setInt   (3, av.getPrice());
            prst.setString(4, av.getKeyWords());
            prst.executeUpdate();

            Statement stmt = con.createStatement();
            ResultSet rsInside;
            rsInside = stmt.executeQuery("SELECT id FROM AV");
            int id_Of_material = 0;
            while (rsInside.next())
                id_Of_material = rsInside.getInt(1);
            prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");

            prst.setInt(1,id_Of_material);
            prst.setInt(2,0);
            prst.setInt(3,999);
            prst.executeUpdate();
        }else{
            prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
            prst.setInt(1,arrayList.get(1));
            prst.setInt(2,0);
            prst.setInt(3,999);
            prst.executeUpdate();
        }
    }
    public  void article_creation(Article article)throws SQLException{
        /*
        * Сперва чекаем есть ли у нас уже такая статья,
        * если есть то запоминаем ее ID и просто добавляе копию в таблицу копий
        * если нет, то добавляем в таблицу Articles описание и одну копию в таблицу копий
        * */
        ArrayList <Integer>arrayList =isArticleAlreadyExist(article);
        if (arrayList.get(0)==0) {
            prst = con.prepareStatement("insert into Articles(Name,Author,Price,Keywords,is_reference,Journal,Editor,Date) values(?,?,?,?,?,?,?,?)");
            prst.setString(1, article.getTitle());
            prst.setString(2, article.getAuthor());
            prst.setInt   (3, article.getPrice());
            prst.setString(4, article.getKeyWords());
            prst.setBoolean(5, article.getReference());
            prst.setString(6,article.getJournal());
            prst.setString(7,article.getEditor());
            prst.setDate(8,java.sql.Date.valueOf(article.getDate()));
            prst.executeUpdate();
            //находим последний добавленный ID статьи и запоминаем его, чтоб потом кинуть его в таблицу копий
            Statement stmt = con.createStatement();
            ResultSet rsInside;
            rsInside = stmt.executeQuery("SELECT id FROM Articles");
            int id_Of_material = 0;
            while (rsInside.next())
                id_Of_material = rsInside.getInt(1);
            prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");

            prst.setInt(1,id_Of_material);
            prst.setInt(2,0);
            prst.setInt(3,999);
            prst.executeUpdate();
        }else{
            prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
            prst.setInt(1,arrayList.get(1));
            prst.setInt(2,0);
            prst.setInt(3,999);
            prst.executeUpdate();
        }
    }
    public  void book_creation(Book book){
        /*
        * Сперва чекаем есть ли у нас уже такая книга,
        * если есть то запоминаем ее ID и просто добавляе копию в таблицу копий
        * если нет, то добавляем в таблицу Book описание и одну копию в таблицу копий
        * */
        try {
            ArrayList <Integer>arrayList =isBookAlreadyExist(book);
            if (arrayList.get(0)==0) {
                prst = con.prepareStatement("insert into Books(Name,Author,Publisher,Edition,Price,Keywords,is_bestseller,is_reference,YEAR) values(?,?,?,?,?,?,?,?,?)");
                prst.setString(1, book.getTitle());
                prst.setString(2, book.getAuthor());
                prst.setString(3, book.getPublisher());
                prst.setString   (4, book.getEdition());
                prst.setInt   (5, book.getPrice());
                prst.setString(6, book.getKeyWords());
                prst.setBoolean(7, book.isIs_bestseller());
                prst.setBoolean(8, book.isReference());
                prst.setInt    (9, book.getYear());
                prst.executeUpdate();
                //находим последний добавленный ID книги и запоминаем его, чтоб потом кинуть его в таблицу копий
                Statement stmt = con.createStatement();
                ResultSet rsInside;
                rsInside = stmt.executeQuery("SELECT id FROM Books");
                int id_Of_material = 0;
                while (rsInside.next())
                    id_Of_material = rsInside.getInt(1);
                //кидайем необходимую инфу в таблицу копий(id оригинала,id хозяина(так как мы только доавили, то это Admin(id=0), и время =999, так как книга в бибиотеке))
                prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
                prst.setInt(1,id_Of_material);
                prst.setInt(2,0);
                prst.setInt(3,999);
                prst.executeUpdate();

            }else{
                //кидаем id оригинала и остальную инфу
                prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
                prst.setInt(1,arrayList.get(1));
                prst.setInt(2,0);
                prst.setInt(3,999);
                prst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList isAVAlreadyExist(AV av)throws SQLException{
        ArrayList <Integer>arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM AV");
        while (rs.next()){
            if (rs.getString(2).equals(av.getTitle()) && rs.getString(3).equals(av.getAuthor()) && rs.getInt(4) == av.getPrice() &&
                    rs.getString(5).equals(av.getKeyWords())){
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }
    public ArrayList isArticleAlreadyExist(Article article)throws SQLException{
        /*
        * Этот метод чекает, есть ли у нас в библиотеке такая статья
        * возвращает лист, В первом элементе: int 1 - если да(уже есть такая статья), 0 - если нет
        * на втором элементе возвращается ID найденной статьи(если она уже есть. В противном случае ничего там нет)
        * */
        ArrayList <Integer>arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Articles");
        while (rs.next()){
            if (rs.getString(2).equals(article.getTitle()) && rs.getString(3).equals(article.getAuthor()) && rs.getInt(4) == article.getPrice() &&
            rs.getString(5).equals(article.getKeyWords())  && rs.getBoolean(6) == article.getReference()
            && rs.getString(7).equals(article.getJournal()) && rs.getString(8).equals(article.getEditor()) && rs.getDate(9).toLocalDate().equals(article.getDate())){
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }
    public ArrayList isBookAlreadyExist(Book book) throws SQLException {
        /*
        * Этот метод чекает, есть ли у нас в библиотеке такая книга
        * возвращает лист, В первом элементе: int 1 - если да(уже есть такая книга), 0 - если нет
        * на втором элементе возвращается ID найденной книги(если она уже есть, в противном случае ничего там нет)
        * */
        ArrayList <Integer>arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Books");
        while (rs.next()){
            if (rs.getString(2).equals(book.getTitle()) && rs.getString(3).equals(book.getAuthor()) && rs.getString(4).equals(book.getPublisher())
            && rs.getString(5).equals(book.getEdition()) && rs.getInt(6) == book.getPrice() && rs.getString(7).equals(book.getKeyWords())
                    && rs.getBoolean(8) == book.isIs_bestseller() && rs.getBoolean(9) == book.isReference() && rs.getInt(10) == book.getYear()){
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }
    public ArrayList isUserAlreadyExist(User user) throws SQLException {
        /*
         * Этот метод чекает, есть ли у нас в библиотеке такая книга
         * возвращает лист, В первом элементе: int 1 - если да(уже есть такая книга), 0 - если нет
         * на втором элементе возвращается ID найденной книги(если она уже есть, в противном случае ничего там нет)
         * */
        ArrayList <Integer>arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Users_of_the_library");
        while (rs.next()){
            if (rs.getString(1).equals(user.get_name()) && rs.getString(2).equals(user.get_adress())
                    && rs.getString(3).equals(user.getPhone_Number())
                    && rs.getString(5).equals(user.get_type()) && rs.getString(6).equals( user.get_password())){
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(4));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }
    public User get_information_about_the_user (int id) throws SQLException{
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Card_number="+id);
        String name="";
        String address="";
        String Phonenumber="";
        String type="";
        String password="";
        while (rs.next()) {
            name = rs.getString("Name");
            address = rs.getString("Address");
            Phonenumber = rs.getString("Phone_number");
            type = rs.getString("Type");
            password = rs.getString("Password");
        }
        User user = new User(name, address, Phonenumber, id, type, password);
        return user;
    }

    public String authorization(int id, String pass) throws SQLException {

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT  * FROM Users_of_the_library WHERE Card_number="+id);

        if(rs.next()) {
            String password = rs.getString("Password");
            if(pass.equals(password)){
                return rs.getString("Type");
            }
        }
        return "";
    }
}