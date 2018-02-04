package ca.team4152.frcsim.utils;

import ca.team4152.frcsim.Simulator;
import ca.team4152.frcsim.utils.math.Vector2d;

import java.util.ArrayList;
import java.util.List;

/*
This class represents a robot in the simulation.
 */
public class Robot {

    private List<RobotTask> taskQueue;
    private RobotTask currentTask = null;
    private String robotName = "";
    private int holdingCubes = 1;
    private Vector2d position;
    private double speed, moveAddition;
    private Simulator simulator;

    private int cubesExchanged, cubesPutOnAllianceSwitch, cubesPutOnOpponentSwitch, cubesPutOnScale;
    private boolean climbed, autoMobility;

    private int currentTaskIndex = 0;
    private int currentTaskTime = -1;
    private double currentMoveTime = -1, currentMoveLength;

    public Robot(Simulator simulator, int robotNum, int x, int y){
        this.position = new Vector2d(x, y);
        this.simulator = simulator;
        this.robotName = "Robot #" + robotNum;
        taskQueue = new ArrayList<>();
    }

    public String getName(){ return robotName; }
    public double getX(){ return position.getX(); }
    public double getY(){ return position.getY(); }
    public void setSpeed(double speed){ this.speed = speed; }
    public double getSpeed(){ return speed; }
    public void giveCube(){ holdingCubes++; }
    public void takeCube(){ if(holdingCubes > 0) holdingCubes--; }
    public boolean hasCubes(){ return holdingCubes > 0; }
    public void addCubeExchanged(){ cubesExchanged++; }
    public int getCubesExchanged(){ return cubesExchanged; }
    public void addCubePutOnAllianceSwitch(){ cubesPutOnAllianceSwitch++; }
    public int getCubesPutOnAllianceSwitch(){ return cubesPutOnAllianceSwitch; }
    public void addCubePutOnOpponentSwitch(){ cubesPutOnOpponentSwitch++; }
    public int getCubesPutOnOpponentSwitch(){ return cubesPutOnOpponentSwitch; }
    public void addCubePutOnScale(){ cubesPutOnScale++; }
    public int getCubesPutOnScale(){ return cubesPutOnScale; }
    public void finishAutoMobility(){ autoMobility = true; }
    public boolean completedAutoMobility(){ return autoMobility; }
    public void finishClimb(){ climbed = true; }
    public boolean completedClimb(){ return climbed; }

    public void update(){
        if(currentTask == null){
            try {
                currentTask = taskQueue.get(currentTaskIndex);
                currentTask.calcRobotTarget(this);
            } catch (IndexOutOfBoundsException e) { return; }
        }

        if(!completedAutoMobility() && getX() >= 124)
            simulator.robotFinishedTask(this, RobotTask.AUTO_MOVE);

        if(currentTask.getX() != position.getX() || currentTask.getY() != position.getY()){
            if(currentTask.getX() == -1 && currentTask.getY() == -1){
                currentTaskIndex++;
                return;
            }

            if(currentMoveTime < 0){
                Vector2d start = new Vector2d(position.getX(), position.getY());
                Vector2d finish = new Vector2d(currentTask.getX() ,currentTask.getY());

                currentMoveLength = Path.getPathLength(start, finish);
                moveAddition = currentMoveLength / speed;
                currentMoveTime = 0;
            }

            if(currentMoveTime < currentMoveLength){
                currentMoveTime += moveAddition;
            }else{
                position.setX(currentTask.getX());
                position.setY(currentTask.getY());
                currentMoveTime = -1;
                currentMoveLength = 0;
                moveAddition = 0;
                update();
            }
        }else{
            if(currentTaskTime < currentTask.getTimeToComplete()){
                currentTaskTime++;
            }else{
                simulator.robotFinishedTask(this, currentTask.getId());
                currentTaskIndex++;
                currentTaskTime = 0;
                currentTask = null;
            }
        }
    }

    public void addTask(RobotTask task){
        taskQueue.add(task);
    }
    public List<RobotTask> getTasks(){ return taskQueue; }
}