package PGL;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    int counter = 0;
    TimeUnit timeUnit = TimeUnit.SECONDS;

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
                if (counter % 2 == 1) {
                    gc.getLightPostOne().setColor(1);
                    counter--;
                } else {
                    gc.getLightPostOne().setColor(0);
                    counter++;
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

        int nextRandom = ran.nextInt((timeStopInterval - timeStartInterval)) + timeStartInterval;
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

//    public void executor(int color, boolean firstRun)
//            throws InterruptedException {
//        if (color < 0 || color > 1) {
//            throw new IllegalArgumentException();
//        }
//        int nextRandom = ran.nextInt((timeStopInterval - timeStartInterval)) + timeStartInterval;
//        if (firstRun) {
//            delay = 5000;
//            System.out.println("Alla stolpar är GULA!!!");
//
//            gc.lightAllLightPosts(0);
//
//        } else {
//            delay = nextRandom * 1000;
//        }
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        TimerTask tt = new TimerTask() {
//            public void run() {
//                //System.out.println("Task performed on " + new Date());
//                //Tänd en random lampa med vald färg.
//
//                gc.lightRandomLightPost(color);
//                System.out.println("delay = " + delay / 1000 + " sekunder ");
//                executor.shutdownNow();
//                try {
//                    executor(color, false);
//                } catch (InterruptedException a) {
//                    //
//                }
//            }
//        };
//
//        //long delay = 1000L;
//        //long period = 1000L;
//        executor.schedule(tt, delay, TimeUnit.MILLISECONDS);
//
//        //executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
//        //Thread.sleep(delay + period * 5);
//
//    }

}
