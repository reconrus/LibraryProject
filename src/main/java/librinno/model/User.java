package sample;

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
    public String Phone_Number;
    public PreparedStatement pr_st;

    /**
     * creating user
     */
   public void get_information(){
        // now entering in simple form
       //get information about user
        Scanner sc=new Scanner(System.in);
        set_name(sc.next());
        set_number(sc.next());
        set_adress(sc.next());
        set_card_number();
    }

    // maybe will be needed and modernized
   /* public void user_creation() {
        try {
            pr_st = Database.con.prepareStatement("insert into users_of_library(username, phoneNumber,address) values(?, ?,?,?)");
            pr_st.setString(1, this.name);
            pr_st.setString(2, this.Phone_Number);
            pr_st.setString(3, this.adress);
            pr_st.setInt(4,this.card_number);
            pr_st.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    */
    /**
     * @param name of user
     */
    public void set_name(String name){
        this.name=name;
    }

    /**
     * @return getting name of user
     */
    public String get_name(){
        return name;
    }

    /**
     * @param number of user
     */
    public void set_number(String number){
        this.Phone_Number=number;
    }

    /**
     * @return number of user
     */
    public String get_number(){
        return Phone_Number;
    }

    /**
     * @param adress of user
     */
    public void set_adress(String adress){
        this.adress=adress;
    }

    /**
     * @return where user lives
     */
    public String get_adress(){
        return adress;
    }

    /**
     * increasing is number in system when registering
     */
    public void set_card_number(){
        //each card_number is individual in case of increasing there won't be identic id's
        int max_id=0;
        try {
            Statement stmt = Database.con.createStatement();
            //get infromation from database
            ResultSet rs = stmt.executeQuery("SELECT card_number FROM users_of_library");
            while (rs.next()){
                int id=rs.getInt("card_number");
                if(id>max_id)
                    max_id=id;
            }
            max_id++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.card_number=max_id;
    }

    /**
     * @return id number os user
     */
    public int get_card_number(){

        return card_number;
    }
}
