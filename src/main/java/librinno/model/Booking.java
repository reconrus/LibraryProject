package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Booking {
    public boolean check_out(int zaregID, int bookID) {
        try {

            Statement ps2 = Database.con.createStatement();
            ResultSet rs = ps2.executeQuery("SELECT Type from Users_of_the_library WHERE Card_number=" + zaregID);
            if (rs.next()) {
                String type = rs.getString("Type");

                PreparedStatement setTime = Database.con.prepareStatement("Update Books set time_left=? where id=?");

                ResultSet book = ps2.executeQuery("SELECT owner, is_bestseller from Books WHERE id=" + bookID);

                if(book.next()) {

                    if(book.getInt("owner") != 0) return false;

                    Boolean is_bestseller = book.getBoolean("is_bestseller");

                    book.close();

                    if (type.equals("Student")) {

                        if (is_bestseller)
                            setTime.setInt(1, 14);
                        else setTime.setInt(1, 21);

                    } else setTime.setInt(1, 28);

                    setTime.setInt(2, bookID);
                    setTime.executeUpdate();
                    setTime.close();

                    PreparedStatement ps = Database.con.prepareStatement("Update Books set owner=? where id=?");
                    ps.setInt(1, zaregID);
                    ps.setInt(2, bookID);
                    ps.executeUpdate();
                    ps.close();

                    return true;
                }

            }

            //ResultSet users=ps2.executeQuery("SELECT Type from Users_of_the_library WHERE Card_number=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
