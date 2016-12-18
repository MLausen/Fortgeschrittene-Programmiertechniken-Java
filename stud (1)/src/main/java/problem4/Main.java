package problem4;

import Helper.ErrorDialog;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;

/**
 * Created by Team 10
 */
public class Main {
    public static void main(String[] args){
        Lock rLock = new ReentrantLock();

        // start aquisition
        Acquisition acquisition = new Acquisition(rLock);
        Thread acquisitionThread = new Thread(acquisition);
        acquisitionThread.start();

        CashpointService.getInstance().openCashpoint();

        // print when every thread has terminated
        // join()...
        System.out.println("Mission completed");
    }
}
