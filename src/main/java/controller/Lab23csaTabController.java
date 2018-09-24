package controller;

import core.CSAClientWorker;
import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab23csaTabController {
    @FXML
    private TextField urlInputField;

    @FXML
    private TextField portInputField;

    @FXML
    private TextField commandInputField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button executeButton;

    @FXML
    private Button clearButton;

    @FXML
    private void handleClearButtonAction (ActionEvent event) {
        urlInputField.setText("");
        portInputField.setText("");
        commandInputField.setText("");
    }

    @FXML
    private void handleExecuteButtonAction(ActionEvent event) {
        Window owner = executeButton.getScene().getWindow();

        String url = urlInputField.getText();
        String port = portInputField.getText();
        String command = commandInputField.getText();

        if (url.isEmpty()
                || port.isEmpty()
                || command.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Command!",
                    "Please enter a URL, Port & Command");
            outputTextArea.setText("");
            return;
        } else {
            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            CSAClientWorker clientWorker = new CSAClientWorker(this.outputTextArea, url, Integer.parseInt(port), command);
            threadPool.execute(clientWorker);
        }
    }
}
