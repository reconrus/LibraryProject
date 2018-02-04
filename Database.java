package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * database with users in it
 */
public class Database {
    //for connection to database
    public String url = "jdbc:mysql://127.0.0.1:3306/librinno";
    public String login = "root";
    public String password = "170199Dima";
    public static PreparedStatement prst;
    public static Connection con;

    /**
     * connecting to mysql database
     */
    public Database() {
        prst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getting users of database to project
     * @param user - user of library
     */
    public static void user_creation(User user) {
        try {
            //get all needed information
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
    //test for entering information in database
    //done for test
    public void test(User user) throws SQLException {

        user.set_adress("somewhere");
        user.set_number("880005553535");
        user.set_name("someone");
        user.set_card_number();

        PreparedStatement pr_st =
                Database.con.prepareStatement("insert into users_of_library(username, phoneNumber, address, card_number) values(?, ?, ?, ?)");
        pr_st.setString(1,user.get_name());
        pr_st.setString(2,user.get_number());
        pr_st.setString(3,user.get_adress());
        pr_st.setInt(4,user.get_card_number());
        pr_st.executeUpdate();
    }
}