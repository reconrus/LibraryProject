package main.java.librinno.model;

import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;


public class Main {
    static final String DB_URL = "jdbc:mysql://localhost/?useSSL=false";

    private static String USER;
    private static String PASS;

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

    public static void main(String[] args) throws SQLException {

        //We need know your login and password in local server MySQL
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your login in MySQL:");
        setUSER(sc.nextLine());
        System.out.println("Write your password in MySQL:");
        setPASS(sc.nextLine());
        Database db=new Database();
        db.creationLocalDB(getUSER(),getPASS());

        Tests test=new Tests();
        test.dump();
        test.tc1();
        System.out.println("TC1 SUCCESS");
        test.dump();

        test.tc2();
        System.out.println("TC2 SUCCESS");
        test.dump();
        test.tc3();
        System.out.println("TC3 SUCCESS");
        test.dump();
        test.tc4();
        System.out.println("TC4 SUCCESS");
        test.dump();
        test.tc5();
        System.out.println("TC5 SUCCESS");
        test.dump();
        test.tc6();
        System.out.println("TC6 SUCCESS");
        test.dump();
        test.tc7();
        System.out.println("TC7 SUCCESS");
        test.dump();
        test.tc8();
        System.out.println("TC8 SUCCESS");
        test.dump();
    }
}
