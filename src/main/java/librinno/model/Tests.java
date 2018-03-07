package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kor19 on 06.03.2018.
 */
public class Tests {
    Database db = new Database();
    Librarian l = new Librarian();
    Book book1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "MIT Press", "Third edition", 1, "b1", false, false, 2009, "In library");
    Book book2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm", " Addison-Wesley Professional", "First edition", 1, "b2", true, false, 2003, "In library");
    Book book3 = new Book("The Mythical Man-month", "Brooks,Jr., Frederick P", "Addison-Wesley Longman Publishing Co., Inc.", "Second edition", 1, "b3", false, true, 1995, "In library");
    AV av1 = new AV("Null References: The Billion Dollar Mistake", ": Tony Hoare", 1, "av1");
    AV av2 = new AV("Information Entropy", "Claude Shannon", 1, "av2");
    User user = new User("Sergey Afonso", "30001", "Via Margutta, 3", "Faculty", "p1");
    User user2 = new User("p2", "p2", "p2", "p2", "p2");
    User user3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", "Student", "p3");

    public void tc1() throws SQLException {
        l.add_book(book1.getTitle(), book1.getAuthor(), book1.getPublisher(), book1.getEdition(), book1.getPrice(), book1.getKeyWords(), book1.isIs_bestseller(), book1.isReference(), book1.getYear(), 3);
        l.add_book(book2.getTitle(), book2.getAuthor(), book2.getPublisher(), book2.getEdition(), book2.getPrice(), book2.getKeyWords(), book2.isIs_bestseller(), book2.isReference(), book2.getYear(), 2);
        l.add_book(book3.getTitle(), book3.getAuthor(), book3.getPublisher(), book3.getEdition(), book3.getPrice(), book3.getKeyWords(), book3.isIs_bestseller(), book3.isReference(), book3.getYear(), 1);

        l.add_AV(av1.getTitle(),av1.getAuthor(),av1.getPrice(),av1.getKeyWords(),1);
        l.add_AV(av2.getTitle(),av2.getAuthor(),av2.getPrice(),av2.getKeyWords(),1);


        db.user_creation(user);
        db.user_creation(user2);
        db.user_creation(user3);
    }

    public void tc2() throws SQLException {
        ArrayList arrayList = db.isBookAlreadyExist(book1);
        ArrayList arrayList3 = db.isBookAlreadyExist(book3);

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList.get(1) + " LIMIT 2");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList3.get(1) + " LIMIT 1");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + db.isUserAlreadyExist(user2).get(1));
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tc3() throws SQLException {
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_name());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_adress());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_card_number());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_type());
        System.out.println("document checked-out,due date: "+l.get_all_copies_taken_by_user(user.getCard_number()));

        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_name());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_adress());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_card_number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_type());
        System.out.println("document checked-out,due date: "+l.get_all_copies_taken_by_user(user.getCard_number()));
    }

    public void tc4() throws SQLException {
        if (db.isUserAlreadyExist(user2).size() == 1) {
            System.out.println("Patron doesn't exist");
        } else {
            System.out.println(db.get_information_about_the_user(user2.getCard_number()).get_name());
            System.out.println(db.get_information_about_the_user(user2.getCard_number()).get_adress());
            System.out.println(db.get_information_about_the_user(user2.getCard_number()).getPhone_Number());
            System.out.println(db.get_information_about_the_user(user2.getCard_number()).get_card_number());
            System.out.println(db.get_information_about_the_user(user2.getCard_number()).get_type());
        }
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_name());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_adress());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_card_number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 2).get_type());
        System.out.println("document checked-out,due date: "+l.get_all_copies_taken_by_user(user.getCard_number()+2));

    }

    public void tc5() throws SQLException {
        boolean correct = l.checkOutBook(user2, (Integer) db.isBookAlreadyExist(book2).get(1));
    }
    public void tc7()throws SQLException{
        boolean p1_correct1=l.checkOutBook(user,(Integer) db.isBookAlreadyExist(book1).get(1));
        boolean p1_correct2=l.checkOutBook(user3,(Integer) db.isBookAlreadyExist(book2).get(1));
        boolean p1_correct3=l.checkOutBook(user3,(Integer) db.isBookAlreadyExist(book3).get(1));
        boolean p1_correct_av=l.checkOutBook(user3,(Integer) db.isAVAlreadyExist(av1).get(1));

        boolean p2_correct1=l.checkOutBook(user,(Integer) db.isBookAlreadyExist(book1).get(1));
        boolean p2_correct2=l.checkOutBook(user3,(Integer) db.isBookAlreadyExist(book2).get(1));
        boolean p2_correct_av=l.checkOutBook(user3,(Integer) db.isAVAlreadyExist(av2).get(1));
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_name());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_adress());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_card_number());
        System.out.println(db.get_information_about_the_user(user.getCard_number()).get_type());
        System.out.println("document checked-out,due date: "+l.get_all_copies_taken_by_user(user.getCard_number()));

        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 1).get_name());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 1).get_adress());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 1).getPhone_Number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 1).get_card_number());
        System.out.println(db.get_information_about_the_user(user3.getCard_number() + 1).get_type());
        System.out.println("document checked-out,due date: "+l.get_all_copies_taken_by_user(user2.getCard_number()+1));

    }
}
