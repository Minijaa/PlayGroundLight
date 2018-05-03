package PGL;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
public class GameController {
    private LightPost[] lightPosts;
    private Game[] games;
    private Timer lightLitDurationTimer;
    private Random ran = new Random();
    private int delay;

    public GameController() {
        lightPosts = new LightPost[]{new LightPost("LightPost1", c("white")), new LightPost("LightPost2", c("white")), new LightPost("LightPost3", c("white")), new LightPost("LightPost4", c("white"))};
        games = new Game[]{(new Game("runhere", "Some rules", this)), new Game("redlamp", "Bla2", this), new Game("danger", "Bla3", this)};

        // Kod för att starta executorn
        //startGameRunHereOrDanger(1, true, games[0]);
//        try{
//            games[0].executor(1, true);
//        }catch (InterruptedException a){
//            //
//        }
    }

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

    public void stopAllGames() {
        for (Game g : games) {
            // g.getTotalGameTimer().cancel();
            if (g.getLightLitDurationTimer() != null){
                g.getLightLitDurationTimer().cancel();
            }

        }
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

    public LightPost getLightPostOne(){
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


//    public void initiateLightTimer(int color, boolean firstRun, Game game) {
//        if (color < 0 || color > 1) {
//            throw new IllegalArgumentException();
//        }
//        //boolean first = firstRun;
////        if (firstRun) {
////            // Lägg till en fast tid innan spelet börjar. Nedräkning 5 eller 10 sek. Ev gula lampor?
////            Timer initialTimer = new Timer();
////            int initialDelay = 1000;
////            initialTimer.schedule(new TimerTask() {
////                @Override
////                public void run() {
////                    System.out.println("GULA LAMPOR!!!");
////                    gameController.lightAllLightPosts(2);
////                    initialTimer.cancel();
////                }
////            }, initialDelay);
////        }
//        // Ta fram en random-delay baserad på det aktuella spelets intervall-regler.
//        int nextRandom = ran.nextInt((game.getTimeStopInterval() - game.getTimeStartInterval()) + game.getTimeStartInterval());
//
//        //Första delayen ska vara hårdkodad. Under delayen ska också alla lampor vara tända gula.
//        if (firstRun) {
//            delay = 5000;
//            System.out.println("Alla stolpar är GULA!!!");
////            for (int i = 0; i < lightPosts.length ; i++){
////                lightPosts[i].setColor(color);
////            }
//            lightAllLightPosts(color);
//
//        } else {
//            delay = nextRandom * 1000;
//        }
//        Timer timer = new Timer("lightduration", true);
//
//        timer.schedule(new TimerTask() {
//            public void run() {
//                //Tänd en random lampa med vald färg.
//                lightRandomLightPost(color);
//
//                System.out.println("delay = " + delay / 1000 + " sekunder ");
//                //period = 500;
//                //timer.cancel();
//                initiateLightTimer(color, false, game);
//            }
//        }, delay);
//    }

}
