package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class LoggerTabController {
    private static Logger log = Logger.getLogger(LoggerTabController.class);
    private static final String LOG_FILE_LOCATION_PROP = "log4j.appender.file.File";
    private static final String LOG_FILE_NAME = "/log4j.properties";

    @FXML
    private Button clearLogButton;

    @FXML
    private Button printLogButton;

    @FXML
    private TextArea loggerTxtArea;

    @FXML
    private void handleClearLogButtonAction(ActionEvent event) {
        loggerTxtArea.setText("");
    }

    @FXML
    private void handlePrintLogButtonAction(ActionEvent event) {
        //TODO: add print support
    }

    @FXML
    private void initialize() {
        loggerTxtArea.setText(readLogFromFile());
    }

    private String readLogFromFile() {
        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();

        try {
            //TODO: read from properties file
            fr = new FileReader("D:\\\\D7001Dp-application.log");
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }

                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String getLogFileLocation() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("");
            prop.load(input);
        } catch (IOException e) {
            log.error("Error loading properties file.");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("Error closing stream.");
                }
            }
        }
        return prop.getProperty(LOG_FILE_LOCATION_PROP);
    }
}
