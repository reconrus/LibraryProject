package main.java.librinno.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class Tests {
    Database db = new Database();
    Librarian l = new Librarian();
    Book book1=new Book("Introduction to Algorithms","Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein","MIT Press","Third edition",1,"b1",false,false,2009,"In library");
    Book book2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software","Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm"," Addison-Wesley Professional","First edition",1,"b2",true,false,2003,"In library");
    Book book3 = new Book("The Mythical Man-month","Brooks,Jr., Frederick P","Addison-Wesley Longman Publishing Co., Inc.","Second edition",1,"b3",false,true,1995,"In library");
    AV av1 = new AV("Null References: The Billion Dollar Mistake",": Tony Hoare",1,"av1");
    AV av2 = new AV("Information Entropy","Claude Shannon",1,"av2");
    User user = new User("Sergey Afonso","Via Margutta, 3","30001",1010,"Faculty","p1");
    User user2 = new User("p2","p2","p2",1010,"p2","p2");
    User user3 = new User("Elvira Espindola","Via del Corso, 22","30003",1100,"Student","p3");
    public void tc1() throws SQLException {
        l.add_book(book1.getTitle(),book1.getAuthor(),book1.getPublisher(),book1.getEdition(),book1.getPrice(),book1.getKeyWords(),book1.isIs_bestseller(),book1.isReference(),book1.getYear(),3);
        l.add_book(book2.getTitle(),book2.getAuthor(),book2.getPublisher(),book2.getEdition(),book2.getPrice(),book2.getKeyWords(),book2.isIs_bestseller(),book2.isReference(),book2.getYear(),2);
        l.add_book(book3.getTitle(),book3.getAuthor(),book3.getPublisher(),book3.getEdition(),book3.getPrice(),book3.getKeyWords(),book3.isIs_bestseller(),book3.isReference(),book3.getYear(),1);

        l.add_AV(av1.getTitle(),av1.getAuthor(),av1.getPrice(),av1.getKeyWords(),false,false,1);
        l.add_AV(av2.getTitle(),av2.getAuthor(),av2.getPrice(),av2.getKeyWords(),false,false,1);

        db.user_creation(user);
        db.user_creation(user2);
        db.user_creation(user3);
    }
    public void tc2()throws SQLException {


    }
    public void tc3() throws SQLException{
        System.out.println(db.get_information_about_the_user(user.get_card_number())
                .get_name());
        System.out.println(db.get_information_about_the_user(user.get_card_number()).get_adress());
        System.out.println(db.get_information_about_the_user(user.get_card_number()).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user.get_card_number()).get_card_number());
        System.out.println(db.get_information_about_the_user(user.get_card_number()).get_type());

        System.out.println(db.get_information_about_the_user(user3.get_card_number()).get_name());
        System.out.println(db.get_information_about_the_user(user3.get_card_number()).get_adress());
        System.out.println(db.get_information_about_the_user(user3.get_card_number()).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user3.get_card_number()).get_card_number());
        System.out.println(db.get_information_about_the_user(user3.get_card_number()).get_type());
    }
    public void tc4() throws SQLException{

    }
}
