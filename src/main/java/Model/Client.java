package Model;

import java.util.Random;

public class Client {
    private int id;
    private int tArrival;
    private int tService;
    private int inQueue = 0;
    public int waitingTime;
    public int fixedServiceTime;

    public int getInQueue() {
        return inQueue;
    }

    public void setInQueue(int inQueue) {
        this.inQueue = inQueue;
    }

    public Client(int id, int tArrivalMin, int tArrivalMax, int tServiceMin, int tServiceMax) {
        this.id = id;
        Random r = new Random();
        int tArr = r.nextInt((tArrivalMax - tArrivalMin) - 1) + tArrivalMin;
        int tSer = r.nextInt((tServiceMax - tServiceMin) - 1) + tServiceMax;
        this.tArrival = tArr;
        this.tService = tSer;
        this.waitingTime = tArrival;
        fixedServiceTime = tSer;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int gettArrival() {
        return tArrival;
    }

    public void settArrival(int tArrival) {
        this.tArrival = tArrival;
    }

    public int gettService() {
        return tService;
    }

    public void settService(int tService) {
        this.tService = tService;
    }
}
