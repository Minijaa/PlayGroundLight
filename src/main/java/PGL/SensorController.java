package PGL;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "10.200.3.220", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
public class SensorController { //AKA en lekplats, som ska ha flera sensorer egentligen. Kanske lägger till en sensor klass som simulerar att flera kommer och går från olika håll senare, ganska onödigt för själva visningen dock.
    private Set<Park> parks = new HashSet<>();
    private Random rnd = new Random();

    @RequestMapping("/sensors") //returnerar -1 om den inte funkade.
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
        if (!found) {
/*
    Detta (new Park delen) är det enda som behöver ändras för att ändra antalet max visitors, deviansen på hur mkt folk som lämnar/tillkommer är som mest "(maxVisitors/10)*NoOfSensors)
 */
            Park newPark = new Park(parkId, 1, 50, (rnd.nextInt(40) + 5));
            parks.add(newPark);
            System.out.println("Creating new with visitors:" + newPark.getVisitors());
            return newPark.getVisitors();
        }
        return -1;

    }


}
