package main.java.librinno.model;

import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;


public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/";

    private static String USER;
    private static String PASS;

    public final String getPASS() {
        return PASS;
    }
    public static void setPASS(String PASS) {
        Main.PASS = PASS;
    }
    public final String getUSER() {
        return USER;
    }
    public static void setUSER(String USER) {
        Main.USER = USER;
    }

    public static void main(String[] args) throws SQLException {

        //We need know your login and password in local server MySQL
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your login in MySQL:");
        setUSER(sc.nextLine());
        System.out.println("Write your password in MySQL:");
        setPASS(sc.nextLine());

        creationLocalDB();

        //Librarian l = new Librarian("1", "1", "1", 1, "1", "1");
        //l.addBook("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "MIT Press", "Third edition", 1, "b1", false, false, 2009,3);
        //launching database and enter user in it
        //ATTENTION!!!! ALARM!!!!
        //YOU HAVE TO ENTER YOUR INFORMATION IN 1 MINUTE!!!!!!
        //ONLY 1 MINUTE!!!!
        //1 MINUTE!!


        //it is typical version of creation
        //use for project
        //enter information which will be put in database
        //Database db = new Database();
        //Librarian l = new Librarian();
        //l.getAllUsers();
       //System.out.println(l.getAllCopiesTakenByUser(1));
       // System.out.println(l.getAllCopiesTakenByUser(1));
       // for (int i = 0; i < Librarian.getAllCopiesTakenByUser(1).size(); i++) {
       //        System.out.println(Librarian.getAllCopiesTakenByUser(1).get(i).getType());
       // }
        //l.deleteUserById(35);
        //System.out.println(l.avById(201).getAuthor());
      //  System.out.println(l.getAllCopies().size());
       // System.out.println(l.getAllCopies());

//        Tests test=new Tests();
//        test.dump();
//        test.tc1();
//        System.out.println("TC1 SUCCESS");
//        test.dump();
//        test.tc2();
//        System.out.println("TC2 SUCCESS");
//        test.dump();
//        test.tc3();
//        System.out.println("TC3 SUCCESS");
//        test.dump();
//        test.tc4();
//        System.out.println("TC4 SUCCESS");
//        test.dump();
//        test.tc5();
//        System.out.println("TC5 SUCCESS");
//        test.dump();
//        test.tc6();
//        System.out.println("TC6 SUCCESS");
//        test.dump();
//        test.tc7();
//        System.out.println("TC7 SUCCESS");
//        test.dump();
//        test.tc8();
//        System.out.println("TC8 SUCCESS");
//        test.dump();



    }
    public static void creationLocalDB(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS dmitrDbK";
            stmt.executeUpdate(sql);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dmitrdbk?useSSL=false",USER,PASS);
            stmt = conn.createStatement();

            //create table Books
            sql="CREATE  TABLE IF NOT EXISTS Books(id INT(255) AUTO_INCREMENT , Name VARCHAR(255) , Author VARCHAR(255) , Publisher VARCHAR(255) ," +
                    " Edition VARCHAR(255) , Price INT(255) , Keywords VARCHAR(255) , is_bestseller TINYINT(1) , is_reference TINYINT(1) , Year INT(11),PRIMARY KEY(id))";
            stmt.executeUpdate(sql);

            //create table Copy
            sql="CREATE TABLE IF NOT EXISTS Copy(Id_of_copy INT(255) AUTO_INCREMENT,Id_of_original INT(255) , Owner INT(255) , " +
                    "Time_left int(11) , Status VARCHAR(255) default 'In library', Return_date DATE DEFAULT '9999-01-01', PRIMARY KEY(Id_of_copy) )";
            stmt.executeUpdate(sql);

            //create table Users
            sql="CREATE TABLE IF NOT EXISTS Users_of_the_library(Name VARCHAR(30) , Address VARCHAR(30) , Phone_number VARCHAR(255) , Card_number int(255) AUTO_INCREMENT ," +
                    " Type VARCHAR(30), Password VARCHAR(30) , PRIMARY KEY(Card_number))";
            stmt.executeUpdate(sql);

            //create table Articles
            sql="CREATE TABLE IF NOT EXISTS Articles(id int(255) AUTO_INCREMENT, Name VARCHAR(255),Author VARCHAR(255),Price INT(11), Keywords VARCHAR(255),is_reference tinyint(1)," +
                    "Journal VARCHAR(255),Editor VARCHAR(255),Date VARCHAR(255),PRIMARY KEY(id) )";
            stmt.executeUpdate(sql);

            //create table AV
            sql="CREATE TABLE IF NOT EXISTS AV(id int(255) AUTO_INCREMENT, Name VARCHAR(255), Author varchar(255),Price int(255),Keywords VARCHAR(255),PRIMARY KEY(id))";
            stmt.executeUpdate(sql);

        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }



}
