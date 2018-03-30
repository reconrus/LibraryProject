package main.java.librinno.model;

public class Notification_thread extends Thread {
    public Notification_thread() {
    }

    Database db = new Database();

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < db.notification().size(); i++)
                System.out.println(db.notification().get(i));
            //break;
            //db.notification();
            send_email();
        }
    }

    public void send_email() {
        SendEmail sendEmail = new SendEmail();
        sendEmail.send();
    }
}