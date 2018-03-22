package main.java.librinno.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * user card in library
 */
public class User {
    public String name;
    public String adress;
    public int card_number;
    public String phoneNumber;
    public String type;
    public String password;


    private String queue_date;
    public String  getDate() {
        return ""+queue_date;
    }
    public void setDate(String  returnDate){
        this.queue_date = returnDate;
    }
    //constructors for different cases
    //in some situations not all information is needed
    //for queue
    public User(int card_number,String type,String queue_date){
     setCardNumberAsString(card_number);
     setType(type);
     setDate(queue_date);
    }
    public User(String name, String number, String address, String type, String password) {
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        //setCard_number();
        setType(type);
        setPassword(password);
    }

    public User(String name, String address, String number, int cardnumber, String type, String password) {
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        setCardNumberAsString(cardnumber);
        setType(type);
        setPassword(password);
    }

    //getters and setters
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

    public int getCard_Number() {

        return card_number;
    }

}
