package back;

import java.util.Vector;

public class graphInfo {
	private int signals;
	private double time;
	private Vector<timesnap> timesnaps;
	
	
	graphInfo(){}
	
	void setTime(double a)
	{
		time = a;
	}
	
	double getTime()
	{
		return time;
	}
	
	void setSignals(int a)
	{
		signals = a;
	}
	
	int getSignals()
	{
		return signals;
	}
	
	void new_timesnap(int slots,int collisions, int empty)
	{
		timesnap tempSnap = new timesnap(slots,collisions,empty);
		
		timesnaps.add(tempSnap);
	}
	
	Vector get_timeline()
	{
		return timesnaps;
	}
	
	 class timesnap{
		 public int total_slots;		
		 public int total_collisions;
		 public int total_empty;
		 timesnap(int slots, int collisions, int empty)
		 {
			 total_slots = slots;
			 total_collisions = collisions;
			 total_empty = empty;
		 }
	 }
}
