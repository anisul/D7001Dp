package core;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CSAClientWorker implements Runnable {
    private static Logger log = Logger.getLogger(CSAClientWorker.class);

    @FXML
    private TextArea outputField;

    private Socket socket;
    private String command;


    public CSAClientWorker(TextArea outputField, String url, Integer port, String command) {
        try {
            this.outputField = outputField;
            this.socket = new Socket(url, port);
            this.command = command;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(this.command);

            //flushes the stream
            out.flush();

            String feed = null;
            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //read from socket input stream (responses from server)
            while ((feed = in.readLine()) != null) {
                sb.append(feed);
                sb.append("\n");
            }
            //settings server response to GUI
            outputField.setText(sb.toString());
            return;
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
