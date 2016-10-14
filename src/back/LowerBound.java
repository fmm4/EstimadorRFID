package back;

/**
 * Estimador Lower Bound
 */
public class LowerBound implements Estimator {

    public int estimate (double collision_slots, double successful_slots){
        return (int) (collision_slots*2+successful_slots);
    }
}
