package ca.team4152.frcsim.utils;

import ca.team4152.frcsim.Simulator;

/*
This class represents a cube "plate".
A cube plate is anything cubes can be placed on, in this case the scale and two switches.
 */
public class Plate {

    private int owner, lastOwner;
    private int redCubes, blueCubes;
    private boolean ownerChanged;

    public int getOwner(){ return owner; }
    public boolean hasOwnerChanged(){ return ownerChanged; }
    public int getRedCubes(){ return redCubes; }
    public int getBlueCubes(){ return blueCubes; }

    public void update(){
        ownerChanged = lastOwner != owner;
        lastOwner = owner;
    }

    public void addCube(int owner){
        if(owner == Simulator.TEAM_RED) redCubes++;
        else if(owner == Simulator.TEAM_BLUE) blueCubes++;

        if(redCubes > blueCubes) this.owner = Simulator.TEAM_RED;
        else if(blueCubes > redCubes) this.owner = Simulator.TEAM_BLUE;
        else this.owner = Simulator.TEAM_NONE;
    }
}