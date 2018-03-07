package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kor19 on 06.03.2018.
 */
public class Tests {
    Database db = new Database();
    Librarian l = new Librarian("1","1","1",1,"1","1");
    Book book1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "MIT Press", "Third edition", 1, "b1", false, false, 2009, "In library");
    Book book2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm", " Addison-Wesley Professional", "First edition", 1, "b2", true, false, 2003, "In library");
    Book book3 = new Book("The Mythical Man-month", "Brooks,Jr., Frederick P", "Addison-Wesley Longman Publishing Co., Inc.", "Second edition", 1, "b3", false, true, 1995, "In library");
    AV av1 = new AV("Null References: The Billion Dollar Mistake", ": Tony Hoare", 1, "av1");
    AV av2 = new AV("Information Entropy", "Claude Shannon", 1, "av2");
    User user = new User("Sergey Afonso", "30001", "Via Margutta, 3", "Faculty", "p1");
    User user2 = new User("p2", "p2", "p2", "p2", "p2");
    User user3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", "Student", "p3");

    public void tc1() throws SQLException {
        l.addBook(book1.getTitle(), book1.getAuthor(), book1.getPublisher(), book1.getEdition(), book1.getPrice(), book1.getKeyWords(), book1.getBestseller(), book1.getReference(), book1.getYear(), 3);
        l.addBook(book2.getTitle(), book2.getAuthor(), book2.getPublisher(), book2.getEdition(), book2.getPrice(), book2.getKeyWords(), book2.getBestseller(), book2.getReference(), book2.getYear(), 2);
        l.addBook(book3.getTitle(), book3.getAuthor(), book3.getPublisher(), book3.getEdition(), book3.getPrice(), book3.getKeyWords(), book3.getBestseller(), book3.getReference(), book3.getYear(), 1);

        l.addAV(av1.getTitle(), av1.getAuthor(), av1.getPrice(), av1.getKeyWords(), 1);
        l.addAV(av2.getTitle(), av2.getAuthor(), av2.getPrice(), av2.getKeyWords(), 1);


        db.userCreation(user);
        db.userCreation(user2);
        db.userCreation(user3);
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
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getName());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getAdress());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getPhoneNumber());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getCard_Number());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getType());
        System.out.println("document checked-out,due date: " + l.getAllCopiesTakenByUser(user.getCard_number()));

        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getName());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getAdress());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getPhoneNumber());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getCard_Number());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getType());
        System.out.println("document checked-out,due date: " + l.getAllCopiesTakenByUser(user.getCard_number()));
    }

    public void tc4() throws SQLException {
        if (db.isUserAlreadyExist(user2).size() == 1) {
            System.out.println("Patron doesn't exist");
        } else {
            System.out.println(db.getInformationAboutTheUser(user2.getCard_number()).getName());
            System.out.println(db.getInformationAboutTheUser(user2.getCard_number()).getAdress());
            System.out.println(db.getInformationAboutTheUser(user2.getCard_number()).getPhoneNumber());
            System.out.println(db.getInformationAboutTheUser(user2.getCard_number()).getCard_Number());
            System.out.println(db.getInformationAboutTheUser(user2.getCard_number()).getType());
        }
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getName());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getAdress());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getPhoneNumber());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getCard_Number());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 2).getType());
        System.out.println("document checked-out,due date: " + l.getAllCopiesTakenByUser(user.getCard_number() + 2));

    }

    public void tc5() throws SQLException {
        boolean correct = l.checkOutBook(user2, (Integer) db.isBookAlreadyExist(book2).get(1));
    }

    public void tc7() throws SQLException {
        boolean p1_correct1 = l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1));
        boolean p1_correct2 = l.checkOutBook(user3, (Integer) db.isBookAlreadyExist(book2).get(1));
        boolean p1_correct3 = l.checkOutBook(user3, (Integer) db.isBookAlreadyExist(book3).get(1));
        boolean p1_correct_av = l.checkOutBook(user3, (Integer) db.isAVAlreadyExist(av1).get(1));

        boolean p2_correct1 = l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1));
        boolean p2_correct2 = l.checkOutBook(user3, (Integer) db.isBookAlreadyExist(book2).get(1));
        boolean p2_correct_av = l.checkOutBook(user3, (Integer) db.isAVAlreadyExist(av2).get(1));
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getName());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getAdress());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getPhoneNumber());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getCard_Number());
        System.out.println(db.getInformationAboutTheUser(user.getCard_number()).getType());
        System.out.println("document checked-out,due date: " + l.getAllCopiesTakenByUser(user.getCard_number()));

        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 1).getName());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 1).getAdress());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 1).getPhoneNumber());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 1).getCard_Number());
        System.out.println(db.getInformationAboutTheUser(user3.getCard_number() + 1).getType());
        System.out.println("document checked-out,due date: " + l.getAllCopiesTakenByUser(user2.getCard_number() + 1));

    }
}