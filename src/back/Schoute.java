package back;

import java.lang.Math;

/**
 * Estimador Shoute.
 */
public class Schoute implements Estimator {
    public double estimate (double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame){
        return Math.round((collision_slots*2.39));
    }
    
    public String getName(){
    	return "SCHOUTE";
    }
}
