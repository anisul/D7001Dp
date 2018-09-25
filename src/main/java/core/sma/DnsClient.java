package core.sma;

public class DnsClient {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please provide url, port and query");
            return;
        }
        String host = args[0];
        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch(Exception e) {
            port = DnsService.DNS_SERVICE_PORT;
        }
        MessageClient conn;
        try {
            conn = new MessageClient(host,port);
        } catch(Exception e) {
            System.err.println(e);
            return;
        }
        Message m = new Message();
        m.setType(DnsService.DNS_SERVICE_MESSAGE);
        m.setParam("person","Anis");
        m.setParam("query", args[2]);
        m = conn.call(m);
        System.out.println("Resolved host address for "+ m.getParam("query")+ " : " + m.getParam("resolved-address"));
        m.setType(75);
        m = conn.call(m);
        conn.disconnect();
    }
}
