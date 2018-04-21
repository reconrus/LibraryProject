package main.java.librinno.ui.adminScreen;

//this class is used to make logs applicable to show in tables
public class LogsForTable {
    private String date;
    private String event;

    /**
     * parses string of log to arr of dates and events so it could be displayed in table
     * @param a
     */
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
