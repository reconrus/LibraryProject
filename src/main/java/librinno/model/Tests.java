package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kor19 on 06.03.2018.
 */
public class Tests {
    Database db = new Database();
    Librarian l = new Librarian("1", "1", "1", 1, "1", "1");
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
        Statement stmt = db.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library");
        while (rs.next()) {
            assert (rs.getString("Name").equals(user.getName()) &&
                    rs.getString("Address").equals(user.getAdress()) &&
                    rs.getString("Phone_number").equals(user.getPhoneNumber()) &&
                    rs.getString("Type").equals(user.getType()) &&
                    rs.getString("Password").equals(user.getPassword()));
            assert (rs.getString("Name").equals(user2.getName()) &&
                    rs.getString("Address").equals(user2.getAdress()) &&
                    rs.getString("Phone_number").equals(user2.getPhoneNumber()) &&
                    rs.getString("Type").equals(user2.getType()) &&
                    rs.getString("Password").equals(user2.getPassword()));
            assert (rs.getString("Name").equals(user3.getName()) &&
                    rs.getString("Address").equals(user3.getAdress()) &&
                    rs.getString("Phone_number").equals(user3.getPhoneNumber()) &&
                    rs.getString("Type").equals(user3.getType()) &&
                    rs.getString("Password").equals(user3.getPassword()));
        }
        ResultSet rs_book = stmt.executeQuery("SELECT * FROM Books");
        while (rs_book.next()) {
            assert (rs_book.getString("Name").equals(book1.getTitle()) &&
                    rs_book.getString("Author").equals(book1.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book1.getPublisher()) &&
                    rs_book.getString("Edition").equals(book1.getEdition()) &&
                    rs_book.getInt("Price") == book1.getPrice() &&
                    rs_book.getString("Keywords").equals(book1.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book1.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book1.getReference() &&
                    rs_book.getInt("Year") == book1.getYear());
            assert (rs_book.getString("Name").equals(book2.getTitle()) &&
                    rs_book.getString("Author").equals(book2.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book2.getPublisher()) &&
                    rs_book.getString("Edition").equals(book2.getEdition()) &&
                    rs_book.getInt("Price") == book2.getPrice() &&
                    rs_book.getString("Keywords").equals(book2.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book2.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book2.getReference() &&
                    rs_book.getInt("Year") == book2.getYear());
            assert (rs_book.getString("Name").equals(book3.getTitle()) &&
                    rs_book.getString("Author").equals(book3.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book3.getPublisher()) &&
                    rs_book.getString("Edition").equals(book3.getEdition()) &&
                    rs_book.getInt("Price") == book3.getPrice() &&
                    rs_book.getString("Keywords").equals(book3.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book3.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book3.getReference() &&
                    rs_book.getInt("Year") == book3.getYear());
        }
        ResultSet rs_av = stmt.executeQuery("SELECT * FROM AV");
        while (rs_av.next()) {
            assert (rs_av.getString("Name").equals(av1.getTitle()) &&
                    rs_av.getString("Author").equals(av1.getAuthor()) &&
                    rs_av.getInt("Price") == av1.getPrice() &&
                    rs_av.getString("Keywords").equals(av1.getKeyWords()));
            assert (rs_av.getString("Name").equals(av2.getTitle()) &&
                    rs_av.getString("Author").equals(av2.getAuthor()) &&
                    rs_av.getInt("Price") == av2.getPrice() &&
                    rs_av.getString("Keywords").equals(av2.getKeyWords()));
        }
        System.out.println("tc1 success");
    }


    public void tc2() throws SQLException {
        ArrayList arrayList = db.isBookAlreadyExist(book1);
        ArrayList arrayList3 = db.isBookAlreadyExist(book3);

        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList.get(1) + " LIMIT 2");
            pr.executeUpdate();

            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Id_of_original FROM Copy");
            int i = 0;
            while (rs.next())
                if (rs.getInt(1) == (Integer) arrayList.get(1))
                    i++;
            assert (i == 1);

            pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList3.get(1) + " LIMIT 1");
            pr.executeUpdate();

            stmt = db.con.createStatement();
            rs = stmt.executeQuery("SELECT Id_of_original FROM Copy");
            i = 0;
            while (rs.next())
                if (rs.getInt(1) == (Integer) arrayList3.get(1))
                    i++;
            assert (i == 0);

            pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + db.isUserAlreadyExist(user2).get(1));
            pr.executeUpdate();

            stmt = db.con.createStatement();
            rs = stmt.executeQuery("SELECT Card_number FROM Users_of_the_library");
            i = 0;
            while (rs.next())
                if (rs.getInt(1) == (Integer) db.isUserAlreadyExist(user2).get(1))
                    i++;
            assert (i == 0);
            System.out.println("tc2 success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tc3() throws SQLException {
        Statement stmt = db.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library");
        while (rs.next()) {
            assert (rs.getString("Name").equals(user.getName()) &&
                    rs.getString("Address").equals(user.getAdress()) &&
                    rs.getString("Phone_number").equals(user.getPhoneNumber()) &&
                    rs.getString("Type").equals(user.getType()) &&
                    rs.getString("Password").equals(user.getPassword()));
            assert (rs.getString("Name").equals(user3.getName()) &&
                    rs.getString("Address").equals(user3.getAdress()) &&
                    rs.getString("Phone_number").equals(user3.getPhoneNumber()) &&
                    rs.getString("Type").equals(user3.getType()) &&
                    rs.getString("Password").equals(user3.getPassword()));
        }
        System.out.println("tc3 success");
    }

    public void tc4() throws SQLException {
        assert (db.isUserAlreadyExist(user2).size() == 1);
        assert (user3.getName().equals(db.getInformationAboutTheUser((Integer) db.isUserAlreadyExist(user3).get(1)).getName()));
        assert (user3.getAdress().equals(db.getInformationAboutTheUser((Integer) db.isUserAlreadyExist(user3).get(1)).getAdress()));
        assert (user3.getPhoneNumber().equals(db.getInformationAboutTheUser((Integer) db.isUserAlreadyExist(user3).get(1)).getPhoneNumber()));
        assert (user3.getCard_number() == db.getInformationAboutTheUser((Integer) db.isUserAlreadyExist(user3).get(1)).getCard_number());
        assert (user3.getType().equals(db.getInformationAboutTheUser((Integer) db.isUserAlreadyExist(user3).get(1)).getType()));
        System.out.println("tc4 assert success");
    }

    public void tc5() throws SQLException {
        assert (db.isUserAlreadyExist(user2).size() == 1);
        System.out.println("tc5 success");
    }

    public void tc6() throws SQLException {
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user3, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book2).get(1));
        System.out.println(user.getName());
        System.out.println(user.getAdress());
        System.out.println(user.getPhoneNumber());
        System.out.println(db.isUserAlreadyExist(user).get(1));
        System.out.println(user.getType());
        LinkedList<Material> linkedList = l.getAllCopiesTakenByUser((Integer) db.isUserAlreadyExist(user).get(1));
        for (int i = 0; i < linkedList.size(); i++)
            System.out.println(linkedList.get(i).getTitle() + ", " + linkedList.get(i).getReturnDate());

        System.out.println(user3.getName());
        System.out.println(user3.getAdress());
        System.out.println(user3.getPhoneNumber());
        System.out.println(db.isUserAlreadyExist(user3).get(1));
        System.out.println(user3.getType());
        LinkedList<Material> linkedList2 = l.getAllCopiesTakenByUser((Integer) db.isUserAlreadyExist(user3).get(1) + 1);
        for (int i = 0; i < linkedList2.size(); i++)
            System.out.println(linkedList2.get(i).getTitle() + ", " + linkedList2.get(i).getReturnDate());
    }

    public void tc7() throws SQLException {
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book2).get(1));
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book3).get(1));
        l.checkOutAV(user, (Integer) db.isAVAlreadyExist(av1).get(1));

        l.checkOutBook(user2, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user2, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutAV(user2, (Integer) db.isAVAlreadyExist(av2).get(1));
        Statement stmt = db.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Users_of_the_library");
        while (rs.next()) {
            assert (rs.getString("Name").equals(user.getName()) &&
                    rs.getString("Address").equals(user.getAdress()) &&
                    rs.getString("Phone_number").equals(user.getPhoneNumber()) &&
                    rs.getString("Type").equals(user.getType()) &&
                    rs.getString("Password").equals(user.getPassword()));
            assert (rs.getString("Name").equals(user2.getName()) &&
                    rs.getString("Address").equals(user2.getAdress()) &&
                    rs.getString("Phone_number").equals(user2.getPhoneNumber()) &&
                    rs.getString("Type").equals(user2.getType()) &&
                    rs.getString("Password").equals(user2.getPassword()));
        }
        ResultSet rs_book = stmt.executeQuery("SELECT * FROM Books");
        while (rs_book.next()) {
            assert (rs_book.getString("Name").equals(book1.getTitle()) &&
                    rs_book.getString("Author").equals(book1.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book1.getPublisher()) &&
                    rs_book.getString("Edition").equals(book1.getEdition()) &&
                    rs_book.getInt("Price") == book1.getPrice() &&
                    rs_book.getString("Keywords").equals(book1.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book1.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book1.getReference() &&
                    rs_book.getInt("Year") == book1.getYear());
            assert (rs_book.getString("Name").equals(book2.getTitle()) &&
                    rs_book.getString("Author").equals(book2.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book2.getPublisher()) &&
                    rs_book.getString("Edition").equals(book2.getEdition()) &&
                    rs_book.getInt("Price") == book2.getPrice() &&
                    rs_book.getString("Keywords").equals(book2.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book2.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book2.getReference() &&
                    rs_book.getInt("Year") == book2.getYear());
            assert (rs_book.getString("Name").equals(book3.getTitle()) &&
                    rs_book.getString("Author").equals(book3.getAuthor()) &&
                    rs_book.getString("Publisher").equals(book3.getPublisher()) &&
                    rs_book.getString("Edition").equals(book3.getEdition()) &&
                    rs_book.getInt("Price") == book3.getPrice() &&
                    rs_book.getString("Keywords").equals(book3.getKeyWords()) &&
                    rs_book.getBoolean("is_bestseller") == book3.getBestseller() &&
                    rs_book.getBoolean("is_reference") == book3.getReference() &&
                    rs_book.getInt("Year") == book3.getYear());
        }
        ResultSet rs_av = stmt.executeQuery("SELECT * FROM Books");
        while (rs_av.next()) {
            assert (rs_av.getString("Name").equals(av1.getTitle()) &&
                    rs_av.getString("Author").equals(av1.getAuthor()) &&
                    rs_av.getInt("Price") == av1.getPrice() &&
                    rs_av.getString("Keywords").equals(av1.getKeyWords()));
            assert (rs_av.getString("Name").equals(av2.getTitle()) &&
                    rs_av.getString("Author").equals(av2.getAuthor()) &&
                    rs_av.getInt("Price") == av2.getPrice() &&
                    rs_av.getString("Keywords").equals(av2.getKeyWords()));
        }
        assert (l.getAllCopiesTakenByUser(user.getCard_number()).size() >0);
        assert (l.getAllCopiesTakenByUser(user.getCard_number()).size() >0);
        System.out.println("tc7 success");
    }
}