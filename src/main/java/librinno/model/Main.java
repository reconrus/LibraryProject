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
        Tests test=new Tests();
        test.dump();
        test.tc1();
        System.out.println("TC1 SUCCESS");
        test.dump();
        test.tc2();
        System.out.println("TC2 SUCCESS");
        test.dump();
        test.tc3();
        System.out.println("TC3 SUCCESS");
        test.dump();
        test.tc4();
        System.out.println("TC4 SUCCESS");
        test.dump();
        test.tc5();
        System.out.println("TC5 SUCCESS");
        test.dump();
        test.tc6();
        System.out.println("TC6 SUCCESS");
        test.dump();
        test.tc7();
        System.out.println("TC7 SUCCESS");
        test.dump();
        test.tc8();
        System.out.println("TC8 SUCCESS");
        test.dump();
    }




}
