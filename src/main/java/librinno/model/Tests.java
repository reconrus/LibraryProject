package main.java.librinno.model;

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
    User p3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", User.professor, "p3", "3@3.r");
    User s = new User("Andrey Velo", "30004", "Avenida Mazatlan 250", User.student, "s", "s@s.r");
    User v = new User("Veronika Rama", "30005", "Stret Atocha, 27", User.vProfessor, "vp", "vp@vp.r");


    public Tests() throws SQLException {
    }

    private void initially() throws SQLException{

        l.addBook(d1.getTitle(), d1.getAuthor(), d1.getPublisher(), d1.getEdition(), d1.getPrice(), d1.getKeyWords(), d1.getBestseller(), d1.getReference(), d1.getYear(), 3);
        l.addBook(d2.getTitle(), d2.getAuthor(), d2.getPublisher(), d2.getEdition(), d2.getPrice(), d2.getKeyWords(), d2.getBestseller(), d2.getReference(), d2.getYear(), 3);
        l.addAV(d3.getTitle(), d3.getAuthor(), d3.getPrice(), d3.getKeyWords(), 2);
    }

    /**
     * first test
     * @throws SQLException
     */
    public void tc1() throws SQLException {


        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy");
        rs.last();
        assert(rs.getRow() == 8);

        db.userCreation(p1);
        db.userCreation(p2);
        db.userCreation(p3);

        rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Card_number NOT IN (31,32)");
        rs.last();
        assert(rs.getRow()==3);
    }
    /**
     * second test
     * @throws SQLException
     */
    public void tc2() throws SQLException {
        tc1();
        //IsBookAlreadyExist outputs ArrayList of size 2 if the user is already in the system and list[1] == id of the user, of size 1 otherwise
        ArrayList arrayList = db.isBookAlreadyExist(d1);
        ArrayList arrayList3 = db.isBookAlreadyExist(book3);

        PreparedStatement pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList.get(1) + " LIMIT 2");
        pr.executeUpdate();

        pr = db.con.prepareStatement("DELETE from Copy WHERE Id_of_original=" + arrayList3.get(1) + " LIMIT 1");
        pr.executeUpdate();

        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy");
        rs.last();
        assert(rs.getRow() == 5);

        pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + db.isUserAlreadyExist(user2).get(1));
        pr.executeUpdate();

        rs = stmt.executeQuery("SELECT * FROM Users_of_the_library WHERE Card_number NOT IN (31,32)");
        rs.last();
        assert(rs.getRow() == 2);
    }
    /**
     * third test
     * @throws SQLException
     */
    public void tc3() throws SQLException {
        tc1();
        assert((Database.isUserAlreadyExist(user).size()+ Database.isUserAlreadyExist(user3).size())==4);
    }
    /**
     * fourth test
     * @throws SQLException
     */
    public void tc4() throws SQLException {
        tc2();
        assert(Database.isUserAlreadyExist(user2).size()==1);
        assert(Database.isUserAlreadyExist(user3).size()==2);
    }

    /**
     * fifth test
     * @throws SQLException
     */
    public void tc5() throws SQLException {
        tc2();
        assert(db.isUserAlreadyExist(user2).size() == 1);
    }


    /*
        We think that expected results of test case 6 is not appropriate.
        P3 checks out only b1, however, expected results says that he has b2 and P1 tries to check out P1 tries to check out
        b2 and he is able to do that (there are sufficient numbers of copies), however expected results says that he hasn't this book.

        SO. We have corrected actions and now it fits the expected results:
        p1 checks out b1
        p3 checks out b1
        p3 checks out b2
     */

    /**
     * sixth test
     * @throws SQLException
     */
    public void tc6() throws SQLException {
        tc2();
        LocalDate march5 = LocalDate.of(2018, 03, 05);

        int idBook1 = (Integer)db.isBookAlreadyExist(d1).get(1);
        int idBook2 = (Integer)db.isBookAlreadyExist(d2).get(1);
        int idUser = (Integer)db.isUserAlreadyExist(user).get(1);
        int idUser3 = (Integer)db.isUserAlreadyExist(user3).get(1);

        user.setCardNumberAsString(idUser);
        user3.setCardNumberAsString(idUser3);

        checkOutBook(user, idBook1, march5);
        checkOutBook(user3, idBook1, march5);
        checkOutBook(user3, idBook2, march5);

        Statement stmt = db.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser);

        boolean b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(28)) );
                b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser3);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser3)
                b=true;
        assert(!b);//cause we have only one copy of b1, and this copy not in library


        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook2 +" AND Owner="+idUser3);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook2 && rs.getInt("Owner")==idUser3 &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(14))) //b2 is a bestseller
                b=true;
        assert(b);
    }


    /**
     * seventh test
     * @throws SQLException
     */
    public void tc7() throws SQLException {
        tc1();

        LocalDate march5 = LocalDate.of(2018, 03, 05);

        int idBook1 = (Integer)db.isBookAlreadyExist(d1).get(1);
        int idBook2 = (Integer)db.isBookAlreadyExist(d2).get(1);
        int idBook3 = (Integer)db.isBookAlreadyExist(book3).get(1);

        int idAV1 = (Integer)db.isAVAlreadyExist(d3).get(1);
        int idAV2 = (Integer)db.isAVAlreadyExist(av2).get(1);

        int idUser = (Integer)db.isUserAlreadyExist(user).get(1);
        int idUser2 = (Integer)db.isUserAlreadyExist(user2).get(1);

        user.setCardNumberAsString(idUser);
        user2.setCardNumberAsString(idUser2);

        checkOutBook(user, idBook1, march5);
        checkOutBook(user, idBook2, march5);
        checkOutBook(user, idBook3, march5);
        checkOutAV(user, idAV1, march5);

        checkOutBook(user2, idBook1, march5);
        checkOutBook(user2, idBook2, march5);
        checkOutAV(user2, idAV2, march5);


        Statement stmt = db.con.createStatement();

        // P1

        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser);

        boolean b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(28)))
                        b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook2 +" AND Owner="+idUser);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook2 && rs.getInt("Owner")==idUser &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(28)))
                b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook3 +" AND Owner="+idUser);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook2 && rs.getInt("Owner")==idUser)
                b=true;
        assert(!b); //b3 is a reference

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idAV1 +" AND Owner="+idUser);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idAV1 && rs.getInt("Owner")==idUser &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(14)))
                b=true;
        assert(b);


        // P2

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser2);

        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser2 &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(21)))
                        b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook2 +" AND Owner="+idUser2);

        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook2 && rs.getInt("Owner")==idUser2 &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(14))) // b2 is a bestseller
                        b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idAV2 +" AND Owner="+idUser2);
        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idAV2 && rs.getInt("Owner")==idUser2 &&
                    rs.getDate("Return_date").toLocalDate().equals(march5.plusDays(14)))
                b=true;
        assert(b);
    }

    /**
     * eights test
     * @throws SQLException
     */
    public void tc8() throws SQLException{
        tc1();

        LocalDate february2 = LocalDate.of(2018, 02, 02);
        LocalDate february5 = LocalDate.of(2018, 02, 05);
        LocalDate february9 = LocalDate.of(2018, 02, 9);
        LocalDate february17 = LocalDate.of(2018, 02, 17);
        LocalDate march5 = LocalDate.of(2018, 03, 05);

        int idBook1 = (Integer)db.isBookAlreadyExist(d1).get(1);
        int idBook2 = (Integer)db.isBookAlreadyExist(d2).get(1);
        int idAV1 = (Integer)db.isAVAlreadyExist(d3).get(1);

        int idUser = (Integer)db.isUserAlreadyExist(user).get(1);
        int idUser2 = (Integer)db.isUserAlreadyExist(user2).get(1);

        user.setCardNumberAsString(idUser);
        user2.setCardNumberAsString(idUser2);

        checkOutBook(user, idBook2, february2);
        checkOutBook(user2, idBook1, february5);
        checkOutBook(user, idBook1, february9);
        checkOutAV(user2, idAV1, february17);

        Period m5f2 = Period.between(february2.plusDays(28), march5);
        Period m5f5 = Period.between(february5.plusDays(21), march5);
        Period m5f9 = Period.between(february9.plusDays(28), march5);
        Period m5f17 = Period.between(february17.plusDays(14), march5);

        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook2 +" AND Owner="+idUser);

        boolean b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook2 && rs.getInt("Owner")==idUser &&
                    Period.between(rs.getDate("Return_date").toLocalDate(), march5).equals(m5f2))
                        b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser2);

        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser2 &&
                    Period.between(rs.getDate("Return_date").toLocalDate(), march5).equals(m5f5))
                         b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idBook1 +" AND Owner="+idUser);

        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idBook1 && rs.getInt("Owner")==idUser &&
                    Period.between(rs.getDate("Return_date").toLocalDate(), march5).equals(m5f9))
                         b=true;
        assert(b);

        rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original =" + idAV1 +" AND Owner="+idUser2);

        b=false;
        while (rs.next())
            if (rs.getInt("Id_of_original")==idAV1 && rs.getInt("Owner")==idUser2 &&
                    Period.between(rs.getDate("Return_date").toLocalDate(), march5).equals(m5f17))
                        b=true;
        assert(b);
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

    /**
     * special for tests because now time is taken from local PC
     * @param user which user takes book
     * @param idOfBook books id
     * @param date time
     * @return boolean value - success or not success
     */
    public static boolean checkOutBook(User user, int idOfBook, LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfBook + " AND Status= 'In library' LIMIT 1 ");
            Book book = Librarian.bookByID(idOfBook);
            if (!book.getReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.getType().equals("Student") && book.getBestseller()) {
                    pr.setInt(2, 14);//если студент и книга бестселлер, то ставим 14 дней
                    LocalDate returnDate = date.plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.getType().equals("Student") && !book.getBestseller()) {
                    pr.setInt(2, 21);//если студент и книга бестселлер, то ставим 21 дней
                    LocalDate returnDate = date.plusDays(21);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else {
                    pr.setInt(2, 28);//это если факулти
                    LocalDate returnDate = date.plusDays(28);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                }
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else
                return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * the same method for AV
     * @param user which user takes AV
     * @param idOfAV what AV to take
     * @param date time
     * @return boolean value  - success or not
     */
    public static boolean checkOutAV(User user, int idOfAV, LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfAV + " AND Status= 'In library' LIMIT 1 ");
            AV av = Librarian.avById(idOfAV);
            pr.setInt(1, user.getCard_number());
            pr.setInt(2, 14);
            LocalDate returnDate = date.plusDays(14);
            pr.setDate(4, java.sql.Date.valueOf(returnDate));
            pr.setString(3, "Issued");
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}