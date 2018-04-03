package main.java.librinno.model;

import static java.time.temporal.ChronoUnit.DAYS;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
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
    public void tc1() throws SQLException {
        dump();
        initially();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy");
        rs.last();
        assert (rs.getRow() == 8);
        rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Card_number NOT IN (31,32)");
        rs.last();
        assert (rs.getRow() == 5);
        LocalDate date = LocalDate.parse("2018-03-05");
        l.checkOutWithData(p1, d1.getId(), date);
        l.checkOutWithData(p1, d2.getId(), date);
        l.returnBook(d2.getId());
        assert (l.fineWithDate(l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId(), date)) == 0;
        assert (l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getOverdue(p1, date)) == 0;
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
    public void tc3() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.of(2018,3,29);
        l.checkOutWithData(p1,d1.getId(),date);
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);

        l.renew(p1,l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId());
        l.renew(s,l.getAllCopiesTakenByUser(s.getCard_number()).get(0).getId());
        l.renew(v,l.getAllCopiesTakenByUser(v.getCard_number()).get(0).getId());

        assert (l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,30)));
        assert (l.getAllCopiesTakenByUser(s.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,16)));
        assert (l.getAllCopiesTakenByUser(v.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,9)));
    }

    public void tc4() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.parse("2018-05-29");
        LocalDate now = LocalDate.parse("2018-04-02");
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);
        ArrayList<ArrayList<String>> emails=l.outstandingRequestWithDate(d2.getId(),date);
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
    public void tc5() throws SQLException{
        dump();
        initially();
        l.checkOut(p1,d3.getId());
        l.checkOut(s,d3.getId());
        l.checkOut(v,d3.getId());
        assert (l.getQueue(d3.getId()).size() == 1 && l.getQueue(d3.getId()).get(0).getName().equals("Veronika Rama"));
    }

    public void tc6() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.parse("2018-03-29");
        LocalDate now = LocalDate.parse("2018-04-02");
        l.checkOutWithData(p1,d3.getId(),date);
        l.checkOutWithData(p2,d3.getId(),date);
        l.checkOutWithData(s,d3.getId(),date);
        l.checkOutWithData(v,d3.getId(),date);
        l.checkOutWithData(p3,d3.getId(),date);
        ArrayList<User> usQueue= l.getQueue(d3.getId());
        assert (usQueue.get(0).getCard_number()==s.getCard_number());
        assert (usQueue.get(1).getCard_number()==v.getCard_number());
        assert (usQueue.get(2).getCard_number()==p3.getCard_number());
    }
    public void tc7() throws SQLException{
        tc6();
        LocalDate now = LocalDate.parse("2018-04-02");
        ArrayList<String> with_already_taken_book=l.outstandingRequestWithDate(d3.getId(),now).get(0);
        assert (l.getQueue(d3.getId()).size() == 0);
        assert (with_already_taken_book.get(0).equals(p1.getEmail()));
        assert (with_already_taken_book.get(1).equals(p2.getEmail()));
        ArrayList<String> waiting_for_book=l.outstandingRequestWithDate(d3.getId(),now).get(1);
        assert (waiting_for_book.get(0).equals(s.getEmail()));
        assert (waiting_for_book.get(1).equals(v.getEmail()));
        assert (waiting_for_book.get(2).equals(p3.getEmail()));
    }
    public void tc8() throws SQLException, InterruptedException {
        tc6();
        LinkedList<Material> p2doc = l.getAllCopiesTakenByUser(p2.getCard_number());
        l.returnBook(p2doc.get(0).getId());
        LinkedList<Material> p2docAfter = l.getAllCopiesTakenByUser(p2.getCard_number());
        ResultSet rs= stmt.executeQuery("SELECT * FROM queue_on_"+ d3.getId());
        ArrayList temp= new ArrayList();
        while (rs.next()){
            temp.add(rs.getString("is_sended"));
        }
        ArrayList<User> usQueue= l.getQueue(d3.getId());
        assert (usQueue.get(0).getCard_number()==s.getCard_number());
        assert (usQueue.get(1).getCard_number()==v.getCard_number());
        assert (usQueue.get(2).getCard_number()==p3.getCard_number());
        Thread.sleep(10000);
        assert(temp.get(0).equals("1"));
        assert(p2docAfter.size()==0);
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
            DatabaseMetaData md = db.con.getMetaData();
            ResultSet rs = md.getTables(null, null, "queue%", null);
            while (rs.next()) {
                String table_name = rs.getString(3);
                pr.executeUpdate("DROP TABLE IF EXISTS "+table_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}