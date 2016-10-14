package back;

import java.util.Vector;

public class graphInfo {
	public double nr_of_Reads;
	public double avg_slots;
	public double avg_empty;
	public double avg_colli;
	public long time;
	
	
	graphInfo(){}

	@Override
	public String toString() {
		return "{nr_of_Reads: " + nr_of_Reads +
				", avg_slots: " + avg_slots +
				", avg_empty: " + avg_empty +
				", avg_colli: " + avg_colli +
				", time: " + time + "}";
	}

}
