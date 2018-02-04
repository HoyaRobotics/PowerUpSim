package ca.team4152.frcsim.utils;

import java.util.ArrayList;
import java.util.List;

/*
This is a class that describes a station that holds a certain number of cubes.
Robots can visit these stations and pick up cubes.
CubeStations can be portals or cubes placed on the floor.
 */
public class CubeStation{

    private static List<CubeStation> stations = new ArrayList<>();

    static{
        stations.add(new CubeStation(7, 629, 19)); //top portal
        stations.add(new CubeStation(7, 629, 300)); //bottom portal
        stations.add(new CubeStation(10, 122, 161)); //red cube pyramid
        stations.add(new CubeStation(10, 529, 161)); //blue cube pyramid
        stations.add(new CubeStation(1, 205, 95)); //red switch 1
        stations.add(new CubeStation(1, 205, 124)); //red switch 2
        stations.add(new CubeStation(1, 205, 148)); //red switch 3
        stations.add(new CubeStation(1, 205, 175)); //red switch 4
        stations.add(new CubeStation(1, 205, 201)); //red switch 5
        stations.add(new CubeStation(1, 205, 227)); //red switch 6
        stations.add(new CubeStation(1, 445, 95)); //blue switch 1
        stations.add(new CubeStation(1, 445, 124)); //blue switch 2
        stations.add(new CubeStation(1, 445, 148)); //blue switch 3
        stations.add(new CubeStation(1, 445, 175)); //blue switch 4
        stations.add(new CubeStation(1, 445, 201)); //blue switch 5
        stations.add(new CubeStation(1, 445, 227)); //blue switch 6
    }

    private int cubes;
    private int x, y;

    public CubeStation(int cubes, int x, int y){
        this.cubes = cubes;
        this.x = x;
        this.y = y;
        stations.add(this);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public void takeCube(){
        if(hasCubes()) cubes--;
    }

    public boolean hasCubes(){
        return cubes > 0;
    }

    public static CubeStation getCubeStation(int x, int y){
        for(int i = 0; i < stations.size(); i++){
            CubeStation check = stations.get(i);
            if(check.getX() == x && check.getY() == y) return check;
        }
        return null;
    }
}