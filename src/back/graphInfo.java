package back;

import java.util.Vector;

public class graphInfo {
	public double nr_of_Reads;
	public double avg_slots;
	public double avg_empty;
	public double avg_colli;
	public double time;
	
	
	graphInfo(){}

	@Override
	public String toString() {
		return "{nr_of_Reads: " + nr_of_Reads +
				", avg_slots: " + avg_slots +
				", avg_empty: " + avg_empty +
				", avg_colli: " + avg_colli +
				", time: " + time + "}";
	}
	
	public graphInfo add(graphInfo a)
	{
		graphInfo newGraphInfo = new graphInfo();
		newGraphInfo.nr_of_Reads = nr_of_Reads + a.nr_of_Reads;
		newGraphInfo.avg_slots = avg_slots + a.avg_slots;
		newGraphInfo.avg_empty = avg_empty + a.avg_empty;
		newGraphInfo.avg_colli = avg_colli + a.avg_colli;
		newGraphInfo.time = time + a.time;
		
		return newGraphInfo;
	}
	
	public graphInfo divide(double a){
		graphInfo newGraphInfo = new graphInfo();
		newGraphInfo.nr_of_Reads =nr_of_Reads/a;
		newGraphInfo.avg_slots =avg_slots/ a;
		newGraphInfo.avg_empty =avg_empty/ a;
		newGraphInfo.avg_colli = avg_colli/ a;
		newGraphInfo.time =time/ a;
		return newGraphInfo;
	}

}
