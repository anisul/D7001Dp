package core.sma;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageClient {
    Socket socket;
    OutputStreamWriter out;
    InputStreamReader in;

    public MessageClient(String host, int port)
            throws IOException {
        socket = new Socket(host, port);
        out = new OutputStreamWriter(socket.getOutputStream());
        in = new InputStreamReader(socket.getInputStream());
    }

    public Message call(Message message) {
        try {
            message.putMessage(out);
        } catch(Exception e) {
            System.err.println("MessageClient (call): " + e);
            return null;
        }
        try {
            Message m = Message.getMessage(in);
            m.unpack();
            return m;
        } catch(Exception e) {
            System.err.println("MessageClient (reply): " + e);
            return new Message();
        }
    }

    public void disconnect() {
        Message m = new Message();
        m.setType(0);
        m.setParam("$disconnect","$disconnect");
        call(m);
        try {
            socket.close();
        } catch(Exception e) {
            System.err.println("MessageClient (disconnect): "+e);
        }
    }
}
