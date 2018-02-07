package main.java.librinno.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Test {

    private static String press = "Press Enter to continue \n";
    private static Scanner in;

    public static void main(String[] args) throws SQLException {

        Database db = new Database();

        in = new Scanner(System.in);
//tests
        test_1();
        test_2();
        test_3();
        test_4();
        test_5();
        test_6();
        test_7();
        test_8();
        test_9();
        test_10();
    }

    private static void test_1() throws SQLException {
        defaultTable();
        System.out.println("Test 1");

        printUsers();
        printBooks();

        System.out.println("Ilnur tries to check out Touch of Class \n" + press);
        in.nextLine();

        Booking booking = new Booking();
        if (booking.check_out(1, 2)) System.out.println("Checking out was successful \n\n");

        printBooks();
        System.out.println(press + "\n");
        defaultTable();
        in.nextLine();
    }

    private static void test_2() throws SQLException {
        defaultTable();
        System.out.println("Test 2");
        printUsers();
        printBooks();
        System.out.println("Ilnur tries to check out a book of author A\n" + press);
        in.nextLine();
        Booking booking = new Booking();
        if (booking.check_out_by_author(1, "A"))
            System.out.println("Checking out was successful\n");
        else
            System.out.println("Author not found!!");
        printBooks();
        defaultTable();
        System.out.println(press + "\n");
    }

    private static void test_3() throws SQLException {
        defaultTable();
        System.out.println("Test 3");

        printUsers();
        printBooks();

        System.out.println("Shilov tries to check out Touch of Class \n" + press);
        in.nextLine();

        Booking booking = new Booking();
        if (booking.check_out(2, 2)) System.out.println("Checking out was successful \n\n");

        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }


    private static void test_4() throws SQLException {
        defaultTable();
        System.out.println("Test 4");

        printUsers();
        printBooks();

        System.out.println("Shilov tries to check out War And Peace  \n" + press);
        in.nextLine();
        Booking booking = new Booking();
        if (booking.check_out(2, 1)) System.out.println("Checking out was successful \n\n");

        printBooks();
        defaultTable();
        System.out.println("We have noticed the contradiction between test suite and project description.\nTest suite says to set time on 2 weeks but " +
                "description for 4 weeks for faculty members REGARDLESS the book is best seller. So we decided to choose project description requirments.\n" + press + "\n");
        in.nextLine();
    }

    private static void test_5() throws SQLException {
        defaultTable();
        System.out.println("Test 5");
        printUsers();
        printBooks();
        System.out.println("Ilnur,Ilnur1 and Ilnur2 are trying to take book Touch of class,which there are only 2 in library");
        Booking booking = new Booking();
        booking.check_out(1, 2);
        booking.check_out(3, 3);
        booking.check_out(4, 3);
        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }

    private static void test_6() throws SQLException {
        defaultTable();
        System.out.println("Test 6");
        printUsers();
        printBooks();
        Booking booking = new Booking();
        booking.check_out(1, 2);
        System.out.println("Ilnur tries to get 2nd copy of book Touch of class");
        booking.check_out(1, 3);
        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }

    private static void test_7() throws SQLException {
        defaultTable();
        System.out.println("Test 7");

        printUsers();
        printBooks();

        System.out.println("Shilov tries to check out Touch of Class  \n" + press);
        in.nextLine();

        Booking booking = new Booking();
        if (booking.check_out(2, 2)) System.out.println("Checking out was successful \n\n");

        printBooks();

        System.out.println("Ilnur tries to check out Touch of Class  \n" + press);
        in.nextLine();
        if (booking.check_out(1, 3)) System.out.println("Checking out was successful \n\n");
        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }

    private static void test_8() throws SQLException {
        defaultTable();
        System.out.println("Test 8");

        printUsers();
        printBooks();

        System.out.println("Ilnur tries to check out Touch of Class  \n" + press);
        in.nextLine();

        Booking booking = new Booking();
        if (booking.check_out(1, 2)) System.out.println("Checking out was successful \n\n");

        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();

    }

    private static void test_9() throws SQLException {
        defaultTable();
        System.out.println("Test 9");
        printUsers();
        printBooks();
        System.out.println("Ilnur tries to check out War And Peace  \n" + press);
        in.nextLine();
        Booking booking = new Booking();
        if (booking.check_out(1, 1)) System.out.println("Checking out was successful \n\n");

        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }

    private static void test_10() throws SQLException {
        defaultTable();
        System.out.println("Test 10");
        printUsers();
        printBooks();
        System.out.println("Ilnur tries to take Touch of class(not reference and Harry Potter(reference))");
        Booking booking = new Booking();
        booking.check_out(1, 2);
        booking.check_out(1, 4);
        printBooks();
        defaultTable();
        System.out.println(press + "\n");
        in.nextLine();
    }

    private static void printBooks() throws SQLException {
        Statement ps2 = Database.con.createStatement();
        ResultSet rs = ps2.executeQuery("SELECT * from Books");

        System.out.println("Books \n");
        System.out.println("id       Name                Author          Is Bestseller       Owner       Days left    Is_reference");

        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String author = rs.getString(3);
            String isBestseller;
            int owner = rs.getInt(8);
            if (rs.getInt(9) == 0) isBestseller = "False";
            else isBestseller = "True";
            int days = rs.getInt(10);
            Boolean is_reference = rs.getBoolean(11);
            System.out.println(id + "       " + name + "       " + author + "          " + isBestseller + "                " + owner + "             " + days + "      " + is_reference);
        }
        System.out.println("\n");
    }


    private static void printUsers() throws SQLException {
        Statement ps2 = Database.con.createStatement();
        ResultSet rs = ps2.executeQuery("SELECT * from Users_of_the_library");

        System.out.println("Users in the system \n");
        System.out.println("ID       Name       Type");

        while (rs.next()) {
            int id = rs.getInt(4);
            String name = rs.getString(1);
            String type = rs.getString(5);

            System.out.println(id + "       " + name + "       " + type);
        }

        System.out.println("\n");
    }


    private static void defaultTable() throws SQLException {

        PreparedStatement set = Database.con.prepareStatement("Update Books set time_left=?, owner=?");
        set.setInt(1, 999);
        set.setInt(2, 0);
        set.executeUpdate();
        set.close();
    }


}
