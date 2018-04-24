package main.java.librinno.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Main {
    static String DB_URL;
    private static String USER;
    private static String PASS;
    private static final Logger LOGGER = Logger.getLogger("GLOBAL");

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
        PropertyConfigurator.configure("log4j.properties");
        //We need know your login and password in local server MySQL
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your login in MySQL:");
        setUSER(sc.nextLine());
        System.out.println("Write your password in MySQL:");
        setPASS(sc.nextLine());
        setDbUrl("jdbc:mysql://localhost:3306");
        Database db = new Database();
        db.creationLocalDB(getUSER(), getPASS());
        Tests tests = new Tests();
        tests.tc1();
        System.out.println("Test 1 success");
        tests.tc2();
        System.out.println("Test 2 success");
        tests.tc3();
        System.out.println("Test 3 success");
        tests.tc4();
        System.out.println("Test 4 success");
        tests.tc5();
        System.out.println("Test 5 success");
        tests.tc6();
        System.out.println("Test 6 success");
        tests.tc7();
        System.out.println("Test 7 success");
        tests.tc10();
        System.out.println("Test 10 success");
        tests.tc11();
        System.out.println("Test 11 success");
        tests.tc12();
        System.out.println("Test 12 success");
        tests.tc13();
        System.out.println("Test 13 success");
        tests.tc14();
        System.out.println("Test 14 success");
    }
}