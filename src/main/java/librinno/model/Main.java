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

        //db.user_creation(user);
        Database db = new Database();
        //test_1();
        //User user = new User();
       // db.user_creation(user);
        //Book book= new Book();
        //db.book_creation(book);
    }


    public static void test_1() {
        Database db = new Database();
        User user=new User("1234","1234","1234","1234","1234");
        db.user_creation(user);
        Book book = new Book("1234","1234","1234",1234,1234,"1234",true,1234);
        db.book_creation(book);
    }
}
