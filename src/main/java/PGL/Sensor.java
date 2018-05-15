package PGL;

import java.util.Random;

public class Sensor {

    private Random rnd = new Random();
    private int deviation; //max skillnaden i besökare för varje uppdatering, tilldelas i konstruktorn
    private int maxVisitors; //max antalet besökare den räknar i en park, tilldelas i konstruktorn
    private int low;
    private int high;
    private int weightPoint;

    public Sensor() {
        deviation = 6;
        maxVisitors = 50;
        weightPoint = maxVisitors/2;
        setBoundarys();
    }

    public Sensor(int maxVisitors) {
        this.maxVisitors = maxVisitors;
        deviation = (maxVisitors / 10) + 1;
        weightPoint = maxVisitors/2;
        setBoundarys();
    }

    public Sensor(int maxVisitors, int weightPoint) {
        this.maxVisitors = maxVisitors;
        deviation = (maxVisitors / 10) + 1;
        if(weightPoint > maxVisitors || weightPoint < 0){
            throw new IllegalArgumentException("weightPoint does not fall in maxVisitor range");
        }
        this.weightPoint = weightPoint;
        setBoundarys();

        System.out.println("Deviaton =" + deviation);
        System.out.println("WeightPoint =" + weightPoint);
        System.out.println("HighPoint=" + (high));
        System.out.println("LowPoint=" + (low));
    }

    public void setBoundarys() {
        int difference = weightPoint/4; //kan även vara maxVisitors/10 för mer consistent ändringar över olika parker, detta ger lite annorlunda variation dock.
        low = weightPoint - difference;
        high = weightPoint + difference;

    }


    public int updateVisitors(int visitors) {
        double updatedValue = 0;

        if (visitors <= (low)) {
            updatedValue = ((deviation - (deviation / 4)) - (rnd.nextInt(deviation)));
            System.out.println("Running Low: " + updatedValue);
        } else if (visitors >= (high)) {
            updatedValue = ((deviation / 4) - (rnd.nextInt(deviation)));
            System.out.println("Running High: " + updatedValue);
        } else if (visitors > maxVisitors) {
            updatedValue = (0 - (rnd.nextInt(deviation)));
            System.out.println("Running VERY HIGH" + updatedValue);
        } else {
            updatedValue = (deviation / 2 - (rnd.nextInt(deviation)));
            System.out.println("Running Median: " + updatedValue);
        }

        return (int) updatedValue;
    }

}
