package utils;

import com.sun.javafx.PlatformUtil;
import org.apache.log4j.Logger;

import java.io.*;

public class BashCommandUtil {
    private static Logger log = Logger.getLogger(BashCommandUtil.class);

    public static String executeCommand(String command) {
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
