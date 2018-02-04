package ca.team4152.frcsim.utils.math;

/*
This is a class that represents a pair of Vectors, for use in calculating paths.
 */
public class VecPair {
    private Vector2d vec0, vec1;

    public VecPair(Vector2d vec0, Vector2d vec1){
        this.vec0 = vec0;
        this.vec1 = vec1;
    }

    public Vector2d getVec0(){ return vec0; }
    public Vector2d getVec1(){ return vec1; }

    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof VecPair)) return false;

        VecPair pair = (VecPair) object;
        return pair.vec0.equals(this.vec0) && pair.vec1.equals(this.vec1);
    }
}