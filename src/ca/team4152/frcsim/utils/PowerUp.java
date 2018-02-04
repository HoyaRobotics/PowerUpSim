package ca.team4152.frcsim.utils;

import java.util.ArrayList;
import java.util.List;

/*
This class defines and powerups that can be activated during play.
 */
public class PowerUp {

    private static List<PowerUp> powerUps = new ArrayList<>();
    public static final int LEVITATE = 0;
    public static final int FORCE = 1;
    public static final int BOOST = 2;

    static{
        powerUps.add(new PowerUp(LEVITATE, -1, -1, 0));
        powerUps.add(new PowerUp(FORCE, -1, 10, 0));
        powerUps.add(new PowerUp(BOOST, -1, 10, 0));
    }

    private int id;
    private int startTime, duration, level;

    private PowerUp(int id, int startTime, int duration, int level){
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.level = level;
    }

    public int getId(){ return id; }
    public int getDuration(){ return duration; }
    public void setLevel(int level){ this.level = level; }
    public int getLevel(){ return level; }
    public void setStartTime(int startTime){ this.startTime = startTime; }
    public int getStartTime(){ return startTime; }

    public static PowerUp newPowerUp(int id, int startTime, int level){
        for(int i = 0; i < powerUps.size(); i++){
            if(powerUps.get(i).getId() == id){
                PowerUp blueprint = powerUps.get(i);
                return new PowerUp(blueprint.getId(), startTime, blueprint.getDuration(), level);
            }
        }
        return null;
    }
}