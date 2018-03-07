
package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import static main.java.librinno.model.Librarian.article_by_id;
import static main.java.librinno.model.Librarian.av_by_id;
import static main.java.librinno.model.Librarian.bookByID;

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
    User user2 = new User("Nadia Teixeira", "Via Sacra, 13", "30002", "Student", "p2");
    User user3 = new User("Elvira Espindola", "30003", "Via del Corso, 22", "Student", "p3");
    public static boolean checkOutBook(User user, int idOfBook, LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfBook + " AND Status= 'In library' LIMIT 1 ");
            Book book = bookByID(idOfBook);
            if (!book.isReference()) {
                pr.setInt(1, user.getCard_number());
                if (user.get_type().equals("Student") && book.isIs_bestseller()) {
                    pr.setInt(2, 14);//если студент и книга бестселлер, то ставим 14 дней
                    LocalDate returnDate = date.plusDays(14);
                    pr.setDate(4, java.sql.Date.valueOf(returnDate));
                } else if (user.get_type().equals("Student") && !book.isIs_bestseller()) {
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

    public static boolean checkOutAV(User user, int idOfAV, LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfAV + " AND Status= 'In library' LIMIT 1 ");
            AV av = av_by_id(idOfAV);
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

    public static boolean checkOutArticle(User user, int idOfArticle, LocalDate date) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Copy SET Owner=?,Time_left=?,Status=?,Return_date=? WHERE Id_of_original= " + idOfArticle + " AND Status= 'In library' LIMIT 1 ");
            Article article = article_by_id(idOfArticle);
            if (!article.getReference()) {
                pr.setInt(1, user.getCard_number());
                pr.setInt(2, 14);
                LocalDate returnDate = date.plusDays(14);
                pr.setDate(4, java.sql.Date.valueOf(returnDate));
                pr.setString(3, "Issued");
                pr.executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkOut(User user, int idMaterial, LocalDate date){
        boolean success = false;
        Statement stmt= null;
        try {
            Database db = new Database();
            stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books WHERE id =" + idMaterial);
            if(rs.next()){
                success = checkOutBook(user, idMaterial, date);
            }
            rs = stmt.executeQuery("SELECT * FROM AV WHERE id =" + idMaterial);
            if(rs.next()){
                success = checkOutAV(user, idMaterial, date);
            }
            rs = stmt.executeQuery("SELECT * FROM Articles WHERE id =" + idMaterial);
            if(rs.next()){
                success = checkOutArticle(user, idMaterial, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public void tc1() throws SQLException {
        l.add_book(book1.getTitle(), book1.getAuthor(), book1.getPublisher(), book1.getEdition(), book1.getPrice(), book1.getKeyWords(), book1.isIs_bestseller(), book1.isReference(), book1.getYear(), 3);
        l.add_book(book2.getTitle(), book2.getAuthor(), book2.getPublisher(), book2.getEdition(), book2.getPrice(), book2.getKeyWords(), book2.isIs_bestseller(), book2.isReference(), book2.getYear(), 2);
        l.add_book(book3.getTitle(), book3.getAuthor(), book3.getPublisher(), book3.getEdition(), book3.getPrice(), book3.getKeyWords(), book3.isIs_bestseller(), book3.isReference(), book3.getYear(), 1);
        l.add_AV(av1.getTitle(), av1.getAuthor(), av1.getPrice(), av1.getKeyWords(), 1);
        l.add_AV(av2.getTitle(), av2.getAuthor(), av2.getPrice(), av2.getKeyWords(), 1);


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

        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user).get(1)).get_name());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user).get(1)).get_adress());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user).get(1)).getPhone_Number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user).get(1)).get_card_number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user).get(1)).get_type());

        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_name());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_adress());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).getPhone_Number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_card_number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_type());
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
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_name());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_adress());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).getPhone_Number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_card_number());
        System.out.println(db.get_information_about_the_user((Integer) db.isUserAlreadyExist(user3).get(1)).get_type());
    }

    public void tc5() {
        try {
            if ((Integer) db.isUserAlreadyExist(user2).get(0) == 1)
                l.checkOutBook(user2, (Integer) db.isBookAlreadyExist(book2).get(1));
            else
                System.out.println("This user not in system");
        } catch (SQLException e) {
            System.out.println("This user not in system");
            e.printStackTrace();
        }
    }

    public void tc6() throws SQLException {
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user3, (Integer) db.isBookAlreadyExist(book1).get(1));
        l.checkOutBook(user, (Integer) db.isBookAlreadyExist(book2).get(1));
        System.out.println(user.get_name());
        System.out.println(user.get_adress());
        System.out.println(user.getPhone_Number());
        System.out.println(db.isUserAlreadyExist(user).get(1));
        System.out.println(user.get_type());
        LinkedList<Material> linkedList = l.get_all_copies_taken_by_user((Integer) db.isUserAlreadyExist(user).get(1));
        for (int i = 0; i < linkedList.size(); i++)
            System.out.println(linkedList.get(i).getTitle() + ", " + linkedList.get(i).getReturnDate());

        System.out.println(user3.get_name());
        System.out.println(user3.get_adress());
        System.out.println(user3.getPhone_Number());
        System.out.println(db.isUserAlreadyExist(user3).get(1));
        System.out.println(user3.get_type());
        LinkedList<Material> linkedList2 = l.get_all_copies_taken_by_user((Integer) db.isUserAlreadyExist(user3).get(1) + 1);
        for (int i = 0; i < linkedList2.size(); i++)
            System.out.println(linkedList2.get(i).getTitle() + ", " + linkedList2.get(i).getReturnDate());
    }
    public void tc7() throws SQLException{
        //tc1();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDate date=LocalDate.of(2018,03,5);
        checkOutBook(user, (Integer) db.isBookAlreadyExist(book1).get(1),date);
        checkOutBook(user, (Integer) db.isBookAlreadyExist(book2).get(1),date);
        checkOutBook(user, (Integer) db.isBookAlreadyExist(book3).get(1),date);
        checkOutAV(user,(Integer) db.isAVAlreadyExist(av1).get(1),date);

        checkOutBook(user2, (Integer) db.isBookAlreadyExist(book1).get(1),date);
        checkOutBook(user2, (Integer) db.isBookAlreadyExist(book2).get(1),date);
        checkOutAV(user2,(Integer) db.isAVAlreadyExist(av2).get(1),date);
        System.out.println(user.get_name());
        System.out.println(user.get_adress());
        System.out.println(user.getPhone_Number());
        System.out.println(db.isUserAlreadyExist(user).get(1));
        System.out.println(user.get_type()+"\n");
        LinkedList<Material> linkedList = l.get_all_copies_taken_by_user((Integer) db.isUserAlreadyExist(user).get(1));
        for (int i = 0; i < linkedList.size(); i++)
            System.out.println(linkedList.get(i).getTitle() + ", " + linkedList.get(i).getReturnDate());

        System.out.println(user2.get_name());
        System.out.println(user2.get_adress());
        System.out.println(user2.getPhone_Number());
        System.out.println(db.isUserAlreadyExist(user2).get(1));
        System.out.println(user2.get_type());
        LinkedList<Material> linkedList2 = l.get_all_copies_taken_by_user((Integer) db.isUserAlreadyExist(user2).get(1));
        for (int i = 0; i < linkedList2.size(); i++)
            System.out.println(linkedList2.get(i).getTitle() + ", " + linkedList2.get(i).getReturnDate());
    }
}