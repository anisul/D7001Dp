package core.sma;

import interfaces.Deliverable;

import java.util.Date;

public class DateService implements Deliverable {
    public static final int DATE_SERVICE_MESSAGE = 100;
    public static final int DATE_SERVICE_PORT = 1999;
    public Message send(Message m) {
        Date today = new Date();
        m.setParam("date", today.toString());
        return m;
    }
    public static void main(String args[]) {
        DateService ds = new DateService();
        MessageServer ms;
        try {
            ms = new MessageServer(DATE_SERVICE_PORT);
        } catch(Exception e) {
            System.err.println("Could not start service " + e);
            return;
        }
        Thread msThread = new Thread(ms);
        ms.subscribe(DATE_SERVICE_MESSAGE, ds);
        msThread.start();
    }
}
