package main.java.librinno.model;


import java.sql.SQLException;

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
        Booking booking=new Booking();
        booking.check_out(108,12);
    }


    public void test_1() {
        Database db = new Database();
        Patron patron=new Patron();
        Librarian librarian=new Librarian();
        db.user_creation(patron);
        db.user_creation(librarian);
        Book book = new Book();
        db.book_creation(book);
    }
}
