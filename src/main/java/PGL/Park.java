package PGL;

import java.util.ArrayList;

public class Park {
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private int visitors;

    public Park(int NoOfSensors, int initialVisitors){
        visitors = initialVisitors;
        for(int i = 0; i < NoOfSensors; i++){
            sensors.add(new Sensor());
        }
    }

    public ArrayList<Sensor> getSensors(){
        return sensors;
    }

    public int getVisitors(){
        return visitors;
    }

    public int updateVisitors(){

    }

}
