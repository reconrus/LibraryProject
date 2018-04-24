package main.java.librinno.model;

import java.sql.*;
import java.util.ArrayList;

/**
 * Test cases for D3.
 */
public class Tests {
    //creation of all needed information for tests
    Database db = new Database();
    Statement stmt = db.con.createStatement();
    //    Librarian l = new Librarian("1", "1", "1", 1, "1", "1",null);
    Book d1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", "MIT Press", "Third edition", 5000, "Algorithms, Data Structures, Complexity, Computational Theory", false, false, 2009, "In library");
    Book d2 = new Book("Algorithms + Data Structures = Programs", "Niklaus Wirth", "Prentice Hall PTR", "First edition", 5000, "Algorithms, Data Structures, Search Algorithms, Pascal", true, false, 1978, "In library");
    Book d3 = new Book("The Art of Computer Programming", "Donald E. Knuth", "Addison Wesley Longman Publishing Co., Inc.", "Third edition", 5000, "Algorithms, Combinatorial Algorithms, Recursion", false, false, 1997, "In library");
    //AV d3 = new AV("Null References: The Billion Dollar Mistake", ": Tony Hoare", 700, "av1");
    //AV av2 = new AV("Information Entropy", "Claude Shannon", 1, "av2");
    User p1 = new User("Sergey Afonso", "30001", "Via Margutta, 3", User.professor, "p1", "1@1.r");
    User p2 = new User("Nadia Teixeira", "30002", "Via Sacra, 13", User.professor, "p2", "2@2.r");
    User p3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", User.professor, "p3", "sadasd@gmail.com");
    User s = new User("Andrey Velo", "30004", "Avenida Mazatlan 250", User.student, "s", "sasdasd@mail.com");
    User v = new User("Veronika Rama", "30005", "Stret Atocha, 27", User.vProfessor, "vp", "solovov305@gmail.com");
    Admin admin1 = new Admin("Admin", "!", "1", 0, "Admin", "0", "r@r.ur");
    Librarian l1 = new Librarian("Eugenia Rama", "l1", "l1", 1, "Librarian Priv1", "l1", "l1@mail.ru");
    Librarian l2 = new Librarian("Luie Ramos", "l2", "l2", 1, "Librarian Priv2", "l2", "l2@mail.ru");
    Librarian l3 = new Librarian("Ramon Valdez", "l3", "l3", 1, "Librarian Priv3", "l3", "l3@mail.ru");

    public Tests() throws SQLException {
    }

    private void initially() throws SQLException {
        db.admin_creation(admin1);
    }

    public void tc1() throws SQLException {
        dump();
        initially();
        Admin admin2 = new Admin("admin", "admin", "admin", 999, "Admin", "admin", "admin@mail.ru");
        ResultSet rs = stmt.executeQuery("SELECT  * FROM users_of_the_library WHERE Type= 'Admin'");
        rs.last();
        int admin_amount = rs.getRow();
        assert (admin_amount == 1);
        db.admin_creation(admin2);
        rs = stmt.executeQuery("SELECT  * FROM users_of_the_library WHERE Type= 'Admin'");
        rs.last();
        admin_amount = rs.getRow();
        assert (admin_amount == 1);
    }

    public void tc2() throws SQLException {
        dump();
        initially();
        admin1.add_librarian(l1);
        admin1.add_librarian(l2);
        admin1.add_librarian(l3);
        assert ((Integer) db.isUserAlreadyExist(l1).get(0) == 1);
        assert ((Integer) db.isUserAlreadyExist(l2).get(0) == 1);
        assert ((Integer) db.isUserAlreadyExist(l3).get(0) == 1);
    }

    public void tc3() throws SQLException {
        tc2();
        l1.addBook(d1.getTitle(), d1.getAuthor(), d1.getPublisher(), d1.getEdition(), d1.getPrice(), d1.getKeyWords(), d1.getBestseller(), d1.getReference(), d1.getYear(), 3);
        l1.addBook(d2.getTitle(), d2.getAuthor(), d2.getPublisher(), d2.getEdition(), d2.getPrice(), d2.getKeyWords(), d2.getBestseller(), d2.getReference(), d2.getYear(), 3);
        l1.addBook(d3.getTitle(), d3.getAuthor(), d3.getPublisher(), d3.getEdition(), d3.getPrice(), d3.getKeyWords(), d3.getBestseller(), d3.getReference(), d3.getYear(), 3);

        ResultSet rs = stmt.executeQuery("SELECT  * FROM Copy");
        rs.last();
        int copy_count = rs.getRow();
        rs = stmt.executeQuery("SELECT  * FROM Books");
        rs.last();
        int book_count = rs.getRow();
        //books and copies were not added to library
        assert (copy_count == 0);
        assert (book_count == 0);

    }

    public void tc4() throws SQLException {
        tc2();

        l2.addBook(d1.getTitle(), d1.getAuthor(), d1.getPublisher(), d1.getEdition(), d1.getPrice(), d1.getKeyWords(), d1.getBestseller(), d1.getReference(), d1.getYear(), 3);
        l2.addBook(d2.getTitle(), d2.getAuthor(), d2.getPublisher(), d2.getEdition(), d2.getPrice(), d2.getKeyWords(), d2.getBestseller(), d2.getReference(), d2.getYear(), 3);
        l2.addBook(d3.getTitle(), d3.getAuthor(), d3.getPublisher(), d3.getEdition(), d3.getPrice(), d3.getKeyWords(), d3.getBestseller(), d3.getReference(), d3.getYear(), 3);

        int d1ID = (Integer) db.isBookAlreadyExist(d1).get(1);
        int d2ID = (Integer) db.isBookAlreadyExist(d2).get(1);
        int d3ID = (Integer) db.isBookAlreadyExist(d3).get(1);

        l2.addUser(p1);
        l2.addUser(p2);
        l2.addUser(p3);
        l2.addUser(s);
        l2.addUser(v);

        //3 librarians:l1,l2,l3 and users p1,p2,p3,s,v
        //admin is not a user,he is an admin(HE IS GOD)
        assert (l2.getAllUsers().size() == 8);
        assert (l2.getNumberOfCopiesOfBook(d1ID) == 3);
        assert (l2.getNumberOfCopiesOfBook(d2ID) == 3);
        assert (l2.getNumberOfCopiesOfBook(d3ID) == 3);
    }

    public void tc5() throws SQLException {
        tc4();
        int idOfCopy = 0;
        stmt = db.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT  * FROM copy WHERE Id_of_original =" + (Integer) db.isBookAlreadyExist(d1).get(1));
        while (rs.next())
            idOfCopy = rs.getInt("Id_of_copy");
        l3.deleteOneCopy(idOfCopy);

        assert (l3.getNumberOfCopiesOfBook((Integer) db.isBookAlreadyExist(d1).get(1)) == 2);
    }

    public void tc6() throws SQLException {
        tc4();
        int d3ID = (Integer) db.isBookAlreadyExist(d3).get(1);
        get_user_in_table(p1);
        get_user_in_table(p2);
        get_user_in_table(p3);
        get_user_in_table(s);
        get_user_in_table(v);
        l1.checkOut(p1, d3ID);
        l1.checkOut(p2, d3ID);
        l1.checkOut(s, d3ID);
        l1.checkOut(v, d3ID);
        l1.checkOut(p3, d3ID);
        l1.outstandingRequest(d3ID);
        ResultSet rs = stmt.executeQuery("SELECT * FROM Copy WHERE Id_of_original=" + d3ID + " and Owner<>0");
        rs.last();
        int copy_count = rs.getRow();
        assert (copy_count == 3);
        rs = stmt.executeQuery("SELECT  * FROM queue_on_" + d3ID);
        rs.last();
        int awaiting = rs.getRow();
        assert (awaiting == 2);
    }

    public void tc7() throws SQLException {
        tc4();
        int d3ID = (Integer) db.isBookAlreadyExist(d3).get(1);
        l3.checkOut(p1, d3ID);
        l3.checkOut(p2, d3ID);
        l3.checkOut(s, d3ID);
        l3.checkOut(v, d3ID);
        l3.checkOut(p3, d3ID);
        l3.outstandingRequest(d3ID);
        DatabaseMetaData md = db.con.getMetaData();
        ResultSet rs = md.getTables(null, null, "queue_on" + d3ID, null);
        assert (!rs.next());
        //There is no way to check if email was sent or not
        //anyway,all emails are collected in class librarian
        //method outstanding request
        // in debug mode it is possible to see all notified emails
    }

    public void tc10() throws SQLException {
        tc4();
        Search search=new Search();
        ArrayList<Book> result=search.bookByTitle("Introduction to Algorithms");
        assert(result.size()==1);
    }
    public void tc11() throws SQLException{
        tc4();
        Search search=new Search();
        ArrayList<Book> result=search.bookByTitle("Algorithms");
        assert (result.size()==2);
    }
    public void tc12() throws SQLException{
        tc4();
        Search search=new Search();
        ArrayList<Book> result=search.bookByKeywords("Algorithms");
        assert (result.size()==3);
    }
    public void tc13()throws SQLException{
        tc4();
        Search search=new Search();
        ArrayList<Book>result=search.bookByTitle("Algorithms Programming");
        assert (result.size()==0);
    }
    /*
    public void tc2() throws SQLException {
        dump();
        initially();
        admin1.add_librarian(l1);
        admin1.add_librarian(l2);
        admin1.add_librarian(l3);
        assert((Integer) db.isUserAlreadyExist(l1).get(0)==1);
        assert((Integer) db.isUserAlreadyExist(l2).get(0)==1);
        assert((Integer) db.isUserAlreadyExist(l3).get(0)==1);
    }
    /*
    public void tc3() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.of(2018,3,29);
        l.checkOutWithData(p1,d1.getId(),date);
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);

        LocalDate date2 = LocalDate.of(2018,4,2);


        l.renewWithDate(p1,l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId(),date2);
        l.renewWithDate(s,l.getAllCopiesTakenByUser(s.getCard_number()).get(0).getId(),date2);
        l.renewWithDate(v,l.getAllCopiesTakenByUser(v.getCard_number()).get(0).getId(),date2);


        assert (l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,30)));
        assert (l.getAllCopiesTakenByUser(s.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,16)));
        assert (l.getAllCopiesTakenByUser(v.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,9)));
    }

    public void tc4() throws SQLException {
        dump();
        initially();
        LocalDate date = LocalDate.parse("2018-03-29");
        LocalDate now = LocalDate.parse("2018-04-02");
        l.checkOutWithData(p1,d1.getId(),date);
        l.checkOutWithData(s,d2.getId(),date);
        l.checkOutWithData(v,d2.getId(),date);
        ArrayList<ArrayList<String>> emails=l.outstandingRequestWithDate(d2.getId(),now);

        LinkedList<Material> p1doc = l.getAllCopiesTakenByUser(p1.getCard_number());
        LinkedList<Material> sdoc = l.getAllCopiesTakenByUser(s.getCard_number());
        LinkedList<Material> vdoc = l.getAllCopiesTakenByUser(v.getCard_number());

        l.renewWithDate(p1, p1doc.get(0).getId(),now);
        l.renewWithDate(s, sdoc.get(0).getId() ,now);
        l.renewWithDate(v, vdoc.get(0).getId(),now);

        p1doc = l.getAllCopiesTakenByUser(p1.getCard_number());
        sdoc = l.getAllCopiesTakenByUser(s.getCard_number());
        vdoc = l.getAllCopiesTakenByUser(v.getCard_number());
        assert (p1doc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-30")));
        assert (sdoc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-02")));
        assert (vdoc.get(0).getReturnDate().equals(LocalDate.parse("2018-04-02")));

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
        ArrayList<ArrayList<String>> all_emails=l.outstandingRequestWithDate(d3.getId(),now);
        ArrayList<String> with_already_taken_book=all_emails.get(0);
        ArrayList<String> waiting_for_book= all_emails.get(1);
        assert (l.getQueue(d3.getId()).size() == 0);
        assert (with_already_taken_book.get(0).equals(p1.getEmail()));
        assert (with_already_taken_book.get(1).equals(p2.getEmail()));
        assert (waiting_for_book.get(0).replaceAll(" ","").equals(s.getEmail()));
        assert (waiting_for_book.get(1).replaceAll(" ","").equals(v.getEmail()));
        assert (waiting_for_book.get(2).replaceAll(" ","").equals(p3.getEmail()));
    }
    public void tc8() throws SQLException, InterruptedException {
        tc6();
        Notification_thread my=new Notification_thread();
        my.start();
        LinkedList<Material> p2doc = l.getAllCopiesTakenByUser(p2.getCard_number());
        l.returnBook(p2doc.get(0).getId());
        LinkedList<Material> p2docAfter = l.getAllCopiesTakenByUser(p2.getCard_number());

        ArrayList<User> usQueue= l.getQueue(d3.getId());
        assert (usQueue.get(0).getCard_number()==s.getCard_number());
        assert (usQueue.get(1).getCard_number()==v.getCard_number());
        assert (usQueue.get(2).getCard_number()==p3.getCard_number());
        Thread.sleep(3000);
        ArrayList temp= new ArrayList();
        ResultSet rs= stmt.executeQuery("SELECT * FROM queue_on_"+ d3.getId());
        while (rs.next()){
            temp.add(rs.getString("is_sended"));
        }
        assert(temp.get(0).equals("1"));
        assert(p2docAfter.size()==0);
        my.interrupt();
    }

    public void tc9() throws SQLException {
        dump();
        initially();
        tc6();
        l.renewWithDate(p1,l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId(),LocalDate.of(2018,4,16));

        assert (l.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getReturnDate().equals(LocalDate.of(2018,4,30)));
        assert(l.getQueue(d3.getId()).get(0).getName().equals("Andrey Velo"));
        assert(l.getQueue(d3.getId()).get(1).getName().equals("Veronika Rama"));
        assert(l.getQueue(d3.getId()).get(2).getName().equals("Elvira Espindola"));

    }
    public void tc10() throws SQLException{

        dump();
        initially();

        LocalDate date = LocalDate.of(2018,3,26);
        Librarian.checkOutWithData(p1, d1.getId(), date );
        Librarian.checkOutWithData(v, d1.getId(), date);

        date = LocalDate.of(2018,3,29);
        Librarian.renewWithDate(p1, Librarian.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId(), date);
        Librarian.renewWithDate(v, Librarian.getAllCopiesTakenByUser(v.getCard_number()).get(0).getId(), date);

        Librarian.renew(p1, Librarian.getAllCopiesTakenByUser(p1.getCard_number()).get(0).getId());
        Librarian.renew(v, Librarian.getAllCopiesTakenByUser(v.getCard_number()).get(0).getId());

        LinkedList<Material> p1Copy = Librarian.getAllCopiesTakenByUser(p1.getCard_number());
        LinkedList<Material> vCopy = Librarian.getAllCopiesTakenByUser(v.getCard_number());

        assert (p1Copy.size() == 1 && p1Copy.get(0).getTitle().equals("Introduction to Algorithms") && p1Copy.get(0).getReturnDate().isEqual(date.plusDays(28)));
        assert (vCopy.size() == 1 && vCopy.get(0).getTitle().equals("Introduction to Algorithms") && vCopy.get(0).getReturnDate().isEqual(LocalDate.now().plusDays(7)));

    }

    /**
     * executing update in all tables
     */
    public void dump() {
        try {
            PreparedStatement pr = db.con.prepareStatement("DELETE from AV");
            pr.executeUpdate();
            pr = db.con.prepareStatement("TRUNCATE Copy");
            pr.executeUpdate();
            pr = db.con.prepareStatement("TRUNCATE Articles");
            pr.executeUpdate();
            pr = db.con.prepareStatement("TRUNCATE Books");
            pr.executeUpdate();
            pr = db.con.prepareStatement("TRUNCATE Users_of_the_library");
            pr.executeUpdate();
            DatabaseMetaData md = db.con.getMetaData();
            ResultSet rs = md.getTables(null, null, "queue%", null);
            while (rs.next()) {
                String table_name = rs.getString(3);
                pr.executeUpdate("DROP TABLE IF EXISTS " + table_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void get_user_in_table(User user) throws SQLException {
        //name,number,adress,type,password,email
        int id = (Integer) db.isUserAlreadyExist(user).get(1);
        user.setCardNumberAsString(id);
    }
}
