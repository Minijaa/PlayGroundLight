package PGL;

import java.util.Random;

public class Sensor {

    private Random rnd = new Random();
    private int deviation; //max skillnaden i besökare för varje uppdatering, tilldelas i konstruktorn
    private int maxVisitors; //max antalet besökare den räknar i en park, tilldelas i konstruktorn
    private int low;
    private int high;

    public Sensor() {
        deviation = 6;
        maxVisitors = 50;
        setBoundarys();
    }

    public Sensor(int maxVisitors) {
        this.maxVisitors = maxVisitors;
        deviation = (maxVisitors / 10) + 1;
        setBoundarys();
    }

    public void setBoundarys() {
        low = maxVisitors / 3;
        high = (int) (maxVisitors / 1.5);
    }


    public int updateVisitors(int visitors) {
        double updatedValue = 0;
        System.out.println("Deviaton =" + deviation);
        System.out.println("maxVisitors/3.0=" + (high));
        System.out.println("maxVisitors/1.5=" + (low));

        if (visitors <= (low)) {
            updatedValue = ((deviation - (deviation / 4)) - (rnd.nextInt(deviation)));
            System.out.println("Running /3 with result:" + updatedValue);
        } else if (visitors >= (high)) {
            updatedValue = ((deviation / 4) - (rnd.nextInt(deviation)));
            System.out.println("Running /1.5 with result:" + updatedValue);
        } else if (visitors > maxVisitors) {
            updatedValue = (0 - (rnd.nextInt(deviation)));
            System.out.println("Running higher than max" + updatedValue);
        } else {
            updatedValue = (deviation / 2 - (rnd.nextInt(deviation)));
            System.out.println("Running around middle" + updatedValue);
        }

        return (int) updatedValue;
    }


}
