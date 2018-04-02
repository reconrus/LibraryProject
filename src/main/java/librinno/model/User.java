package main.java.librinno.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * user card in library
 */
public class User {
    //TODO Check Types
    public static final String student = "Student";
    public static final String professor = "Professor";
    public static final String instructor = "Instructor";
    public static final String ta = "TA";
    public static final String vProfessor = "Visiting Professor";


    public String name;
    public String adress;
    public int card_number;
    public String phoneNumber;
    public String type;
    public String password;
    public String email;
    public Boolean isNotified;
    public ArrayList<String> notification=new ArrayList<>();

    public String queue_date;

    public void setQueue_date(String queue_date) {
        this.queue_date = queue_date;
    }

    public String getQueue_date() {
        return queue_date;
    }

    public String getDate() {
        return "" + queue_date;
    }

    public void setDate(String returnDate) {
        this.queue_date = returnDate;
    }

    //constructors for different cases
    //in some situations not all information is needed
    //for queue
    public User(int card_number, String type, String queue_date,ArrayList<String> notification,String email) {
        setCardNumberAsString(card_number);
        setType(type);
        setDate(queue_date);
        set_notifications(notification);
        setEmail(email);
    }

    public User(int card_number, String reservationDate, Boolean isNotified) {
        setCardNumberAsString(card_number);
        setDate(reservationDate);
        setIsNotified(isNotified);
    }


    public User(String name, String number, String address, String type, String password, String email) {
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        setType(type);
        setPassword(password);
    }

    public User(String name, String address, String number, int cardnumber, String type, String password,String email) {
        setName(name);
        setPhoneNumber(number);
        setAdress(address);
        setCardNumberAsString(cardnumber);
        setType(type);
        setPassword(password);
        setEmail(email);
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){this.email=email;}
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

    public ArrayList<String> get_notifications() {
        return notification;
    }

    public void set_notifications(ArrayList<String> notification) {
        this.notification = notification;

    }

    public Boolean getIsNotified() {
        return isNotified;
    }

    public void setIsNotified(Boolean notified) {
        isNotified = notified;
    }
}
