package ca.team4152.frcsim.utils.math;

/*
This is a utility class used to describe an (x,y) position.
 */
public class Vector2d{

    private double x, y;

    public Vector2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setX(double x){ this.x = x; }
    public double getX(){ return x; }
    public void setY(double y){ this.y = y; }
    public double getY(){ return y; }

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof Vector2d)) return false;

        Vector2d vec = (Vector2d) object;
        return vec.getX() == this.getX() && vec.getY() == this.getY();
    }
}