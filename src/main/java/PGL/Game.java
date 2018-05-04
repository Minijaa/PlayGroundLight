package PGL;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private String name;
    private String rules;
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

    public Game(String name, String rules, GameController gc) {
        this.name = name;
        this.rules = rules;
        this.gc = gc;

        switch (name) {
            case "runhere":
                timeStartInterval = 7;
                timeStopInterval = 18;
                initialDelay = 5;

                break;
            case "redlamp":
                timeStartInterval = 2;
                timeStopInterval = 7;
                initialDelay = 10;
                break;
            case "danger":
                timeStartInterval = 7;
                timeStopInterval = 18;
                initialDelay = 5;

                break;
            default:
                throw new IllegalArgumentException("Illegal Game Type");
        }
    }

    public String getName() {
        return name;
    }

    public String getRules() {
        return rules;
    }

    public int getTimeStartInterval() {
        return timeStartInterval;
    }

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
                startGameRunHereOrDanger(1, true);
                break;
            case "danger":
                startGameRunHereOrDanger(0, true);
                break;
            case "redlamp":
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
}
