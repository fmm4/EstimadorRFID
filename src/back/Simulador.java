package back;
import java.util.Arrays;
import java.util.Vector;

import back.graphInfo;

import java.util.Random;

public class Simulador {

	public static void main(String[] args) {
		
		int n_of_tags = 30;
		int increment_step;
		int max_tags = 90;
		
		

	}

	
	void test_tags(int protocolo, int init_tags, int increment, int max_tags, int frame_size, boolean frame_pow2)
	{
		
	}
	
	graphInfo Simulate_Lower_Bound(int tags, int frame_size)
	{
		Vector a = new Vector();
		graphInfo graph_information = new graphInfo();
		
		int current_size = frame_size;
		while(tags>0)
		{
			int[] frame = new int[current_size];
			Arrays.fill(frame,0);
			for(int i = 0; i < tags; i++)
			{
				int slot = get_slot(current_size);
				frame[slot]++;
			}
			
			int collisions_slots = 0;
			int successful_slots = 0;
			int empty_slots = 0;
			for(int i = 0; i < current_size; i++)
			{
				if(frame[i]>1)
				{
					collisions_slots++;					
				}else if(frame[i]==1)
				{
					successful_slots++;
				}else if(frame[i]==0)
				{
					empty_slots++;
				}
			}
			graph_information.new_timesnap(current_size, collisions_slots, empty_slots);
			current_size = collisions_slots*2+successful_slots;
		}
		
	}
	
	public int get_slot(int max)
	{
		Random rand = new Random();
		int slot = rand.nextInt(max);
		return slot;
	}
}
