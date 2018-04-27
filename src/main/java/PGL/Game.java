package PGL;

public class Game {
    private String name;
    private String rules;
    //private String lightConfig; //this is just a reminder that it should have some sort of config for how the lights are used

    public Game(String name){
        this.name = name;
    }
    public Game(String name, String rules){
        this.name = name;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public String getRules(){
        return rules;
    }

    public void setRules(String rules){
        this.rules = rules;
    }

    public void setName(String name) {
        this.name = name;
    }
}
