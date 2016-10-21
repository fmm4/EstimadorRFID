package back;

/**
 * Interface dos Estimadores.
 */

public interface Estimator {
	double estimate(double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame);

	String getName();

}