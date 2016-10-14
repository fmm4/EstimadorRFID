package back;

/**
 * Interface dos Estimadores.
 */

public interface Estimator {
    int estimate(double collision_slots, double successful_slots);

	String getName();
}