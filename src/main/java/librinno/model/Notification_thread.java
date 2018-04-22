package main.java.librinno.model;

import java.sql.*;
import java.text.ParseException;
import java.util.Date;

/**
 * Second thread for sending emails to users
 */
public class Notification_thread extends Thread {
    /**
     * constructor
     */
    public Notification_thread() {
    }

    Database db = new Database();

    /**
     * main, launching method
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            send_email();
            hours24Deletion();
            try {
                Thread.sleep(1);
            }
            catch (NullPointerException e){}
            catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * sending emails t users
     */
    public void send_email() {
        try {
            SendEmail sendEmail = new SendEmail();
            sendEmail.send();
            sendEmail.clean_receivers();
        }
        catch (NullPointerException n){}
    }

    /**
     * deletion of the user from the queue after 24 hours
     */
    public void hours24Deletion() {
        Database db = new Database();
        try {
            DatabaseMetaData md = db.con.getMetaData();
            Statement stmt = db.con.createStatement();
            ResultSet rs = md.getTables(null, null, "queue%", null);
            while (rs.next()) {
                String table_name = rs.getString(3);
                try {
                    ResultSet table_rs = stmt.executeQuery("SELECT * FROM " + table_name + " LIMIT 1");
                    if (table_rs.next()) {
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat dateFormat =
                                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = dateFormat.parse(table_rs.getString("First_time"));
                        Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
                        if (dt.after(tomorrow)) {
                            PreparedStatement pr = db.con.prepareStatement("DELETE from " + table_name + " LIMIT 1");
                            pr.executeUpdate();
                        }
                    }
                } catch (SQLException e) {
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}