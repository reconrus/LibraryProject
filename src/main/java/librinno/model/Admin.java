package main.java.librinno.model;

import org.apache.log4j.PropertyConfigurator;

import java.sql.*;

/**
 * Admin class inherited from user
 * It already has all user properties
 */
public class Admin extends User {
    public static PreparedStatement prst=null;

    /**
     * Admin's constructor
     * @param name admin's name
     * @param address admin's address
     * @param number admin's number
     * @param cardnumber admin's id
     * @param type admin's type
     * @param password admin's password
     * @param email admin's email
     */
    public Admin(String name, String address, String number, int cardnumber, String type, String password, String email) {
        super(name, address, number, cardnumber, type, password, email);
    }

    /**
     * changing information of librarian
     * @param user which librarian to modify
     */
    public static void modify_librarian(User user) {
        PropertyConfigurator.configure("log4j.properties");
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
            db.get_user_in_table(user);
            LOGGER.trace("Librarian with id "+user.getCard_number()+" was modified");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("Error in librarian modification");
        }
    }

    /**
     * deletion of librarian
     * @param user_id id of deleting librarian
     */
    public static void delete_librarian(int user_id) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            Database db = new Database();
            PreparedStatement pr = db.con.prepareStatement("DELETE from Users_of_the_library WHERE Card_number=" + user_id+" and Type= 'Librarian'");
            pr.executeUpdate();
            LOGGER.trace("Librarian with id "+user_id+" was deleted");
        } catch (SQLException e) {
            LOGGER.error("Error in librarian modification,librarian not found");
        }
    }

    /**
     * adding librarian to the system
     * @param user what librarian (with all his parameters) to add
     */
    public static void add_librarian(User user) {
        PropertyConfigurator.configure("log4j.properties");
        try {
            Database db=new Database();
            prst = db.con.prepareStatement("insert into Users_of_the_library(Name, Address, Phone_number,Type,Password,Email) values(?, ?, ?,?,?,?)");
            prst.setString(1, user.getName());
            prst.setString(2, user.getAdress());
            prst.setString(3, user.getPhoneNumber());
            prst.setString(4, "Librarian");
            prst.setString(5, user.getPassword());
            prst.setString(6,user.getEmail());
            prst.executeUpdate();
            db.get_user_in_table(user);
            LOGGER.trace("Librarian with id "+user.getCard_number()+" was created");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error in librarian adding");
        }
    }
}