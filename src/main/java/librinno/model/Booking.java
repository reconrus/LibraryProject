package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Booking {
    private boolean moreOrEqualThanOneCopyOfOneBook = false;

    public boolean checkOutByAuthor(int zaregID, String author) {
        try {
            Statement stmt = Database.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Author,id from Books");
            int id = -1;
            //in future there will be while,but not know
            if (rs.next()) {
                String book_author = rs.getString("Author");
                if (book_author.equals(author))
                    id = rs.getInt("id");
            }
            if (id != -1)
                checkOut(zaregID, id);
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkOut(int zaregID, int bookID) {
        try {
            Statement ps2 = Database.con.createStatement();
            ResultSet rs = ps2.executeQuery("SELECT Type from Users_of_the_library WHERE Card_number=" + zaregID);
            if (rs.next()) {
                String type = rs.getString("Type");
                PreparedStatement setTime = Database.con.prepareStatement("Update Books set time_left=? where id=?");
                ResultSet book = ps2.executeQuery("SELECT owner, is_bestseller,is_reference from Books WHERE id=" + bookID);
                if (book.next()) {
                    Boolean is_bestseller = book.getBoolean("is_bestseller");
                    if (type.equals("Student")) {
                        if (is_bestseller)
                            setTime.setInt(1, 14);
                        else setTime.setInt(1, 21);
                    } else setTime.setInt(1, 28);
                    setTime.setInt(2, bookID);
                    setTime.executeUpdate();
                    setTime.close();
                    if (book.getInt("owner") != 0) {
                        System.out.println("user with id " + zaregID + " can't take books,because they have finished,come later");
                        return false;
                    } else if (moreOrEqualThanOneCopyOfOneBook) {
                        System.out.println("book is already taken by this user");
                        return false;
                    }else if(book.getBoolean("is_reference")){
                        System.out.println("this is reference,impossible to take");
                        return false;
                    } else {
                        //this is for test
                        ResultSet equal_number = ps2.executeQuery("SELECT owner from Books");
                         while (equal_number.next()) {
                        if (equal_number.getInt("owner") == zaregID)
                            moreOrEqualThanOneCopyOfOneBook = true;
                    }
                        if(!moreOrEqualThanOneCopyOfOneBook)
                        System.out.println("user with id " + zaregID + " takes a book");
                        else {
                            System.out.println("user with id " + zaregID + " can't take a book");
                            return false;
                        }
                    }
                    PreparedStatement ps = Database.con.prepareStatement("Update Books set owner=? where id=?");
                    ps.setInt(1, zaregID);
                    ps.setInt(2, bookID);
                    ps.executeUpdate();
                    book.close();
                    ps.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
