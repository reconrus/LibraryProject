package main.java.librinno.model;

import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail extends Database{
    private static String USER_NAME = "librinno";
    private static String PASSWORD = "thebestteamever";

    public static void send() {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = send_email().toArray(new String[send_email().size()]);
        String subject = "You can get reserved material";
        String body = "Вы зарезервировали книгу";
        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
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
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
                try {
                    DatabaseMetaData md = con.getMetaData();
                    Statement stmt = con.createStatement();
                    ResultSet rs = md.getTables(null, null, "queue%", null);
                    while (rs.next()) {
                        String table_name = rs.getString(3);
                        ResultSet table_rs = stmt.executeQuery("SELECT * FROM " + table_name + " LIMIT 1");
                        for (int i = 0; i < toAddress.length; i++) {
                            if (table_rs.next() && emailsEquals(table_rs.getString("Email"), toAddress[i].toString()) && table_rs.getBoolean("is_sended") == false) {
                                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                                message.setSubject(subject);
                                message.setText(body);
                                Transport transport = session.getTransport("smtp");
                                transport.connect(host, from, pass);
                                transport.sendMessage(message, message.getAllRecipients());
                                transport.close();
                                PreparedStatement pr = con.prepareStatement("UPDATE " + table_name + " SET is_sended=? LIMIT 1");
                                pr.setBoolean(1, true);
                                pr.executeUpdate();
                            }
                        }
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    private static boolean emailsEquals(String email1,String email2) {
        Pattern address=Pattern.compile("([\\w\\.]+)@([\\w\\.]+\\.\\w+)");
        Matcher match1=address.matcher(email1);
        Matcher match2=address.matcher(email2);
        if(!match1.find() || !match2.find()) return false; //Not an e-mail address? Already false
        if(!match1.group(2).equalsIgnoreCase(match2.group(2))) return false; //Not same serve? Already false
        switch(match1.group(2).toLowerCase()) {
            case "gmail.com":
                String gmail1=match1.group(1).replace(".", "");
                String gmail2=match2.group(1).replace(".", "");
                return gmail1.equalsIgnoreCase(gmail2);
            default: return match1.group(1).equalsIgnoreCase(match2.group(1));
        }
    }
}