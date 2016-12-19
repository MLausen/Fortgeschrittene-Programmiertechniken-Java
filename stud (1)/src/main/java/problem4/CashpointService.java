package problem4;

import Helper.ErrorDialog;

import java.util.ArrayList;

/**
 * Created by Team 10
 */
// singleton that defines and administrates cashpoints
public class CashpointService {
    private static CashpointService instance;
    private ArrayList<Cashpoint> cashpoints;

    // creates cashpoints once
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

    // next unemployed cashpoint will be opened
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
            System.err.println("Sorry, every cashpoint is busy");
        }else {
            current.start();
        }
    }

    // return cashpoint with lowest customer amount
    public Cashpoint getCashpointWithLowestCustomerAmount(){
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints){
            if(cp.isOpen() && temp.getQueueSize() > cp.getQueueSize()){
                temp = cp;
            }
        }
        return temp;
    }

    // returns cashpoint  with highest customer amount
    public Cashpoint getCashpointWithHighestCustomerAmount(){
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints){
            if(cp.isOpen() && temp.getQueueSize() < cp.getQueueSize()){
                temp = cp;
            }
        }
        return temp;
    }

    // gets value of cashpoint with highest customer amount
    public int getHighestCustomerAmount(){
        return getCashpointWithHighestCustomerAmount().getQueueSize();
    }

    // return cashopint with id = <param id>
    public Cashpoint getCashpointById(int id){
        for(int i = 0; i < 6; i++){
            if(cashpoints.get(i).getId() == id) {
                return cashpoints.get(i);
            }
        }
        return null;
    }

    public void checkForCashpointToOpen(){
        for(Cashpoint c : cashpoints){
            if(c.getQueueSize() > 5) {
                openCashpoint();
                return; // important, do not delete --> same cashpoint would be opened 5 times
            }
        }
    }

    public boolean isAnyCashpointOpen(){
        for(Cashpoint cp : cashpoints){
            if(cp.isOpen()) {
                return true;
            }
        }
        return false;
    }

    // return all cashpoints as arraylist
    public ArrayList<Cashpoint> getCashpoints(){
        return this.cashpoints;
    }
}
