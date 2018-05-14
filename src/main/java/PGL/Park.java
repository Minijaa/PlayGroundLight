package PGL;

import java.util.ArrayList;
import java.util.Random;

public class Park {
    private String id;
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private int visitors;

    public Park(String id, int NoOfSensors, int maxVisitors) {
        Random rnd = new Random();
        if (visitors < 0 || NoOfSensors < 1) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        visitors = rnd.nextInt(maxVisitors + 1);
        for (int i = 0; i < NoOfSensors; i++) {
            sensors.add(new Sensor(maxVisitors));
        }
    }

    public String getId() {
        return id;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public int getVisitors() {
        return visitors;
    }

    public int updateVisitors() {
        int visitorDiff = 0;

        for (int i = 0; i < sensors.size(); i++) {

            visitorDiff += sensors.get(i).updateVisitors(visitors);
            System.out.println("sensor update running:" + visitorDiff);
        }
        visitors += visitorDiff;

        if (visitors < 0) {
            visitors = 0;
        }
        System.out.println("updated visitors:" + visitors);
        return visitors;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
