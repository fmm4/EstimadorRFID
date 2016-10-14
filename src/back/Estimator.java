package back;

/**
 * Interface dos Estimadores.
 */

public interface Estimator {
    int estimate(double collision_slots, double empty_slots, double successful_slots, int[] frame);
}