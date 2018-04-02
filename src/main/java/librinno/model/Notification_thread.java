package main.java.librinno.model;

import java.util.LinkedList;

public class Notification_thread extends Thread {
    public Notification_thread() {
    }

    Database db = new Database();

    @Override
    public void run() {
        while (!isInterrupted()) {
            send_email();
        }
    }

    public void send_email() {
        SendEmail sendEmail = new SendEmail();
        sendEmail.send();
    }
}