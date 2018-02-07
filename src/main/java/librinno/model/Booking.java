package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Booking {
    public void check_out(int zaregID, int bookID) {
        try {
            Scanner sc = new Scanner(System.in);

            PreparedStatement ps = Database.con.prepareStatement("Update Books set owner=? where id=?");
            ps.setInt(1, zaregID);
            ps.setInt(2, bookID);
            ps.executeUpdate();
            ps.close();

            Statement ps2 = Database.con.createStatement();
            ResultSet rs = ps2.executeQuery("SELECT Type from Users_of_the_library WHERE Card_number=" + zaregID);
            if (rs.next()) {
                String type = rs.getString("Type");
                System.out.println(type);
            }
            ResultSet users=ps2.executeQuery("SELECT Type from Users_of_the_library WHERE Card_number=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
