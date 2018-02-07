package main.java.librinno.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            prst = con.prepareStatement("insert into Users_of_the_library(Name, Address, Phone_number, Card_number,Type,Password) values(?, ?, ?, ?,?,?)");
            prst.setString(1, user.get_name());
            prst.setString(2, user.get_number());
            prst.setString(3, user.get_adress());
            prst.setInt(4, user.get_card_number());
            prst.setString(5,user.get_type());
            prst.setString(6,user.get_password());
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void book_creation(Book book){
        try {
            prst=con.prepareStatement("insert into Books(id,Name,Author,Publisher,Edition,Price,Keywords,owner,is_bestseller,time_left) values(?,?,?,?,?,?,?,?,?,?)");
            prst.setInt(1,book.getId());
            prst.setString(2,book.getTitle());
            prst.setString(3,book.getAuthor());
            prst.setString(4,book.getPublisher());
            prst.setInt(5,book.getEdition());
            prst.setInt(6,book.getPrice());
            prst.setString(7,book.getKeyWords());
            //owner
            prst.setInt(8,0);
            prst.setBoolean(9,book.get_is_bestseller());
            prst.setInt(10,book.get_left_time());
            //еще owner
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}