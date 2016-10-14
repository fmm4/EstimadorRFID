package back;

import java.lang.Math;

/**
 * Estimador Shoute.
 */
public class Schoute implements Estimator {
    public int estimate (double collision_slots, double empty_slots, double successful_slots, int[] frame){
        return (int) Math.ceil((collision_slots*2.39));
    }
    
    public String getName(){
    	return "SCHOUTE";
    }
}
