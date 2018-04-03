package main.java.librinno.model;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class Notification_thread extends Thread {
    public Notification_thread() {
    }

    Database db = new Database();

    @Override
    public void run() {
        while (!isInterrupted()) {
            send_email();
            hours24Deletion();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void send_email() {
        SendEmail sendEmail = new SendEmail();
        sendEmail.send();
    }

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