package PGL;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Game {
    private String name;
    private String gameTitle;
    private String rules;
    private String imgurl;
    private int timeStartInterval;
    private int timeStopInterval;
    private Timer lightLitDurationTimer;
    private Random ran = new Random();
    private GameController gc;
    private int delay;
    private int initialDelay;
    private int redGreenFlipCounter = 0; // Bör ändras till boolean

    //private String lightConfig; //this is just a reminder that it should have some sort of config for how the lights are used

    public Game(String name) {
        this.name = name;
    }

    public Game(String name, GameController gc) {
        this.name = name;
        this.gc = gc;

        switch (name) {
            case "runhere":
                timeStartInterval = 7;
                timeStopInterval = 18;
                initialDelay = 10;
                imgurl = "assets/imgs/defaultmix.jpg";
                gameTitle = "Spring hit";
                rules = "Låt alla barn ställa sig mot lampan som lyser gult, detta är startfärgen. Efter 10 sekunder börjar lampan växla mellan rött och grönt. Vid grönt rör sig deltagarna mot lampan. När lampan plötsligt blir röd måste alla barnen stå helt stilla. Den som rör sig kallas tillbaka till startlinjen. Den som först når fram till lyktstolpen vinner. ";
                break;
            case "redlamp":
                timeStartInterval = 2;
                timeStopInterval = 7;
                initialDelay = 10;
                imgurl = "assets/imgs/defaultred.jpg";
                gameTitle = "Röda lampan";
                rules = "Låt alla barn ställa sig mot lampan som lyser gult, detta är startfärgen. Efter 10 sekunder börjar lampan växla mellan rött och grönt. Vid grönt rör sig deltagarna mot lampan. När lampan plötsligt blir röd måste alla barnen stå helt stilla. Den som rör sig kallas tillbaka till startlinjen. Den som först når fram till lyktstolpen vinner. ";
                break;
            case "danger":
                timeStartInterval = 7;
                timeStopInterval = 18;
                initialDelay = 10;
                imgurl = "assets/imgs/defaultblue.jpg";
                gameTitle = "Farliga lampan";
                rules = "Låt alla barn ställa sig mot lampan som lyser gult, detta är startfärgen. Efter 10 sekunder börjar lampan växla mellan rött och grönt. Vid grönt rör sig deltagarna mot lampan. När lampan plötsligt blir röd måste alla barnen stå helt stilla. Den som rör sig kallas tillbaka till startlinjen. Den som först når fram till lyktstolpen vinner. ";
                break;
            default:
                throw new IllegalArgumentException("Illegal Game Type");
        }
    }
    @JsonProperty
    public String getName() {
        return name;
    }
    @JsonProperty
    public String getRules() {
        return rules;
    }
    @JsonProperty
    public int getTimeStartInterval() {
        return timeStartInterval;
    }
    @JsonProperty
    public void setRedGreenFlipCounter(int redGreenFlipCounter) {
        this.redGreenFlipCounter = redGreenFlipCounter;
    }

    public int getTimeStopInterval() {
        return timeStopInterval;
    }


    public Timer getLightLitDurationTimer() {
        return lightLitDurationTimer;
    }

    public void setLightLitDurationTimer(Timer lightLitDurationTimer) {
        this.lightLitDurationTimer = lightLitDurationTimer;
    }

    public void startGame() {
        gc.stopAllGames();
        switch (name) {
            case "runhere":
                System.out.println("Sprint hit startad");
                startGameRunHereOrDanger(1, true);
                break;
            case "danger":
                System.out.println("Farliga lampan startad");
                startGameRunHereOrDanger(0, true);
                break;
            case "redlamp":
                System.out.println("Röda lampan startad");
                startGameRedLamp(true);
        }
    }

    private void startGameRedLamp(boolean firstRun) {
        int nextRandom = ran.nextInt((timeStopInterval - timeStartInterval)) + timeStartInterval;
        if (firstRun) {
            delay = initialDelay * 1000;
            // Gör alla stolpar gula
            gc.lightAllLightPosts(gc.c("white"));
            gc.getLightPostOne().setColor(gc.c("yellow"));

        } else {
            delay = nextRandom * 1000;

        }
        lightLitDurationTimer = new Timer("lightduration", true);

        lightLitDurationTimer.schedule(new TimerTask() {
            public void run() {
                if (redGreenFlipCounter % 2 == 1) {
                    gc.getLightPostOne().setColor(1);
                    redGreenFlipCounter--;
                } else {
                    gc.getLightPostOne().setColor(0);
                    redGreenFlipCounter++;
                }

                System.out.println("delay = " + delay / 1000 + " sekunder ");
                lightLitDurationTimer.cancel();
                startGameRedLamp(false);
            }
        }, delay);
    }

    public void startGameRunHereOrDanger(int color, boolean firstRun) {
        if (color < 0 || color > 2) {
            throw new IllegalArgumentException();
        }
        redGreenFlipCounter = 0; // kan tas bort efter omvandling till boolean.

        // Ta fram en random-delay baserad på det aktuella spelets intervall-regler.
        int nextRandom = ran.nextInt((timeStopInterval - timeStartInterval)) + timeStartInterval;

        //Första delayen ska vara hårdkodad. Under delayen ska också alla lampor vara tända gula.
        if (firstRun) {
            delay = initialDelay * 1000;
            // Gör alla stolpar gula
            gc.lightAllLightPosts(gc.c("yellow"));

        } else {
            delay = nextRandom * 1000;
        }
        lightLitDurationTimer = new Timer("lightduration", true);

        lightLitDurationTimer.schedule(new TimerTask() {
            public void run() {
                //Tänd en random lampa med vald färg.
                gc.lightRandomLightPost(color);
                System.out.println("delay = " + delay / 1000 + " sekunder ");
                lightLitDurationTimer.cancel();
                startGameRunHereOrDanger(color, false);
            }
        }, delay);
    }
    @JsonProperty
    public String getImgurl() {
        return imgurl;
    }
    @JsonProperty
    public String getGameTitle() {
        return gameTitle;
    }
}
