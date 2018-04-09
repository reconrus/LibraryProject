package main.java.librinno.model;

import java.sql.*;

public class Admin extends User {
    public static PreparedStatement prst=null;
    public Admin(String name, String address, String number, int cardnumber, String type, String password, String email) {
        super(name, address, number, cardnumber, type, password, email);
    }
    public static void modify_librarian(User user) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("UPDATE Users_of_the_library " +
                    "SET Name=?,Address=?,Phone_number=?,Type=?,Password=?, Email=?,Privilege=? where Card_number=" + user.getCard_Number()
                    + " and Type = 'Librarian'");
            pr.setString(1, user.getName());
            pr.setString(2, user.getAdress());
            pr.setString(3, user.getPhoneNumber());
            pr.setString(4,user.getType());
            pr.setString(5, user.getPassword());
            pr.setString(6, user.getEmail());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete_librarian(int user_id) {
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id+" and Type= 'Librarian'");
            pr.executeUpdate();
        } catch (SQLException e) {
            System.out.println("librarian not found");
        }
    }
    public static void add_librarian(User user) {
        try {
            Database db=new Database();
            prst = db.con.prepareStatement("insert into Users_of_the_library(Name, Address, Phone_number,Type,Password,Email,Privilege) values(?, ?, ?,?,?,?,?)");
            prst.setString(1, user.getName());
            prst.setString(2, user.getAdress());
            prst.setString(3, user.getPhoneNumber());
            prst.setString(4, "Librarian");
            prst.setString(5, user.getPassword());
            prst.setString(6,user.getEmail());
            prst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}