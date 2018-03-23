package main.java.librinno.model;

import javafx.scene.control.Alert;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * database with users in it
 */
public class Database extends Main {

    //for connection to database
    //typical
    public String url = "jdbc:mysql://localhost:3306/dmitrdbk?useSSL=false";
    //public String login = "dmitrDbK";
    public String login =super.getUSER();
    //public String password = "eQ1a5mg0Z7";
    public String password =super.getPASS();
    public static PreparedStatement prst;
    public static Connection con;
    public static boolean is_best_seller;
    public static PriorityQueue<User> pq;
    /**
     * connecting to mysql database
     */
    public Database() {
        prst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
        }
    }

    /**
     * getting users of database to project
     * @param user - user of library
     */
    public static void userCreation(User user) {
        try {
            //get all needed information
            prst = con.prepareStatement("insert into Users_of_the_library(Name, Address, Phone_number,Type,Password) values(?, ?, ?,?,?)");
            prst.setString(1, user.getName());
            prst.setString(2, user.getAdress());
            prst.setString(3, user.getPhoneNumber());
            //prst.setInt(4, user.getCard_Number());
            prst.setString(4,user.getType());
            prst.setString(5,user.getPassword());
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creating AV
     * @param av what to insert into table
     * @throws SQLException
     */
    public void avCreation(AV av)throws SQLException{
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

    /**
     * the same method, but for Articles
     * @param article what article to insert
     * @throws SQLException
     */
    public  void articleCreation(Article article)throws SQLException{
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
            prst.setString(8,article.getDate());
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

    /**
     * the same method,but for book
     *
     * @param book what book to insert
     */
    public  void bookCreation(Book book){
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
                prst.setBoolean(7, book.getBestseller());
                prst.setBoolean(8, book.getReference());
                prst.setInt(9, book.getYear());
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
                prst.setInt(1, id_Of_material);
                prst.setInt(2, 0);
                prst.setInt(3, 999);
                prst.executeUpdate();

            } else {
                //кидаем id оригинала и остальную инфу
                prst = con.prepareStatement("insert into Copy (id_of_original,Owner,Time_left) values(?,?,?)");
                prst.setInt(1, arrayList.get(1));
                prst.setInt(2, 0);
                prst.setInt(3, 999);
                prst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checking for dublicate AVs
     *
     * @param av current av
     * @return arraylist of boolean value and id of av
     * @throws SQLException
     */
    public ArrayList isAVAlreadyExist(AV av) throws SQLException {
        ArrayList<Integer> arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM AV");
        while (rs.next()) {
            if (rs.getString(2).equals(av.getTitle()) && rs.getString(3).equals(av.getAuthor()) && rs.getInt(4) == av.getPrice() &&
                    rs.getString(5).equals(av.getKeyWords())) {
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }

    /**
     * checking for dublicate articles
     *
     * @param article current article
     * @return arraylist of boolean value and id of article
     * @throws SQLException
     */
    public ArrayList isArticleAlreadyExist(Article article) throws SQLException {
        /*
         * Этот метод чекает, есть ли у нас в библиотеке такая статья
         * возвращает лист, В первом элементе: int 1 - если да(уже есть такая статья), 0 - если нет
         * на втором элементе возвращается ID найденной статьи(если она уже есть. В противном случае ничего там нет)
         * */
        ArrayList<Integer> arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Articles");
        while (rs.next()) {
            if (rs.getString(2).equals(article.getTitle()) && rs.getString(3).equals(article.getAuthor()) && rs.getInt(4) == article.getPrice() &&
                    rs.getString(5).equals(article.getKeyWords()) && rs.getBoolean(6) == article.getReference()
                    && rs.getString(7).equals(article.getJournal()) && rs.getString(8).equals(article.getEditor()) && rs.getString(9).equals(article.getDate())) {
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }

    /**
     * checking for dublicate books
     *
     * @param book current book
     * @return arraylist of boolean value and id of book
     * @throws SQLException
     */
    public ArrayList isBookAlreadyExist(Book book) throws SQLException {
        /*
         * Этот метод проверяет, есть ли у нас в библиотеке такая книга
         * возвращает лист, В первом элементе: int 1 - если да(уже есть такая книга), 0 - если нет
         * на втором элементе возвращается ID найденной книги(если она уже есть, в противном случае ничего там нет)
         * */
        ArrayList<Integer> arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Books");
        while (rs.next()) {
            if (rs.getString(2).equals(book.getTitle()) && rs.getString(3).equals(book.getAuthor()) && rs.getString(4).equals(book.getPublisher())
                    && rs.getString(5).equals(book.getEdition()) && rs.getInt(6) == book.getPrice() && rs.getString(7).equals(book.getKeyWords())
                    && rs.getBoolean(8) == book.getBestseller() && rs.getBoolean(9) == book.getReference() && rs.getInt(10) == book.getYear()) {
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(1));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }

    /**
     * checking for created users
     *
     * @param user current user
     * @return arraylist of boolean value and id
     * @throws SQLException
     */
    public static ArrayList isUserAlreadyExist(User user) throws SQLException {
        /*
         * Этот метод чекает, есть ли у нас в библиотеке такая книга
         * возвращает лист, В первом элементе: int 1 - если да(уже есть такая книга), 0 - если нет
         * на втором элементе возвращается ID найденной книги(если она уже есть, в противном случае ничего там нет)
         * */
        ArrayList<Integer> arrayList = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM Users_of_the_library");
        while (rs.next()) {
            if (rs.getString(1).equals(user.getName()) && rs.getString(2).equals(user.getAdress())
                    && rs.getString(3).equals(user.getPhoneNumber())
                    && rs.getString(5).equals(user.getType()) && rs.getString(6).equals(user.getPassword())) {
                arrayList.add(1); // 1 = true
                arrayList.add(rs.getInt(4));//save ID of founded book
                return arrayList;
            }
        }
        arrayList.add(0);
        return arrayList;
    }

    /**
     * gets information about users
     *
     * @param id user's id
     * @return user
     * @throws SQLException
     */
    public User getInformationAboutTheUser(int id) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Card_number=" + id);
        String name = "";
        String address = "";
        String Phonenumber = "";
        String type = "";
        String password = "";
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

    /**
     * authorization of user
     *
     * @param id   it's id
     * @param pass password
     * @return type of user
     * @throws SQLException
     */
    public String authorization(int id, String pass) throws SQLException {

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT  * FROM Users_of_the_library WHERE Card_number=" + id);

        if (rs.next()) {
            String password = rs.getString("Password");
            if (pass.equals(password)) {
                return rs.getString("Type");
            }
        }
        return "";
    }

    public static void creationLocalDB(String user, String pass) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, user, pass);
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS dmitrDbK";
            stmt.executeUpdate(sql);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dmitrdbk?useSSL=false", user, pass);
            stmt = conn.createStatement();

            //create table Books
            sql = "CREATE  TABLE IF NOT EXISTS Books(id INT(255) AUTO_INCREMENT , Name VARCHAR(255) , Author VARCHAR(255) , Publisher VARCHAR(255) ," +
                    " Edition VARCHAR(255) , Price INT(255) , Keywords VARCHAR(255) , is_bestseller TINYINT(1) , is_reference TINYINT(1) , Year INT(11),PRIMARY KEY(id))";
            stmt.executeUpdate(sql);

            //create table Copy
            sql = "CREATE TABLE IF NOT EXISTS Copy(Id_of_copy INT(255) AUTO_INCREMENT,Id_of_original INT(255) , Owner INT(255) , " +
                    "Time_left int(11) , Status VARCHAR(255) default 'In library', Return_date DATE DEFAULT '9999-01-01', PRIMARY KEY(Id_of_copy) )";
            stmt.executeUpdate(sql);

            //create table Users
            sql = "CREATE TABLE IF NOT EXISTS Users_of_the_library(Name VARCHAR(30) , Address VARCHAR(30) , Phone_number VARCHAR(255) , Card_number int(255) AUTO_INCREMENT ," +
                    " Type VARCHAR(30), Password VARCHAR(30) , PRIMARY KEY(Card_number))";
            stmt.executeUpdate(sql);

            //create table Articles
            sql = "CREATE TABLE IF NOT EXISTS Articles(id int(255) AUTO_INCREMENT, Name VARCHAR(255),Author VARCHAR(255),Price INT(11), Keywords VARCHAR(255),is_reference tinyint(1)," +
                    "Journal VARCHAR(255),Editor VARCHAR(255),Date VARCHAR(255),PRIMARY KEY(id) )";
            stmt.executeUpdate(sql);

            //create table AV
            sql = "CREATE TABLE IF NOT EXISTS AV(id int(255) AUTO_INCREMENT, Name VARCHAR(255), Author varchar(255),Price int(255),Keywords VARCHAR(255),PRIMARY KEY(id))";
            stmt.executeUpdate(sql);
            sql = "INSERT IGNORE INTO Users_of_the_library (Name,Address,Phone_number,Card_number,Type,Password) VALUES ('Librarian', 'Russia', '+79999999999',33,'Librarian','124')";
            stmt.executeUpdate(sql);
            sql = "INSERT IGNORE INTO Users_of_the_library (Name,Address,Phone_number,Card_number,Type,Password) VALUES ('Albert Einstein', 'Princeton, New Jersey, U.S.', '+79999999999',32,'Student','1')";
            stmt.executeUpdate(sql);
            sql = "INSERT IGNORE INTO Users_of_the_library (Name,Address,Phone_number,Card_number,Type,Password) VALUES ('Nikolay V. Shilov', 'Innopolis', '+79999999999',31,'Professor','1')";
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void queue_on_material(int material_id, int user_id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            Librarian l = new Librarian("1", "1", "1", 0, "Librarian", "1");
            Comparator<User> comparator = new UserTypeComparator();
            pq = new PriorityQueue<User>(l.getAllUsers().size(), comparator);
            User user = l.UserById(user_id);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");
            Calendar cal = Calendar.getInstance();
            User needed_user=new User(user.getCard_number(),user.getType(),dateFormat.format(cal.getTime()));
            pq.add(needed_user);
            String query = "SELECT * FROM Queue_on_" + material_id;
            ResultSet rs = null;
            String sql = null;
            try {
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int cur_id = rs.getInt("Card_number");
                    if (cur_id != user_id){
                        user=new User(cur_id,rs.getString("Type"),rs.getString("Reserving_time"));
                        pq.add(user);}
                    else{
                        //Alert error = new Alert(Alert.AlertType.INFORMATION);
                       // error.setHeaderText("Document");
                       // error.setContentText("You already reserved this book.");
                       // error.showAndWait();
                        System.out.println("you are already in queue");
                    }
                    PreparedStatement pr = con.prepareStatement("TRUNCATE Queue_on_" + material_id);
                    pr.executeUpdate();
                }
            } catch (SQLException exc) {
                sql = "CREATE TABLE IF NOT EXISTS Queue_on_" + material_id + "(Card_number int(255) ," +
                        " Type VARCHAR(30), Reserving_time VARCHAR(30))";
                stmt.executeUpdate(sql);
            }
            while (pq.size() > 0) {
                User cur_user = pq.poll();
                sql = "INSERT INTO Queue_on_" + material_id + " (Card_number,Type,Reserving_time) " +
                        "VALUES ('" + cur_user.getCard_number() + "', '" + cur_user.getType() + "', '" + cur_user.getDate() + "')";
                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserTypeComparator implements Comparator<User> {
    @Override
    //это пиздец как костыльно и я не ебу как это работает
    //....
    //но это частично работает
    //ОНО РАБОТАЕТ!!!! КАК????
    public int compare(User x, User y) {
        if (Math.abs(x.getType().length() - y.getType().length()) == 3 ||
                Math.abs(x.getType().length() - y.getType().length()) == 2)
            return y.getType().compareTo(x.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 5 ||
                Math.abs(x.getType().length() - y.getType().length()) == 11)
            return x.getType().compareTo(y.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 8 || Math.abs(x.getType().length() - y.getType().length()) == 1)
            return x.getType().compareTo(y.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 7)
            return y.getType().compareTo(x.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 16)
            return x.getType().compareTo(y.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 9)
            return y.getType().compareTo(x.getType());
        else if (Math.abs(x.getType().length() - y.getType().length()) == 0) {
            return x.getDate().compareTo(y.getDate());
        }
        return -1;
    }
}
