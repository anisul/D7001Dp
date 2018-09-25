package core.sma;

import interfaces.Deliverable;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsService implements Deliverable {
    public static final int DNS_SERVICE_MESSAGE = 101;
    public static final int DNS_SERVICE_PORT = 4999;

    @Override
    public Message send(Message m) {
        try {
            InetAddress inetAddress = InetAddress.getByName(m.getParam("query"));
            m.setParam("resolved-address", inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return m;
    }

    public static void main(String args[]) {
        DnsService ds = new DnsService();
        MessageServer ms;
        try {
            ms = new MessageServer(DNS_SERVICE_PORT);
        } catch(Exception e) {
            System.err.println("Could not start service " + e);
            return;
        }
        Thread msThread = new Thread(ms);
        ms.subscribe(DNS_SERVICE_MESSAGE, ds);
        msThread.start();
    }
}
