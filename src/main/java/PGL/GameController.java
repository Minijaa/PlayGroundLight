package PGL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@JsonAutoDetect
@RestController
@CrossOrigin(origins = {"http://localhost:8100", "10.200.3.220", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
public class GameController {
    private LightPost[] lightPosts;
   //private ObjectMapper mapper = new ObjectMapper();

    public GameController() {
        lightPosts = new LightPost[]{new LightPost("LightPost1", c("white")), new LightPost("LightPost2", c("white")), new LightPost("LightPost3", c("white")), new LightPost("LightPost4", c("white"))};
        games = new Game[]{(new Game("runhere", this)), new Game("redlamp", this), new Game("danger", this)};
        //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private Game[] games;

    @RequestMapping("/game")
    public String setupGame(
            @RequestParam(value = "type") String gameType) {
        Game currentGame = null;
        for (Game g : games) {
            if (g.getName().equals(gameType)) {
                currentGame = g;
            }
        }
        if (currentGame == null) {
            throw new IllegalArgumentException("Illegal Game Type");
        } else {
            currentGame.startGame();
        }
        return currentGame.getName() + " started";
    }

    public int c(String color) {
        switch (color) {
            case "red":
                return 0;
            case "green":
                return 1;
            case "yellow":
                return 2;
        }
        return 3;
    }

    private void startGame(Game activeGame) {
        stopAllGames();

    }

    @RequestMapping("/stopgames")
    public String stopAllGames() {
        for (Game g : games) {
            // g.getTotalGameTimer().cancel();
            if (g.getLightLitDurationTimer() != null) {
                g.getLightLitDurationTimer().cancel();
                g.setRedGreenFlipCounter(0); // Kan tas bort när variablen har ändrats till boolean.
            }
        }
        for (LightPost l : lightPosts) {
            l.setColor(3);
        }
        System.out.println("All games stopped");
        return "All games stopped";
    }

    @RequestMapping("/games")
    public Game[] getGames() {
        return games;
    }

    @RequestMapping("/lightpost")
    public LightPost getLightPost(
            @RequestParam(value = "id", defaultValue = "0") String lightPostId
    ) {
        Integer lId = Integer.parseInt(lightPostId);
        return lightPosts[lId];
    }

    public LightPost getLightPostOne() {
        return lightPosts[0];
    }

    // Light a random lightpost with at specified color
    public void lightRandomLightPost(int color) {
        if (color < 0 || color > 2) {
            throw new IllegalArgumentException("Illegal color");
        }
        Random rnd = new Random();
        int offColor;
        if (color == 0) {
            offColor = 1;
        } else {
            offColor = 0;
        }
        int lightPostToLight = rnd.nextInt(4);
        while (lightPosts[lightPostToLight].getColor() == color) {
            lightPostToLight = rnd.nextInt(4);
        }

        lightAllLightPosts(offColor);
        lightPosts[lightPostToLight].setColor(color);
        for (LightPost l : lightPosts) {
            System.out.println(l.getName() + " har färgen " + l.getColor());
        }
        System.out.println(lightPosts[lightPostToLight].getName() + " är tänd med färgen " + color);

    }

    // Light all lightposts green, red, or yellow
    public void lightAllLightPosts(int color) {
        if (color < 0 || color > 3) {
            throw new IllegalArgumentException("Illegal color");
        }
        for (LightPost l : lightPosts) {
            l.setColor(color);
        }
    }
}
