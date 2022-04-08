package Controller;

import Model.Client;
import Model.Store;
import Model.StoreQueue;
import View.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML private TextArea textArea;
    @FXML private Button start;
    @FXML private TextField clientsField;
    @FXML private TextField queuesField;
    @FXML private TextField tSimField;
    @FXML private TextField tArrMinField;
    @FXML private TextField tArrMaxField;
    @FXML private TextField tSerMinField;
    @FXML private TextField tSerMaxField;

    public int checkInput(){
        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher(clientsField.getText());
        Matcher m2 = p1.matcher(queuesField.getText());
        Matcher m3 = p1.matcher(tSimField.getText());
        Matcher m4 = p1.matcher(tArrMinField.getText());
        Matcher m5 = p1.matcher(tArrMaxField.getText());
        Matcher m6 = p1.matcher(tSerMinField.getText());
        Matcher m7 = p1.matcher(tSerMaxField.getText());
        if(clientsField.getText().equals("") || queuesField.getText().equals("") || tSimField.getText().equals("") || tArrMinField.getText().equals("") || tArrMaxField.getText().equals("") || tSerMinField.getText().equals("") || tSerMaxField.getText().equals("")){
            Alert alert = new Alert();
            alert.errorAlert("Input error!", "No empty fields are allowed!");
            return -1;
        }
        if(!(m1.matches() && m2.matches() && m3.matches() && m4.matches() && m5.matches() && m6.matches() && m7.matches())){
            Alert alert = new Alert();
            alert.errorAlert("Input error!", "Only integers are allowed!");
            return -1;
        }
        if(Integer.parseInt(tArrMinField.getText()) > Integer.parseInt(tArrMaxField.getText()) || Integer.parseInt(tSerMinField.getText()) > Integer.parseInt(tSerMaxField.getText())){
            Alert alert = new Alert();
            alert.errorAlert("Interval error!", "Intervals are not correct!");
            return  -1;
        }
        return 0;
    }

    public void startBtnOnAction(javafx.event.ActionEvent event) throws IOException {
        if(checkInput() == -1){
            return;
        }
        textArea.setEditable(false);

        FileWriter file = new FileWriter("output3.txt");

        int clients = Integer.parseInt(clientsField.getText());
        int queues = Integer.parseInt(queuesField.getText());
        int tSim = Integer.parseInt(tSimField.getText());
        int tArrMin = Integer.parseInt(tArrMinField.getText());
        int tArrMax = Integer.parseInt(tArrMaxField.getText());
        int tSerMin = Integer.parseInt(tSerMinField.getText());
        int tSerMax = Integer.parseInt(tSerMaxField.getText());

        ArrayList<Client>allClients = new ArrayList<>();
        ArrayList<StoreQueue>allQueues = new ArrayList<>();

        Store store = new Store(textArea);
        store.thread.start();
        store.simTime = tSim;
        store.file = file;

        for(int i = 0; i < clients; i++){
            Client c = new Client(i+1, tArrMin, tArrMax, tSerMin, tSerMax);
            allClients.add(c);
        }
        for(int i = 0; i < queues; i++){
            StoreQueue q = new StoreQueue(i+1);
            q.file = file;
            q.maxSimTime = tSim;
            allQueues.add(q);
            q.thread.start();
        }

        store.queues = allQueues;
        store.clients = allClients;
    }
}
