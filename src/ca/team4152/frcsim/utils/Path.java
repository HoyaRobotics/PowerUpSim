package ca.team4152.frcsim.utils;

import ca.team4152.frcsim.utils.math.Distance;
import ca.team4152.frcsim.utils.math.VecPair;
import ca.team4152.frcsim.utils.math.Vector2d;

import java.io.*;
import java.util.*;

/*
This is a class that holds all the possible paths and respective path lengths robots can take.
It grabs this list from the pathlengths.txt file, which is in the res folder.
This class exists so that all path lengths are calculated before the simulator starts, instead of calculating the lengths on the fly.
 */
public class Path {

    private static HashMap<VecPair, Double> pathLengths = new HashMap<>();

    public static double getPathLength(Vector2d start, Vector2d finish){
        VecPair path = new VecPair(start, finish);
        VecPair revPath = new VecPair(finish, start);
        if(pathLengths.containsKey(path))
            return pathLengths.get(path);
        else if(pathLengths.containsKey(revPath))
            return pathLengths.get(revPath);
        else
            return Distance.calculateDistance(start, finish);
    }

    public static void initPaths(){
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("res/pathlengths.txt")))){
            String line = "";
            StringTokenizer tokenizer;

            while((line = reader.readLine()) != null){
                double x0 = -2, y0 = -2, x1 = -2, y1 = -2, length = -2;
                tokenizer = new StringTokenizer(line, ",");
                while(tokenizer.hasMoreTokens()){
                    String token = tokenizer.nextToken();
                    if(x0 == -2)
                        x0 = Double.parseDouble(token);
                    else if(y0 == -2)
                        y0 = Double.parseDouble(token);
                    else if(x1 == -2)
                        x1 = Double.parseDouble(token);
                    else if(y1 == -2)
                        y1 = Double.parseDouble(token);
                    else if(length == -2)
                        length = Double.parseDouble(token);
                }
                pathLengths.put(new VecPair(new Vector2d(x0, y0), new Vector2d(x1, y1)), length);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}