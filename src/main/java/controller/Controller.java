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

    }
}
