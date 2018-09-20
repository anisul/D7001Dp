package controller;

import helper.AlertHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.io.File;

public class Controller {
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
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a valid command");
            outputField.setText("");
            return;
        } else {
            String command = commandField.getText().trim().toLowerCase();

            if (command.equals("pwd") || command.equals("chdir")) {
                outputField.setText(System.getProperty("user.dir"));
            } else if (command.equals("ls") || command.equals("dir")) {
                String outputContent = "";
                File directory = new File(System.getProperty("user.dir"));
                File[] files = directory.listFiles();
                for (File file : files) {
                    String fileContent = "";
                    if (file.isDirectory()) {
                        fileContent += "Directory\t: ";
                    } else {
                        fileContent += "file\t\t: ";
                    }
                    fileContent += file.getName() + "\n";
                    outputContent += fileContent;
                }
                outputField.setText(outputContent);
            }
        }
    }
}
