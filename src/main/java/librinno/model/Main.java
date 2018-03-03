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
        Librarian l=new Librarian();
        //db.user_creation(l);
        //System.out.println("Creating user:");
        //l.add_user();
        //l.add_AV("TitleTest1","AuthorTest1",1,"KeyWords1",true,true,1);
        //l.delete_book_by_id(107);
        //System.out.println(l.get_all_articles());
        //System.out.println(l.get_all_AV());
//        ArrayList <ArrayList>arrayList =l.get_all_AV();
//        for (int i = 0; i <arrayList.get(0).size() ; i++)
//            System.out.println(arrayList.get(0).get(i)+" "+arrayList.get(1).get(i));
        //l.add_book("TitleTest3","AuthorTest3","PublisherTest3",3,3,"KeyWordTest3",true,true,2022,2);
        //l.add_article("TitleTest1","AuthorTest1",1,"KeyWords1",true,true,"TestJournal1","TestEditor1",2018,3,12,2);
        //l.modify_book(100);
        //Librarian.get_all_users();
        //l.add_CopiesOfMaterial(110,3);
    }


}
