package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
    public String url = "jdbc:mysql://127.0.0.1:3306/librinno";
    public String login = "root";
    public String password = "170199Dima";
    public static PreparedStatement prst;
    public static Connection con;

    public Database() {
        prst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void user_creation(User user) {
        try {
            prst = con.prepareStatement("insert into users_of_library(username, phoneNumber, address, card_number) values(?, ?, ?, ?)");
            prst.setString(1, user.get_name());
            prst.setString(2, user.get_number());
            prst.setString(3, user.get_adress());
            prst.setInt(4, user.get_card_number());
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}