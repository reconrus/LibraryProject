package main.java.librinno.model;


import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        //launching database and enter user in it
         //ATTENTION!!!! ALARM!!!!
        //YOU HAVE TO ENTER YOUR INFORMATION IN 1 MINUTE!!!!!!
        //ONLY 1 MINUTE!!!!
        //1 MINUTE!!


        //it is typical version of creation
        //use for project
        //enter information which will be put in database

        Database db = new Database();

        test_1();
        test_3();

    }


    private static void test_1() throws SQLException{
        defaultTable();
        System.out.println("Test 1");

        printUsers();
        printBooks();

        Booking booking=new Booking();
        if(booking.check_out(1,2)) System.out.println("Ilnur tries to check out Touch of Class \nChecking out was successful \n\n");

        printBooks();
        System.out.println("\n");
    }


    private static void test_3() throws SQLException{
        defaultTable();
        System.out.println("Test 3");

        printUsers();
        printBooks();

        Booking booking=new Booking();
        if(booking.check_out(2,2)) System.out.println("Shilov tries to check out Touch of Class \nChecking out was successful \n\n");

        printBooks();
        System.out.println("\n");
    }


    private static void printBooks() throws SQLException{
        Statement ps2 = Database.con.createStatement();
        ResultSet rs = ps2.executeQuery("SELECT * from Books");

        System.out.println("Books \n");
        System.out.println("id       Name                Author          Is Bestseller       Owner       Days left");

        while(rs.next()){
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String author = rs.getString(3);
            String isBestseller;
            int owner = rs.getInt(8);
            if(rs.getInt(9) == 0) isBestseller = "False";
            else isBestseller = "True";
            int days = rs.getInt(10);

            System.out.println(id + "       " + name +"       " + author + "          " + isBestseller+"                " + owner+"             "+days);
        }
        System.out.println("\n");
    }


    private static void printUsers() throws SQLException{
        Statement ps2 = Database.con.createStatement();
        ResultSet rs = ps2.executeQuery("SELECT * from Users_of_the_library");

        System.out.println("Users in the system \n");
        System.out.println("ID       Name       Type");

        while(rs.next()){
            int id = rs.getInt(4);
            String name = rs.getString(1);
            String type = rs.getString(5);

            System.out.println(id + "       " + name +"       " + type);
        }

        System.out.println("\n");
    }


    private static void defaultTable() throws SQLException{

        PreparedStatement set = Database.con.prepareStatement("Update Books set time_left=?, owner=?");
        set.setInt(1, 999);
        set.setInt(2, 0);
        set.executeUpdate();
        set.close();
    }



}
