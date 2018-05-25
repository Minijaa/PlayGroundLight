package PGL;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Game {
    private String name;
    private String gameTitle;
    private String[] rules;
    private String gameHeading;
    private String imgurl;
    private int timeStartInterval;
    private int timeStopInterval;
    private Timer lightLitDurationTimer;
    private Random ran = new Random();
    private GameController gc;
    private int delay;
    private int initialDelay;
    private int redGreenFlipCounter = 0; // Bör ändras till boolean
    private String difficulty = "medium";
    private boolean lightYellow = false;
    private boolean lightRed = false;

    public Game(String name) {
        this.name = name;
    }

    public Game(String name, GameController gc) {
        this.name = name;
        this.gc = gc;

        switch (name) {
            case "runhere":
                timeStartInterval = 6;//7;
                timeStopInterval = 7;//18;
                initialDelay = 5; //10;
                //imgurl = "assets/imgs/defaultmix.jpg";
                imgurl = "assets/imgs/runhere_blue.jpg";
                gameTitle = "Spring hit";
                gameHeading = "Spring hit – följ grönt ljus";
                rules = new String[]{"1. Alla lampor lyser gult. Barnen ställer sig under samma lampa.", "2. En av lamporna växlar till grönt. Sist till den åker ut!","3. Grön färg slingas mellan lamporna tills endast ett barn återstår."};
                break;
            case "redlamp":
                timeStartInterval = 3;
                timeStopInterval = 5;
                initialDelay = 5; //10;
                //imgurl = "assets/imgs/defaultred.jpg";
                imgurl = "assets/imgs/redlight_purple.jpg";
                gameTitle = "Röda lyktan";
                gameHeading = "Röda lyktan – nå lampan först";
                rules = new String[]{"1. Alla barn står vid en startlinje vända mot en lampa.",
                        "2. Färgen växlar till grönt, det är fritt fram att röra sig mot lampan.",
                        "3. Färgen växlar till rött, barnen måste stå helt stilla. Den som rör på sig får börja om från startlinjen.",
                        "4. Färgerna växlar mellan rött och grönt. Försten till lampan har vunnit."};
                //rules = "Låt alla barn ställa sig mot lampan som lyser gult, detta är startfärgen. Efter 10 sekunder börjar lampan växla mellan rött och grönt. Vid grönt rör sig deltagarna mot lampan. När lampan plötsligt blir röd måste alla barnen stå helt stilla. Den som rör sig kallas tillbaka till startlinjen. Den som först når fram till lyktstolpen vinner. ";
                break;
            case "danger":
                timeStartInterval = 5; //Bestämmer hur länge det ska lysa gult (Springtid mellan lampor)
                timeStopInterval = 6;
                initialDelay = 5; //10;
                //imgurl = "assets/imgs/defaultblue.jpg";
                imgurl = "assets/imgs/danger_text.jpg";
                gameTitle = "Farliga lampan";
                gameHeading = "Farliga lampan – akta rött";
                rules = new String[]{"1. Alla lampor lyser gult. Varje barn ställer sig under valfri lampa.",
                        "2. En lampa växlar till rött, de andra till grönt. Om någon står under röd lampa åker hen ut."
                        ,"3. Alla lampor växlar till gult igen. Barnen springer till varsin ny lampa."
                        ,"4. Färgerna växlar tills ett barn återstår."};
                //rules = "Låt alla barn ställa sig mot lampan som lyser gult, detta är startfärgen. Efter 10 sekunder börjar lampan växla mellan rött och grönt. Vid grönt rör sig deltagarna mot lampan. När lampan plötsligt blir röd måste alla barnen stå helt stilla. Den som rör sig kallas tillbaka till startlinjen. Den som först når fram till lyktstolpen vinner. ";
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
    public String[] getRules() {
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
        System.out.println("Difficulty: " + difficulty);
        switch (name) {
            case "runhere":
                System.out.println("Spring hit startad");
                startGameRunHereOrDanger(1, true);
                break;
            case "danger":
                System.out.println("Farliga lampan startad");
                startGameRunHereOrDanger(0, true);
                break;
            case "redlamp":
                System.out.println("Röda lampan startad");
                //startGameRedLamp(true);
                setLightPost1Red(lightRed);
                lightRed = !lightRed;
        }
    }

    private void setLightPost1Red(boolean lightRed) {
        if (lightRed){
            lightLitDurationTimer = new Timer("lightduration", true);
            gc.lightLightPost1(gc.c("yellow"));
            lightLitDurationTimer.schedule(new TimerTask() {
                public void run() {
                    gc.lightLightPost1(gc.c("red"));
                    lightLitDurationTimer.cancel();
                }
            }, 1050);

        }else {
            gc.lightLightPost1(gc.c("green"));
        }
    }

    private int getRealStartInterval(String gameName, boolean startInterval) {
        int returnInterval;
        int offsetValue;
        if (startInterval) {
            returnInterval = timeStartInterval;
        } else {
            returnInterval = timeStopInterval;
        }
        if (gameName.equals("redlamp")) {
            offsetValue = 2;
        } else {
            offsetValue = 3;
        }
        switch (difficulty) {
            case "easy":
                returnInterval += offsetValue;
                break;
            case "hard":
                returnInterval -= offsetValue;
                break;
        }
        return returnInterval;
    }

    private void startGameRedLamp(boolean firstRun) {
        int nextRandom = ran.nextInt((getRealStartInterval(name, false) - getRealStartInterval(name, true))) + getRealStartInterval(name, true);
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
        int nextRandom = ran.nextInt((getRealStartInterval(name, false) - getRealStartInterval(name, true))) + getRealStartInterval(name, true);

        //Första delayen ska vara hårdkodad. Under delayen ska också alla lampor vara tända gula.
        if (firstRun) {
            delay = initialDelay * 1000;
            System.out.println("GUL syns i " + delay / 1000 + " sek");
            // Gör alla stolpar gula
            gc.lightAllLightPosts(gc.c("yellow"));
            lightYellow = false;

        } else {
            delay = nextRandom * 1000;
        }

        if (lightYellow && name.equals("danger")) {
            //Bestämmer längden för RÖD/GRÖN - fasen i spelet Farliga lampan.
            delay = 4000;
            System.out.println("RÖD / GRÖN syns i " + delay / 1000 + " sek");
        } else {
            if (name.equals("danger")) {
                System.out.println("GUL syns i " + delay / 1000 + " sek");
            } else {
                System.out.println("NY GRÖN OM " + delay / 1000 + " sek");
            }
        }
        lightLitDurationTimer = new Timer("lightduration", true);

        lightLitDurationTimer.schedule(new TimerTask() {
            public void run() {

                if (lightYellow && name.equals("danger")) {
                    gc.lightAllLightPosts(gc.c("yellow"));

                } else {
                    //Tänd en random lampa med vald färg.
                    gc.lightRandomLightPost(color, name);
                }

                //System.out.println("delay = " + delay / 1000 + " sekunder ");
                lightYellow = !lightYellow;
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;

    }

    @JsonProperty
    public String getGameHeading() {
        return gameHeading;
    }
}
