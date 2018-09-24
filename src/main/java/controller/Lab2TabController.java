package controller;

import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.apache.log4j.Logger;
import utils.BashCommandUtil;


public class Lab2TabController {
    private static Logger log = Logger.getLogger(Lab2TabController.class);

    @FXML
    private TextField commandField;

    @FXML
    private TextField loopCounterField;

    @FXML
    private TextArea outputField;

    @FXML
    private Button runButton;

    @FXML
    private Button executeButton;

    @FXML
    protected void handleRunButtonAction(ActionEvent event) {
        Window owner = runButton.getScene().getWindow();

        if (commandField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Wrong Command!",
                    "Please enter a valid command");
            outputField.setText("");
            return;
        } else {
            String command = commandField.getText().trim().toLowerCase();
            outputField.setText(BashCommandUtil.executeCommand(command));
        }
    }

    @FXML
    private void handleExecuteButtonAction(ActionEvent event) {

    }
}
