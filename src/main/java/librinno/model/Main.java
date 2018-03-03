package main.java.librinno.model;


import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        Librarian l = new Librarian();
        //l.get_all_users();
        //System.out.println(l.get_all_copies_taken_by_user(1));
        // System.out.println(l.get_all_copies_taken_by_user(1));

        //for (int i = 0; i < l.get_all_copies_taken_by_user(1).size(); i++) {
        //    try {
        //        AV av = AV.class.cast(l.get_all_copies_taken_by_user(1).get(i));
        //        System.out.println(av.getId()+" "+av.getTitle());
        //    } catch (ClassCastException e) {
        //        Book book = Book.class.cast(l.get_all_copies_taken_by_user(1).get(i));
        //        System.out.println(book.getId());
        //    }
        //}
    }


}
