package problem4;

import java.util.*;

/**
 * Created by Team 10
 */
public class Balance {
    private static Balance instance;

    private HashMap<Integer, Double> cashpointToRevenue;// Integer: Id of Cashpoint, Double: sales

    public Balance(){
        cashpointToRevenue = new HashMap<>();

        for(Cashpoint c : CashpointService.getInstance().getCashpoints()){
            cashpointToRevenue.put(c.getId(), 0.0);
        }
    }

    public static Balance getInstance(){
        if(instance == null){
            instance = new Balance();
        }
        return instance;
    }

    public List<Map.Entry<Integer, Double>> getCashpointsDescByRevenue(){
        Set<Map.Entry<Integer, Double>> entries = cashpointToRevenue.entrySet();
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(entries);
        Collections.sort(entryList, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                // sortiervorschrift
                if(o1.getValue() < o2.getValue()){
                    return 1;
                }
                if(o1.getValue() > o2.getValue()){
                    return -1;
                }
                return 0;
            }
        });


        System.out.println("- cashpoint " + entryList.get(0).getKey() + " with revenue "  + entryList.get(0).getValue() + " -");
        System.out.println("- cashpoint " + entryList.get(1).getKey() + " with revenue "  + entryList.get(1).getValue() + " -");
        System.out.println("- cashpoint " + entryList.get(2).getKey() + " with revenue "  + entryList.get(2).getValue() + " -");
        System.out.println("- cashpoint " + entryList.get(3).getKey() + " with revenue "  + entryList.get(3).getValue() + " -");
        System.out.println("- cashpoint " + entryList.get(4).getKey() + " with revenue "  + entryList.get(4).getValue() + " -");
        System.out.println("- cashpoint " + entryList.get(5).getKey() + " with revenue "  + entryList.get(5).getValue() + " -");

        return entryList;
    }

    public void addValue(int id, double price){
        this.getCashpointsDescByRevenue();
        cashpointToRevenue.replace(id, (price + cashpointToRevenue.get(id)));
    }

    public double getRevenueSum(){
        double sum = 0;
        for(Double d : cashpointToRevenue.values()){
            sum += d;
        }
        return sum;
    }
}
