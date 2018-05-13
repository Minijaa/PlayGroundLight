package PGL;

import java.util.Random;

public class Sensor {

    private Random rnd = new Random();


    public int updateVisitors(int visitors){
        int updatedValue = 0;
        if(visitors > 35 && visitors < 65) {
            updatedValue = (5 - (rnd.nextInt(11)));
        }else if(visitors <= 35){
            updatedValue = (8 - (rnd.nextInt(11)));
        } else if(visitors >= 65){
            updatedValue = (2 - (rnd.nextInt(11)));
        } else if(visitors > 100){
            updatedValue = (0 - (rnd.nextInt(11)));
        }

        return updatedValue;
    }


}
