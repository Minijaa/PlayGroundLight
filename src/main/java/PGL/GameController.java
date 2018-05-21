package PGL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@JsonAutoDetect
@RestController
@CrossOrigin(origins = {"http://192.*", "http://localhost:8100", "10.200.3.220", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.14:8000", "http://192.168.0.14:8080", "http://192.168.0.14:80", "http://192.168.0.17:8000", "http://192.168.0.17:80", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
public class GameController {
    private LightPost[] lightPosts;
    private Game activeGame;

    public GameController() {
        lightPosts = new LightPost[]{new LightPost("LightPost1", c("white")), new LightPost("LightPost2", c("white")), new LightPost("LightPost3", c("white")), new LightPost("LightPost4", c("white"))};
        games = new Game[]{(new Game("runhere", this)), new Game("redlamp", this), new Game("danger", this)};
    }

    private Game[] games;

    @RequestMapping("/game")
    public String setupGame(
            @RequestParam(value = "type") String gameType) {
//        Game currentGame = null;
//        for (Game g : games) {
//            if (g.getName().equals(gameType)) {
//                currentGame = g;
//                currentGame.setActive(true);
//            } else {
//                g.setActive(false);
//            }
//        }
//        if (currentGame == null) {
//            throw new IllegalArgumentException("Illegal Game Type");
//        } else {
//            currentGame.startGame();
        if (activeGame == null) {
            throw new IllegalArgumentException("Illegal Game Type");
        } else {
            activeGame.startGame();
        }
        return activeGame.getName() + " started";
    }

    @RequestMapping("/difficulty")
    public void setDifficulty(
            @RequestParam(value = "level", defaultValue = "easy") String diff) {

        activeGame.setDifficulty(diff);
        System.out.println(activeGame.getName());
        System.out.println(activeGame.getDifficulty());

    }

    @RequestMapping("/setactive")
    public void setActive(
            @RequestParam(value = "gamename", defaultValue = "runhere") String gameName
    ) {
        for (Game g : games) {
            if (g.getName().equals(gameName)) {
                activeGame = g;
                //currentGame.setActive(true);
            }
        }activeGame.setDifficulty("easy");

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
