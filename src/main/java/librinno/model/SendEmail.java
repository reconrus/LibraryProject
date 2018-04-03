package main.java.librinno.model;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends Database {
    private static String USER_NAME = "librinno";
    private static String PASSWORD = "thebestteamever";
    private static ArrayList<Address> all_emails = new ArrayList<>();

    public static void send() {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = send_email().toArray(new String[send_email().size()]);
        String subject = "You can get reserved material";
        String body = "You can get the material,which you wanted to took.\n" +
                "You have 24 hours to get document.";
        sendFromGMail(from, pass, to, subject, body);
    }
    public static void sendToOne(String email,String subject,String body) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = {email};
        sending_to_one(from, pass, to, subject, body);
    }
    private static void sending_to_one(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) throws NullPointerException {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Address array[] = null;
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        Transport transport = null;
        try {
                message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                    toAddress[i] = new InternetAddress(to[i]);

            }
            for (int i = 0; i < toAddress.length; i++) {
                try {
                    DatabaseMetaData md = con.getMetaData();
                    Statement stmt = con.createStatement();
                    ResultSet rs = md.getTables(null, null, "queue%", null);
                    try {
                        while (rs.next()) {
                            String table_name = rs.getString(3);
                            ResultSet table_rs = stmt.executeQuery("SELECT * FROM " + table_name + " LIMIT 1");
                            while (table_rs.next()) {
                                String email = table_rs.getString("Email");
                                String address = toAddress[i].toString().toLowerCase();
                                boolean bool = table_rs.getInt("is_sended") == 0;
                                if (emailsEquals(email, address) && bool) {
                                    PreparedStatement pr = con.prepareStatement("UPDATE " + table_name + " SET is_sended=? LIMIT 1");
                                    pr.setInt(1, 1);
                                    pr.executeUpdate();
                                    //косяк!!!!
                                    message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                                    message.setSubject(subject);
                                    message.setText(body);
                                    transport = session.getTransport("smtp");
                                    transport.connect(host, from, pass);
                                }
                            }
                        }
                    }
                    catch (SQLException e){}
                    try {
                        for (int k = 0; k < message.getAllRecipients().length; k++)
                            if (numberOfMeetings(all_emails, message.getAllRecipients()[k].toString()))
                                all_emails.add(message.getAllRecipients()[k]);
                    }
                    catch (NullPointerException e){
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
                array = all_emails.toArray(new Address[all_emails.size()]);
            try {
                transport.sendMessage(message, array);
                transport.close();
            }
            catch (NullPointerException e){}
        }catch (MessagingException me) {
            me.printStackTrace();
        }

    }

    private static boolean emailsEquals(String email1, String email2) {
        Pattern address = Pattern.compile("([\\w\\.]+)@([\\w\\.]+\\.\\w+)");
        Matcher match1 = address.matcher(email1);
        Matcher match2 = address.matcher(email2);
        if (!match1.find() || !match2.find()) return false; //Not an e-mail address? Already false
        if (!match1.group(2).equalsIgnoreCase(match2.group(2))) return false; //Not same serve? Already false
        switch (match1.group(2).toLowerCase()) {
            case "gmail.com":
                String gmail1 = match1.group(1).replace(".", "");
                String gmail2 = match2.group(1).replace(".", "");
                return gmail1.equalsIgnoreCase(gmail2);
            default:
                return match1.group(1).equalsIgnoreCase(match2.group(1));
        }
    }

    private static boolean numberOfMeetings(ArrayList<Address> l, String id) {
        int count = 0;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).toString().equals(id))
                count++;
        }
        if (count > 1) return false;
        else return true;
    }
}