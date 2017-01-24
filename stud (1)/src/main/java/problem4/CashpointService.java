package problem4;

import Helper.ErrorDialog;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Team 10
 */
// singleton that defines and administrates cashpoints
public class CashpointService {
    private static CashpointService instance;
    private ArrayList<Cashpoint> cashpoints;

    // creates cashpoints once
    private CashpointService() {
        cashpoints = new ArrayList<Cashpoint>();

        Lock rLock = new ReentrantLock();

        cashpoints.add(new Cashpoint(1, rLock));
        cashpoints.add(new Cashpoint(2, rLock));
        cashpoints.add(new Cashpoint(3, rLock));
        cashpoints.add(new Cashpoint(4, rLock));
        cashpoints.add(new Cashpoint(5, rLock));
        cashpoints.add(new Cashpoint(6, rLock));
    }

    public static CashpointService getInstance() {
        if (instance == null) {
            instance = new CashpointService();
        }
        return instance;
    }

    // next unemployed cashpoint will be opened
    public void openCashpoint() {
        Thread current = null;
        int cashpointIndex = -1;

        for (int i = 0; i < 6; i++) {
            // if cashpoint still closed
            if (!cashpoints.get(i).isOpen()) {
                System.out.println("Next Cashpoint to run: " + (i + 1));
                current = new Thread(cashpoints.get(i));
                cashpointIndex = i;
                break; // stop search for unemployed cashpoint
            }
        }

        if (current == null) {
            System.err.println("Sorry, every cashpoint is busy");
            for(Cashpoint c :cashpoints)
            System.err.println("Cashpoint "+c.getId()+" has  ["+ c.getQueueSize()+"] customers");
        } else {
            System.out.println("started new thread");
            current.start();
            cashpoints.get(cashpointIndex).setCashpointOpen();
        }
    }

    // return cashpoint with lowest customer amount
    public Cashpoint getCashpointWithLowestCustomerAmount() {
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints) {
            if (cp.isOpen() && temp.getQueueSize() > cp.getQueueSize()) {
                temp = cp;
            }
        }
        return temp;
    }

    // returns cashpoint  with highest customer amount
    public Cashpoint getCashpointWithHighestCustomerAmount() {
        Cashpoint temp = cashpoints.get(0);
        for (Cashpoint cp : cashpoints) {
            if (cp.isOpen() && temp.getQueueSize() < cp.getQueueSize()) {
                temp = cp;
            }
        }
        return temp;
    }

    // gets value of cashpoint with highest customer amount
    public int getHighestCustomerAmount() {
        return getCashpointWithHighestCustomerAmount().getQueueSize();
    }

    // return cashopint with id = <param id>
    public Cashpoint getCashpointById(int id) {
        for (int i = 0; i < 6; i++) {
            if (cashpoints.get(i).getId() == id) {
                return cashpoints.get(i);
            }
        }
        return null;
    }

    // initialtes new cashpoint if all open cashpoints are busy
    public void checkForCashpointToOpen() {
        boolean success = true;

        // checks if every opened cashpoint has 6 or more customers
        for (Cashpoint c : cashpoints) {
            if (c.getQueueSize() > 5) {
                success = success && true;
            } else if (c.isOpen()) {
                success = false;
            }
        }
        if (success) {
            openCashpoint();
        }
    }

    // return true is any cashpoint is open
    // returns false if all cashpoints are closed
    public boolean isAnyCashpointOpen() {
        for (Cashpoint cp : cashpoints) {
            if (cp.isOpen()) {
                return true;
            }
        }
        return false;
    }

    // return all cashpoints as arraylist
    public ArrayList<Cashpoint> getCashpoints() {
        return this.cashpoints;
    }
}
