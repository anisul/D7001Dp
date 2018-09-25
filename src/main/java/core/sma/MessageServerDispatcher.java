package core.sma;

import interfaces.Deliverable;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageServerDispatcher extends Thread {
    MessageServer callServer;
    Socket socket;
    InputStreamReader in;
    OutputStreamWriter out;

    public static final boolean logging = true;

    public MessageServerDispatcher(MessageServer ms, Socket s)
            throws IOException {
        callServer = ms;
        socket = s;
        in = new InputStreamReader(socket.getInputStream());
        out = new OutputStreamWriter(socket.getOutputStream());
    }

    public void log(String s) {
        if (!logging) return;
        System.err.println("MessageServerDispatcher: " + s);
    }

    public void run() {
        log("Beginning of dispatch run() method.");
        try {
            while (true) {
                Message m = Message.getMessage(in);
                Message result = null;
                log("Received Message " + m + ".");
                if (m.getParam("$disconnect") != null) {
                    log("$disconnect found in Message " + m);
                    System.err.println("-> Disconnect");
                    Message ack = new Message();
                    ack.pack();
                    ack.putMessage(out);
                    socket.close();
                    return;
                }

                Deliverable d;
                d = callServer.getSubscriber(m.getType());
                if (d != null)
                    result = d.send(m);
                else {
                    System.err.println("-> No subscribers found.");
                    result = new Message();
                }
                result.pack();
                result.putMessage(out);
            }
        } catch (EOFException e1) {
            try {
                log("End of file exception." + e1);
                out.close();
                socket.close();
            } catch (Exception e2) {
                log("Unable to free open resources " + e2);
                e2.printStackTrace();
            }
        } catch (Exception e) {
            log("Unknown exception " + e);
            e.printStackTrace();
        }
    }
}
