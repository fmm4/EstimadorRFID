package back;

import java.lang.Math;

/**
 * Estimador Lower Bound
 */
public class LowerBound implements Estimator {

    public double estimate (double collision_slots, double empty_slots, double successful_slots, double a, int[] frame){
        return Math.round((collision_slots*2));
    }
    
    public String getName(){
    	return "LOWER BOUND";
    }

}
