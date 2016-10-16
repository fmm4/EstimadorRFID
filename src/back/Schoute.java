package back;

import java.lang.Math;

/**
 * Estimador Shoute.
 */
public class Schoute implements Estimator {
    public int estimate (double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame){
        return (int) Math.ceil((collision_slots*2.39)+successful_slots);
    }
    
    public String getName(){
    	return "SCHOUTE";
    }
}
