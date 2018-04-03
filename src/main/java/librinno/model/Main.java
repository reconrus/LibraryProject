package main.java.librinno.model;

import java.sql.SQLException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class Main {
    static String DB_URL;

    private static String USER;
    private static String PASS;

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUrlwdbk() {
        return DB_URL + "/dmitrdbk?useSSL=false";
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }

    private static Notification_thread nthread;

    public static final String getPASS() {
        return PASS;
    }

    public static void setPASS(String PASS) {
        Main.PASS = PASS;
    }

    public static final String getUSER() {
        return USER;
    }

    public static void setUSER(String USER) {
        Main.USER = USER;
    }

    public static void main(String[] args) throws SQLException, InterruptedException {

        //We need know your login and password in local server MySQL
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your login in MySQL:");
        setUSER(sc.nextLine());
        System.out.println("Write your password in MySQL:");
        setPASS(sc.nextLine());
        setDbUrl("jdbc:mysql://localhost:3306");
        Database db = new Database();
        db.creationLocalDB(getUSER(), getPASS());
        Notification_thread my = new Notification_thread();
        my.start();
        Tests test = new Tests();
        test.tc1();
        System.out.println("Test 1 success");
        Thread.sleep(1000);
        test.tc2();
        System.out.println("Test 2 success");
        Thread.sleep(1000);
        test.tc3();
        System.out.println("Test 3 success");
        Thread.sleep(1000);
        test.tc4();
        System.out.println("Test 4 success");
        Thread.sleep(1000);
        test.tc5();
        System.out.println("Test 5 success");
        Thread.sleep(1000);
        test.tc6();
        System.out.println("Test 6 success");
        Thread.sleep(1000);
        test.tc7();
        System.out.println("Test 7 success");
        Thread.sleep(1000);
        test.tc8();
        System.out.println("Test 8 success");
        Thread.sleep(1000);
        test.tc9();
        System.out.println("Test 9 success");
        Thread.sleep(1000);
        test.tc10();
        System.out.println("Test 10 success");
        my.interrupt();
    }
}