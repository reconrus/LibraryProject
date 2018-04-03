package main.java.librinno.model;

import static java.time.temporal.ChronoUnit.DAYS;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Test cases for D2.
 */
public class Tests {
    //creation of all needed information for tests
    Database db = new Database();
    Statement stmt = db.con.createStatement();
    Librarian l = new Librarian("1", "1", "1", 1, "1", "1",null);
    Book d1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "MIT Press", "Third edition", 5000, "b1", false, false, 2009, "In library");
    Book d2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm", " Addison-Wesley Professional", "First edition", 1700, "b2", true, false, 2003, "In library");
    //Book book3 = new Book("The Mythical Man-month", "Brooks,Jr., Frederick P", "Addison-Wesley Longman Publishing Co., Inc.", "Second edition", 1, "b3", false, true, 1995, "In library");
    AV d3 = new AV("Null References: The Billion Dollar Mistake", ": Tony Hoare", 700, "av1");
    //AV av2 = new AV("Information Entropy", "Claude Shannon", 1, "av2");
    User p1 = new User("Sergey Afonso", "30001", "Via Margutta, 3", User.professor, "p1", "1@1.r");
    User p2 = new User("Nadia Teixeira", "30002", "Via Sacra, 13", User.professor, "p2", "2@2.r");
    User p3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", User.professor, "p3", "sadasd@gmail.com");
    User s = new User("Andrey Velo", "30004", "Avenida Mazatlan 250", User.student, "s", "sasdasd@mail.com");
    User v = new User("Veronika Rama", "30005", "Stret Atocha, 27", User.vProfessor, "vp", "solovov305@gmail.com");


    public Tests() throws SQLException {
    }

    private void initially() throws SQLException{

        l.addBook(d1.getTitle(), d1.getAuthor(), d1.getPublisher(), d1.getEdition(), d1.getPrice(), d1.getKeyWords(), d1.getBestseller(), d1.getReference(), d1.getYear(), 3);
        l.addBook(d2.getTitle(), d2.getAuthor(), d2.getPublisher(), d2.getEdition(), d2.getPrice(), d2.getKeyWords(), d2.getBestseller(), d2.getReference(), d2.getYear(), 3);
        l.addAV(d3.getTitle(), d3.getAuthor(), d3.getPrice(), d3.getKeyWords(), 2);
        db.userCreation(p1);
        db.userCreation(p2);
        db.userCreation(p3);
        db.userCreation(s);
        db.userCreation(v);
        d1.setId((Integer)db.isBookAlreadyExist(d1).get(1));
        d2.setId((Integer)db.isBookAlreadyExist(d2).get(1));
        d3.setId((Integer)db.isAVAlreadyExist(d3).get(1));
        p1.setCardNumberAsString((Integer)db.isUserAlreadyExist(p1).get(1));
        p2.setCardNumberAsString((Integer)db.isUserAlreadyExist(p2).get(1));
        p3.setCardNumberAsString((Integer)db.isUserAlreadyExist(p3).get(1));
        s.setCardNumberAsString((Integer)db.isUserAlreadyExist(s).get(1));
        v.setCardNumberAsString((Integer)db.isUserAlreadyExist(v).get(1));
    }

    public void tc2() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.parse("2018-03-05");
        LocalDate now = LocalDate.parse("2018-04-02");
        l.checkOutWithData(p1,d1.getId(),date);
        l.checkOutWithData(p1,d2.getId(),date);
        l.checkOutWithData(s,d1.getId(),date);
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d1.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);

        LinkedList<Material> p1doc = l.getAllCopiesTakenByUser(p1.getCard_number());
        LinkedList<Material> sdoc = l.getAllCopiesTakenByUser(s.getCard_number());
        LinkedList<Material> vdoc = l.getAllCopiesTakenByUser(v.getCard_number());

        assert ((p1doc.get(0).getOverdue(p1,now)==0)&&(l.fineWithDate(p1doc.get(0).getId(),now)==0));
        assert ((p1doc.get(1).getOverdue(p1,now)==0)&&(l.fineWithDate(p1doc.get(1).getId(),now)==0));
        assert ((sdoc.get(0).getOverdue(s,now)==7)&&(l.fineWithDate(sdoc.get(0).getId(),now)==700));
        assert ((sdoc.get(1).getOverdue(s,now)==14)&&(l.fineWithDate(sdoc.get(1).getId(),now)==1400));
        assert ((vdoc.get(0).getOverdue(v,now)==21)&&(l.fineWithDate(vdoc.get(0).getId(),now)==2100));
        assert ((vdoc.get(1).getOverdue(v,now)==21)&&(l.fineWithDate(vdoc.get(1).getId(),now)==1700));

    }

    public void tc4() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.parse("2018-05-29");
        LocalDate now = LocalDate.parse("2018-04-02");
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);
        l.outstandingRequest(d2.getId());
        l.renew(p1,d1.getId());
        l.renew(s,d2.getId());
        l.renew(v,d2.getId());
        LinkedList<Material> p1doc = l.getAllCopiesTakenByUser(p1.getCard_number());
        LinkedList<Material> sdoc = l.getAllCopiesTakenByUser(s.getCard_number());
        LinkedList<Material> vdoc = l.getAllCopiesTakenByUser(v.getCard_number());
        assert (p1doc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-30")));
        assert (sdoc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-12")));
        assert (vdoc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-05")));

    }
    /**
     * executing update in all tables
     */
    public void dump(){
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from AV");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE from Copy");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE from Articles");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE from Books");
            pr.executeUpdate();
            pr = db.con.prepareStatement("DELETE FROM Users_of_the_library");
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}