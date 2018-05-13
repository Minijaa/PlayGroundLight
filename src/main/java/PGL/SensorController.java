package PGL;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "10.200.3.220", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
public class SensorController { //AKA en lekplats, som ska ha flera sensorer egentligen. Kanske lägger till en sensor klass som simulerar att flera kommer och går från olika håll senare, ganska onödigt för själva visningen dock.
    private Random rnd = new Random();
    private Set<Park> parks = new HashSet<>();

    //Behöver en metod som hämtar alla parkID från stockholm APIT,
    // för att vara nycklar i "sensors" HashMap och vara värden för @RequestMapping


    //Konstruktorn lägger till mellan 2 och 5 sensorer
    public SensorController(){
        String[] parks = new String[]{"P1", "P2", "P3", "P4", "P5"}; //Detta bör ersättas med en array fyllt av alla parkID, spelar ingen roll om det är strängar eller något annat egentligen, bara det går att urskilja dem, strängar hade underlättat.
        ArrayList<Sensor> tempArr = new ArrayList<>();
        int numberOfSensors;

//        for(int j = 0 ; j < 5 ; j++){ //denna går igenom varje lekpark och lägger till sensorer, 5 parker för testsyften
//            numberOfSensors = rnd.nextInt(3) + 2;
//            for (int i = 0 ; i < numberOfSensors ; i++){
//                tempArr.add(new Sensor());
//            }
//            sensors.put(parks[j], tempArr);
//            tempArr.clear();
//        }

    }

    public void updateVisitors(){
        //insert code for getting the right park, and using the method from that
    }

    @RequestMapping("/sensors")
    public int getParkVisitors(@RequestParam(value = "parkid") String parkId) {
        boolean found = false; //tror inte denna boolean egentligen behövs pga ordningen på return statements, men blir en säkerhetsåtgärd
        for (Park p : parks) {
            if (p.getId().equalsIgnoreCase(parkId)) {
                System.out.println("Found");
                found = true;
                p.updateVisitors();
                return p.getVisitors();
            }
        }
        if (!found){

            Park newPark = new Park(parkId, rnd.nextInt(3)+2, rnd.nextInt(100));
            parks.add(newPark);
            System.out.println("Creating new with visitors:" + newPark.getVisitors());
            return newPark.getVisitors();
        }
        return 0;
        //throw new IllegalArgumentException("The requested resource does not exist");
    }


}
