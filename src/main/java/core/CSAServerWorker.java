package core;

import com.sun.javafx.PlatformUtil;
import org.apache.log4j.Logger;
import utils.BashCommandUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CSAServerWorker implements Runnable {
    private static Logger log = Logger.getLogger(CSAServerWorker.class);

    protected Socket socket;

    public CSAServerWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String clientCommand;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientCommand = bufferedReader.readLine();

            String output = executeCommand(clientCommand);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);

            if (output != null) {
                printWriter.println(output);
                printWriter.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String executeCommand(String command) {
        String[] winCommandTemplate = {
                "cmd",
                "/c",
                command
        };

        String[] linuxCommandTemplate = {
                "/bin/sh",
                "-c",
                command
        };

        StringBuffer output = new StringBuffer();
        try {
            log.info("Executing command: " + "'" +command + "'");
            Process process = null;
            if (PlatformUtil.isWindows()) {
                process = Runtime.getRuntime().exec(winCommandTemplate);
            } else if (PlatformUtil.isLinux()) {
                process = Runtime.getRuntime().exec(linuxCommandTemplate);
            }

            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                output.append(line + "\n");
            }
            input.close();
            log.info("Successfully executed command: " + "'" +command + "'");
        } catch (IOException e) {
            log.error("Error executing command" + "'"+ command+ "' : " + e.getMessage());
        } catch (InterruptedException e) {
            log.error("Error executing command" + "'"+ command+ "' : " + e.getMessage());
        }
        return output.toString();
    }
}
