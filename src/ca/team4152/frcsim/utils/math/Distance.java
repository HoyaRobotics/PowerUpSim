package ca.team4152.frcsim.utils.math;

/*
This is a utility class used to calculate the distance between two points.
 */
public class Distance {

    public static double calculateDistance(Vector2d pos0, Vector2d pos1){
        return calculateDistance(pos0.getX(), pos0.getY(), pos1.getX(), pos1.getY());
    }

    public static double calculateDistance(double x0, double y0, double x1, double y1){
        double yc = y0 - y1;
        double xc = x0 - x1;

        return Math.sqrt((yc * yc) + (xc * xc));
    }
}