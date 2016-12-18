package problem4;

import Helper.ErrorDialog;

import java.util.ArrayList;

/**
 * Created by Team 10
 */
public class Main {
    public static void main(String[] args){
        // start aquisition
        Acquisition acquisition = new Acquisition();
        Thread acquisitionThread = new Thread(acquisition);
        acquisitionThread.start();

        CashpointService.getInstance().openCashpoint();
        //...
        System.out.println("Mission completed");
    }
}
