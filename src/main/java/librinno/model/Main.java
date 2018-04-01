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

    public static void setDbUrl(String dbUrl) {
        DB_URL = dbUrl+"/dmitrdbk?useSSL=false";
    }

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
        setDbUrl("jdbc:mysql://localhost:3306");
        Database db = new Database();
        db.creationLocalDB(getUSER(), getPASS());


        //Tests test=new Tests();
        // test.dump();
        // test.tc1();
        /*System.out.println("TC1 SUCCESS");
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
        */
        /*String student = "Student";
        String instructor = "Instructor";
        String TA = "TA";
        String professor = "Professor";
        Comparator<String> comparator = new StringLengthComparator();
        PriorityQueue<String> pq =
                new PriorityQueue<String>(10, comparator);
        pq.add(professor);
        pq.add(TA);
        pq.add(instructor);
        pq.add(student);
        pq.add(professor);
        pq.add(TA);
        pq.add(instructor);
        pq.add(student);
        while (!pq.isEmpty())
            System.out.println(pq.poll());
            */
        //db.queue_on_material(1, 31);
        //db.queue_on_material(1, 32);
        //db.queue_on_material(1, 33);
        //db.queue_on_material(1, 37);
        //db.queue_on_material(1, 36);
        //db.queue_on_material(1,38);
        //db.queue_on_material(1, 32);
        //db.queue_on_material(500, 38);
       // Librarian l = new Librarian("1", "1", "1", 123123, "123", "12`3","1");
        //User user=l.UserById(35);
        //User user2=l.UserById(37);
       // User user3=l.UserById(32);
        //l.checkOut(user,500);
        ////l.checkOut(user,37);
       // l.checkOut(user3,1);
        //db.notification(36);
        //db.notification(33);
        //db.notification(32);
        //Notification_thread my=new Notification_thread();
       // my.run();
        SendEmail send = new SendEmail();
        while(true)
        send.send();
        //for(int i=0;i<db.send_email().size();i++)
        //    System.out.println(db.send_email().get(i));

    }
}