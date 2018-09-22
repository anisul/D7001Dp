package utils;

import com.sun.javafx.PlatformUtil;
import org.apache.log4j.Logger;

import java.io.*;

public class BashCommandUtil {
    private static Logger log = Logger.getLogger(BashCommandUtil.class);

    public static String getIpConfig () {
        if (PlatformUtil.isWindows()) {
            return executeCommand("cmd.exe /c ipconfig");
        } else if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c ifconfig");
        } else {
            return "";
        }
    }

    public static String getMachineUptime() {
        if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c uptime");
        } else if (PlatformUtil.isWindows()) {
            return executeCommand("cmd.exe /c net statistics workstation");
        } else {
            return "";
        }
    }

    public static String getCurrentLoggedUser () {
        if (PlatformUtil.isWindows()) {
            return executeCommand("cmd.exe /c whoami");
        } else if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c who");
        } else {
            return "";
        }
    }

    public static String getCurrentDirectoryFiles() {
        String currentDirectory = System.getProperty("user.dir");
        if (PlatformUtil.isWindows()) {
            return executeCommand("cmd.exe /c dir");
        } else if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c ls");
        } else {
            return "";
        }
    }

    public static String getPresentDirectory() {
        if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c pwd");
        } else if (PlatformUtil.isWindows()) {
            return executeCommand("cmd.exe /c chdir");
        } else {
            return "";
        }
    }

    public static String getAllRunningProcess() {
        if (PlatformUtil.isWindows()) {
            return executeCommand(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
        } else if (PlatformUtil.isLinux()) {
            return executeCommand("sh -c ps -few");
        } else {
            return "";
        }
    }

    public static String getFileContent(String command) {
        String[] commandParts = command.split(" ");
        if (PlatformUtil.isWindows()) {
            return executeCommand("cmd /c more" + " " + commandParts[1]);
        } else if (PlatformUtil.isLinux()){
            return executeCommand("sh -c cat" + " " + commandParts[1]);
        } else {
            return "";
        }
    }

    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                output.append(line + "\n");
            }
            input.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
