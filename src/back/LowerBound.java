package back;

/**
 * Estimador Lower Bound
 */
public class LowerBound implements Estimator {

    public int estimate (int collision_slots, int successful_slots){
        return collision_slots*2+successful_slots;
    }
}
