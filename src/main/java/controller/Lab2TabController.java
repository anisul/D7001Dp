package controller;

import core.ParallelLooper;
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


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


    /*
    * Handle windows shell or Linux bash command
    * */
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

    /*
    * Handle looping in parallel using Thread
    * */
    @FXML
    private void handleExecuteButtonAction(ActionEvent event) {
        Window owner = executeButton.getScene().getWindow();

        if (loopCounterField.getText().isEmpty() || Integer.parseInt(loopCounterField.getText()) < 0) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Invalid input!",
                    "Please enter a valid number");
            outputField.setText("");
            return;
        } else {
            Integer numberOfLoop = Integer.parseInt(loopCounterField.getText());

            //Creating a pool of thread (of any number of threads...)
            ExecutorService threadPool = Executors.newFixedThreadPool(numberOfLoop);
            //Creating instance of Looper
            ParallelLooper worker =  new ParallelLooper(numberOfLoop, this.outputField);
            try {

                threadPool.execute(worker);
            } catch (Exception e) {
                threadPool.shutdown();
                log.info("Error occurred while looping: " + e.getMessage());
                outputField.setText("");
            }
        }
    }
}
