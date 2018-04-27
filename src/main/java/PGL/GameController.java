package PGL;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "file://"})
public class GameController {

    public static final Game[] games = new Game[]{
            new Game("Red Lights", "Some rules"),
            new Game("2", "Bla2"),
            new Game("3", "Bla3")
    };



    @RequestMapping("/games")
    public Game[] getGames(){
        return games;
    }
}
