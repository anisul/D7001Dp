package core;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ParallelLooper implements Runnable {

    private int numberOfLoop;
    private String traces;
    @FXML
    private TextArea textArea;

    public ParallelLooper(int numberOfLoop, TextArea textArea) {
        this.traces = "";
        this.numberOfLoop = numberOfLoop;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        StringBuilder traces = new StringBuilder();
        textArea.setText("waiting for output.....");
        for (int i = 0; i < numberOfLoop; i++) {
            try {
                Thread.sleep(50);
                //System.out.println("." + i);
                traces.append("." + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        textArea.setText(traces.toString());
    }
}
