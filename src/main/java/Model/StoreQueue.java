package Model;

import Controller.Writer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreQueue implements Runnable{

    public ArrayList<Client>waitingClients = new ArrayList<>();
    public int waitTime = 0;
    public Thread thread = new Thread(this);
    private int id;
    public AtomicInteger simTime = new AtomicInteger(0);
    public int maxSimTime;
    public boolean isEmpty = true;

    public FileWriter file;


    public StoreQueue(int id){
        this.id = id;
        this.waitTime = 0;
    }

    public void addClient(Client c){
        this.waitingClients.add(c);
        this.waitTime += c.gettService();
        isEmpty = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        //Writer writer = new Writer(textArea, file);
        int sim = -1;
        while(simTime.intValue() < maxSimTime){
            sim++;
            if(sim == simTime.intValue() - 1){
                break;
            }
            if(waitingClients.size() > 0) {

                Client currClient = waitingClients.get(0);
                waitingClients.get(0).setInQueue(1);
                currClient.settService(currClient.gettService() - 1);
                waitTime--;
                if(currClient.gettService() == 0) {
                    waitingClients.remove(0);
                }
                if(waitingClients.size() == 0){
                    isEmpty = true;
                }
                for(int i = 1; i < waitingClients.size(); i++){
                    waitingClients.get(i).waitingTime++;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.stop();
        return;


    }
}
