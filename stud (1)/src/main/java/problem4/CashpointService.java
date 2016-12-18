package problem4;

import Helper.ErrorDialog;

import java.util.ArrayList;

/**
 * Created by Team 10
 */
public class CashpointService {
    private static CashpointService instance;
    public ArrayList<Cashpoint> cashpoints;

    private CashpointService(){
        cashpoints = new ArrayList<Cashpoint>();

        cashpoints.add(new Cashpoint(1));
        cashpoints.add(new Cashpoint(2));
        cashpoints.add(new Cashpoint(3));
        cashpoints.add(new Cashpoint(4));
        cashpoints.add(new Cashpoint(5));
        cashpoints.add(new Cashpoint(6));
    }

    public static CashpointService getInstance(){
        if(instance == null){
            instance = new CashpointService();
        }
        return instance;
    }

    public void openCashpoint(){
        Thread current = null;
        for(int i = 0; i < 6; i++){
            // if cashpoint still closed
            if(!cashpoints.get(i).isOpen()) {
                System.out.println("Next Cashpoint to run: " + i);
                current = new Thread(cashpoints.get(i));
                break; // stop search for unemployed cashpoint
            }
        }

        if(current == null){
            ErrorDialog.error("Sorry, every cashpoint is busy");
        }else {
            current.start();
        }
    }

    public Cashpoint getCashpointWithLowestCustomerAmount(){
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints){
            if(cp.isOpen() && temp.getQueueSize() > cp.getQueueSize()){
                temp = cp;
            }
        }
        return temp;
    }

    public Cashpoint getCashpointWithHighestCustomerAmount(){
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints){
            if(cp.isOpen() && temp.getQueueSize() < cp.getQueueSize()){
                temp = cp;
            }
        }
        return temp;
    }

    public int getHighestCustomerAmount(){
        return getCashpointWithHighestCustomerAmount().getQueueSize();
    }

    public Cashpoint getCashpointById(int id){
        for(int i = 0; i < 6; i++){
            if(cashpoints.get(i).getId() == id) {
                return cashpoints.get(i);
            }
        }
        return null;
    }

    public void checkForReopeningCashpoint(){
        for(Cashpoint c : cashpoints){
            if(c.getQueueSize() > 5) {
                openCashpoint();
                return; // important, do not delete --> same cashpoint would be opened 5 times
            }
        }
    }
}
