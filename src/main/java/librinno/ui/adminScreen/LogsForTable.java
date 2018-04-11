package main.java.librinno.ui.adminScreen;

public class LogsForTable {
    private String date;
    private String event;

    public LogsForTable(String a){
        String[] arr= a.split(",");
        this.setDate(arr[0]);
        this.setEvent(arr[1]);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
