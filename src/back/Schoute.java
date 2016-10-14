package back;

/**
 * Estimador Shoute.
 */
public class Schoute implements Estimator {
    public int estimate (double collision_slots, double successful_slots){
        return (int) (collision_slots*2.39);
    }
}
