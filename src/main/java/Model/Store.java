package Model;

import Controller.Writer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public class Store implements Runnable {
    public int simTime;
    public Thread thread = new Thread(this);
    public ArrayList<StoreQueue>queues = new ArrayList<>();
    public ArrayList<Client>clients = new ArrayList<>();

    public void sortQueues(){
        Collections.sort(queues, new SortByWaitTime());
    }
    public void sortClients(){
        Collections.sort(clients, new SortByArrivalTime());
    }

    public TextArea textArea;

    public FileWriter file;

    public Store(TextArea t){
        this.textArea = t;
    }

    @Override
    public void run() {

        Writer writer = new Writer(textArea, file);
        int peakHour = 0;
        int maxQsInService = -1;
        sortClients();
        //sortQueues();
        for(int i = 0; i <= simTime; i++) {
            final int finalI = i;
            writer.writeToGUI("\nsimulation time: " + finalI + '\n');
            writer.writeToFile("\nsimulation time: " + finalI + '\n');
            for(StoreQueue q: queues){
                q.simTime.set(i);
            }
            for (Client c : clients) {
                if (!queues.get(0).isEmpty) {
                    sortQueues();
                }
                if (c.gettArrival() == i) {
                    if(queues.get(0).waitTime == 0){
                        c.setInQueue(1);
                    }
                    queues.get(0).addClient(c);
                }
            }
            int qsInService = 0;
            for(StoreQueue q: queues){
                if(q.waitingClients.size() > 0){
                    qsInService++;
                }
            }
            if(qsInService > maxQsInService){
                maxQsInService = qsInService;
                peakHour = i;
            }
            for(StoreQueue q: queues){
                if(q.isEmpty){
                    writer.writeToGUI("queue " + q.getId() + ": closed\n");
                    writer.writeToFile("queue " + q.getId() + ": closed\n");
                }
                else{
                    writer.writeToGUI("queue " + q.getId() + ": (" + q.waitingClients.get(0).getId() + ", " + q.waitingClients.get(0).gettArrival() + ", " + q.waitingClients.get(0).gettService() + ")\n");
                    writer.writeToFile("queue " + q.getId() + ": (" + q.waitingClients.get(0).getId() + ", " + q.waitingClients.get(0).gettArrival() + ", " + q.waitingClients.get(0).gettService() + ")\n");
                }
            }
            writer.writeToGUI("waiting clients: ");
            writer.writeToFile("waiting clients: ");
            for(Client c: clients){
                if(c.getInQueue() == 0) {
                    writer.writeToGUI("(" + c.getId() + "," + c.gettArrival() + "," + c.gettService() + ") ");
                    writer.writeToFile("(" + c.getId() + "," + c.gettArrival() + "," + c.gettService() + ") ");
                }
            }
            writer.writeToGUI("\n");
            writer.writeToFile("\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int avgWait = 0;
        int avgSer = 0;
        for(Client c: clients){
            if(c.getInQueue() == 1) {
                avgWait += c.waitingTime;
                avgSer += c.waitingTime;
                avgSer += c.fixedServiceTime;
            }
        }
        avgWait = avgWait / clients.size();
        avgSer = avgSer / clients.size();
        writer.writeToGUI("Max simulation time reached.\n");
        //writer.writeToFile("Max simulation time reached.\n");

        writer.writeToGUI("Average waiting time = " + avgWait + "\n");
        //writer.writeToFile("Average waiting time = " + avgWait + "\n");

        writer.writeToGUI("Average service time = " + avgSer + "\n");
        //writer.writeToFile("Average service time = " + avgSer + "\n");

        writer.writeToGUI("Peak hour = " + peakHour + "\n");
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(StoreQueue q: queues){
            q.thread.stop();
        }
        return;
    }
}

class SortByWaitTime implements Comparator<StoreQueue>
{
    public int compare(StoreQueue a, StoreQueue b)
    {
        return a.waitTime - b.waitTime;
    }
}

class SortByArrivalTime implements Comparator<Client>
{
    public int compare(Client a, Client b)
    {
        return a.gettArrival() - b.gettArrival();
    }
}
