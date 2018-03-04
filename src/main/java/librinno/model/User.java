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

    public int zaregID;
    //all needed information for library
    public String name;
    public String adress;
    public int card_number;
    public String Phone_Number;
    public PreparedStatement pr_st;
    public String type;
    public String password;
    /**
     * creating user
     */
    User() {
        // now entering in simple form
        //get information about user
        Scanner sc = new Scanner(System.in);
        set_name(sc.next());
        set_number(sc.next());
        set_adress(sc.next());
        set_card_number();
        set_type(sc.next());
        set_password(sc.next());
    }
    public User(String name, String number, String address, String type, String password) {
        // now entering in simple form
        //get information about user
        set_name(name);
        set_number(number);
        set_adress(address);
        set_card_number();
        set_type(type);
        set_password(password);
    }
    public User(String name, String address,String number,  int cardnumber,String type, String password) {
        // now entering in simple form
        //get information about user
        set_name(name);
        set_number(number);
        set_adress(address);
        set_another_card_number(cardnumber);
        set_type(type);
        set_password(password);
    }
public void set_password(String password){
        this.password=password;
}
public String get_password(){
    return password;
}
    public void set_type(String type) {
        this.type = type;
    }
    public void set_another_card_number(int id){this.card_number=id;}
    public String get_another_card_number(){return ""+card_number;}
    public String get_type() {
        return type;
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

    public String getPhone_Number() {
        return Phone_Number;
    }

    public int getZaregID() {
        return zaregID;
    }

    /**
     * @param name of user
     */
    public void set_name(String name) {
        this.name = name;
    }

    /**
     * @return getting name of user
     */
    public String get_name() {
        return name;
    }

    /**
     * @param number of user
     */
    public void set_number(String number) {
        this.Phone_Number = number;
    }

    /**
     * @return number of user
     */
    public String get_number() {
        return Phone_Number;
    }

    /**
     * @param adress of user
     */
    public void set_adress(String adress) {
        this.adress = adress;
    }

    /**
     * @return where user lives
     */
    public String get_adress() {
        return adress;
    }

    /**
     * increasing is number in system when registering
     */
    public void set_card_number() {
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

    /**
     * @return id number os user
     */
    public int get_card_number() {

        return card_number;
    }

    public void giveID(int id){
        zaregID= id;
    }



}
