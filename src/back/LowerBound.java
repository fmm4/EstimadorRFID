package back;

import java.lang.Math;

/**
 * Estimador Lower Bound
 */
public class LowerBound implements Estimator {

    public int estimate (double collision_slots, double successful_slots){
        return (int) Math.ceil((collision_slots*2+successful_slots));
    }
    
    public String getName(){
    	return "LOWER BOUND";
    }
}
