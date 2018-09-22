package controller;

import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;
import org.apache.log4j.Logger;
import utils.BashCommandUtil;

import java.io.File;
import java.io.FileNotFoundException;

public class Controller {
    final static Logger log = Logger.getLogger(Controller.class);

    @FXML
    private TextField commandField;

    @FXML
    private TextArea outputField;

    @FXML
    private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();

        if (commandField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Command!",
                    "Please enter a valid command");
            outputField.setText("");
            return;
        } else {
            String command = commandField.getText().trim().toLowerCase();

            if (command.equals("pwd") || command.equals("chdir")) {
                outputField.setText(BashCommandUtil.getPresentDirectory());
                log.info("a pwd/chdir command has been executed.");
            } else if (command.equals("ls") || command.equals("dir")) {
                outputField.setText(BashCommandUtil.getCurrentDirectoryFiles());
                log.info("a ls/dir command has been executed.");
            } else if (command.equals("who") || command.equals("whoami")) {
                outputField.setText(BashCommandUtil.getCurrentLoggedUser());
            } else if (command.equals("ps") || command.equals("tasklist")) {
                outputField.setText(BashCommandUtil.getAllRunningProcess());
            } else if (command.contains("cat") || command.contains("more")) {
                outputField.setText(BashCommandUtil.getFileContent(command));
            } else if (command.equals("ifconfig") || command.equals("ipconfig")) {
                outputField.setText(BashCommandUtil.getIpConfig());
            } else if (command.equals("uptime") || command.contains("net statistics")) {
                outputField.setText(BashCommandUtil.getMachineUptime());
            }
        }
    }
}
