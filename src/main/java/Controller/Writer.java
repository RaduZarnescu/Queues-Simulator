package Controller;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public TextArea textArea;
    public FileWriter file;

    public void writeToGUI(String s){
        Platform.runLater( () -> textArea.appendText(s));
    }
    public void writeToFile(String s){
        try {
            file.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Writer(TextArea textArea, FileWriter file) {
        this.textArea = textArea;
        this.file = file;
    }

    public Writer() {
    }
}
