package problem4;

import java.util.ArrayList;

/**
 * Created by Team 10
 */
public class WaitingQueue {
    public static ArrayList<Customer> queue;

    public static void main(String[] args){
        queue = new ArrayList<Customer>();

        // start aquisition
        Acquisition acquisition = new Acquisition();
        Thread acquisitionThread = new Thread(acquisition);
        acquisitionThread.start();

        // start cashpoint
        Cashpoint cashpoint = new Cashpoint();
        Thread cashpointThread = new Thread(cashpoint);
        cashpointThread.start();

        try{
            acquisitionThread.join();
            cashpointThread.join();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }

        System.out.println("Mission completed");
    }

    public static int getSizeOfQueue(){
        return queue.size();
    }
}
