package ca.team4152.frcsim.utils;

import ca.team4152.frcsim.utils.math.Distance;

import java.util.ArrayList;
import java.util.List;

/*
This class defines all the tasks robots can perform.
 */
public class RobotTask {

    private static List<RobotTask> tasks = new ArrayList<>();
    public static final int WAIT = 0;
    public static final int AUTO_MOVE = 1;
    public static final int GET_CUBE = 2;
    public static final int CUBE_EXCHANGE = 3;
    public static final int CUBE_ALLIANCE_SWITCH = 4;
    public static final int CUBE_OPPONENT_SWITCH = 5;
    public static final int CUBE_SCALE = 6;
    public static final int CLIMB = 7;

    static{
        tasks.add(new RobotTask(AUTO_MOVE, 1, 132, 65, 132, 186, 132, 253));
        tasks.add(new RobotTask(GET_CUBE, 2, 629, 19, 629, 300, 122, 161, 529, 161, 205, 95, 205, 124, 205, 148, 205, 175, 205, 201,
                205, 227, 445, 95, 445, 124, 445, 148, 445, 175, 445, 201, 445, 227));
        tasks.add(new RobotTask(CUBE_EXCHANGE, 1, 3, 125));
        tasks.add(new RobotTask(CUBE_ALLIANCE_SWITCH, 2, 140, 110, 168, 84));
        tasks.add(new RobotTask(CUBE_OPPONENT_SWITCH, 2, 480, 237, 512, 210));
        tasks.add(new RobotTask(CUBE_SCALE, 4, 325, 72));
        tasks.add(new RobotTask(CLIMB, 15, 305, 161));
    }

    private int id;
    private int timeToComplete, robotTarget;
    private int[] x, y;

    private RobotTask(int id, int timeToComplete, int... location){
        this.id = id;
        this.timeToComplete = timeToComplete;

        int[] x = new int[location.length / 2];
        int[] y = new int[location.length / 2];
        for(int i = 0; i < location.length / 2; i++){
            x[i] = location[i * 2];
            y[i] = location[(i * 2) + 1];
        }
        this.x = x;
        this.y = y;
    }

    public int getId(){ return id; }
    public void setTimeToComplete(int timeToComplete){ this.timeToComplete = timeToComplete; }
    public int getTimeToComplete(){ return timeToComplete; }
    public int getX(){ return x[robotTarget]; }
    public int getY(){ return y[robotTarget]; }

    public String getName(){
        if(id == AUTO_MOVE)
            return "Auto Movement";
        else if(id == GET_CUBE)
            return "Get Cube";
        else if(id == CUBE_EXCHANGE)
            return "Cube Exchange";
        else if(id == CUBE_ALLIANCE_SWITCH)
            return "Put Cube on Alliance Switch";
        else if(id == CUBE_OPPONENT_SWITCH)
            return "Put Cube on Opponent Switch";
        else if(id == CUBE_SCALE)
            return "Put Cube on Scale";
        else if(id == CLIMB)
            return "Climb";
        else
            return "";
    }

    public static RobotTask newTask(int id){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == id){
                RobotTask blueprint = tasks.get(i);
                return new RobotTask(blueprint.getId(), blueprint.getTimeToComplete(), blueprint.getX(), blueprint.getY());
            }
        }
        return null;
    }

    public static RobotTask waitTask(Robot robot, int duration){
        return new RobotTask(WAIT, duration, (int) robot.getX(), (int) robot.getY());
    }

    public static RobotTask getTask(int id){
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == id){
                return tasks.get(i);
            }
        }
        return null;
    }

    public void calcRobotTarget(Robot robot){
        if(x.length == 1){
            robotTarget = 0;
            return;
        }

        double shortestDistance = 1000000D;
        int shorestDistanceIndex = -1;
        for(int i = 0; i < x.length; i++){
            double distanceToTarget = Distance.calculateDistance(robot.getX(), robot.getY(), x[i], y[i]);

            if(distanceToTarget < shortestDistance){
                if(id == GET_CUBE && !CubeStation.getCubeStation(x[i], y[i]).hasCubes()) continue;

                shortestDistance = distanceToTarget;
                shorestDistanceIndex = i;
            }
        }
        if(shorestDistanceIndex == -1){
            x[0] = -1;
            y[0] = -1;
            shorestDistanceIndex = 0;
        }
        robotTarget = shorestDistanceIndex;
    }
}