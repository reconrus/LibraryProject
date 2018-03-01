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
        Librarian l=new Librarian();
        db.user_creation(l);
        System.out.println("Creating user:");
        //l.add_user();
        //l.add_book();
        //l.modify_book(100);
        l.get_all_users();
    }


}
