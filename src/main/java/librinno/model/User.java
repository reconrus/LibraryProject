package main.java.librinno.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * user card in library
 */
public class User {

    //all needed information for library
    public String name;
    public String adress;
    public int card_number;
    public String phoneNumber;
    public PreparedStatement pr_st;
    public String type;
    public String password;

    public User(String name, String number, String address, String type, String password) {
        // now entering in simple form
        //get information about user
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        setCard_number();
        setType(type);
        setPassword(password);
    }

    public User(String name, String address, String number, int id, String type, String password) {
        // now entering in simple form
        //get information about user
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        setCardNumberAsString(id);
        setType(type);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCardNumberAsString(int id) {
        this.card_number = id;
    }

    public String getCardNumberAsString() {
        return "" + card_number;
    }

    public String getName() {
        return name;
    }

    public int getCard_number() {
        return card_number;
    }

    public String getAdress() {
        return adress;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param name of user
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @param number of user
     */
    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }

    /**
     * @param adress of user
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
    /**
     * increasing is number in system when registering
     */
    public void setCard_number() {
        //each card_number is individual in case of increasing there won't be identic id's
        int max_id = 0;
        try {
            //Statement stmt = Database.con.createStatement();
            Statement stmt = Database.con.createStatement();
            //get infromation from database
            ResultSet rs = stmt.executeQuery("SELECT card_number FROM Users_of_the_library");
            while (rs.next()) {
                int id = rs.getInt("card_number");
                if (id > max_id)
                    max_id = id;
            }
            max_id++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.card_number = max_id;
    }

    public int getCard_Number() {

        return card_number;
    }
}
