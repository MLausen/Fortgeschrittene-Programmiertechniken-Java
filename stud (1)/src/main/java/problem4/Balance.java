package problem4;

import java.util.*;

/**
 * Created by Team 10
 */
public class Balance {
    private static Balance instance;

    // <map key="cashpoint.id" value="revenue">
    private HashMap<Integer, Double> cashpointToRevenue;
    private double revenueSum = 0.0d;

    public Balance(){
        cashpointToRevenue = new HashMap<>();

        // fill map with cashpoints and revenue = 0,00€
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

    // sort cashpoint descending by revenue
    public List<Map.Entry<Integer, Double>> getCashpointsDescByRevenue(int id){
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
            for(int i =0;i<6;i++) {
                if((id-1)!= i){
                    System.out.println("- cashpoint " + entryList.get(i).getKey() + " with revenue " + entryList.get(i).getValue() + "€ -");
                }else {
                    System.out.println("- cashpoint " + entryList.get(i).getKey() + " with revenue " + "\u001B[32m" +
                            entryList.get(i).getValue() + "€ -" + "\u001B[0m");
                }
        /*System.out.println("- cashpoint " + entryList.get(1).getKey() + " with revenue "  + entryList.get(1).getValue() + "€ -");
        System.out.println("- cashpoint " + entryList.get(2).getKey() + " with revenue "  + entryList.get(2).getValue() + "€ -");
        System.out.println("- cashpoint " + entryList.get(3).getKey() + " with revenue "  + entryList.get(3).getValue() + "€ -");
        System.out.println("- cashpoint " + entryList.get(4).getKey() + " with revenue "  + entryList.get(4).getValue() + "€ -");
        System.out.println("- cashpoint " + entryList.get(5).getKey() + " with revenue "  + entryList.get(5).getValue() + "€ -");*/
            }
        System.out.println("intermediate status: " + getRevenueSum() + "€");

        return entryList;
    }

    // add payed value to revenue of cashpoint with <param value="id">
    // add payed value to summarized revenue
    public void addValue(int id, double price){
        this.setRevenueSum(price);

        double newValue = roundToPriceFormat(price, cashpointToRevenue.get(id));
        cashpointToRevenue.replace(id, newValue);

        this.getCashpointsDescByRevenue(id);
    }

    // adds price to revenue
    public void setRevenueSum(double price){
        this.revenueSum = roundToPriceFormat(price, this.revenueSum);
    }

    // returns intermediate status of revenue of all cashpoints summarized
    public double getRevenueSum(){
        return this.revenueSum;
    }

    // round to two digits format
    public double roundToPriceFormat(double price, double sum){
        double newValue = price + sum;

        newValue = newValue * 100;
        newValue = Math.round(newValue);
        newValue = newValue / 100;

        return newValue;
    }
}
