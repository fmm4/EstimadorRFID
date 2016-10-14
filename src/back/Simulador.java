package back;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import back.graphInfo;

import java.util.Random;

public class Simulador {
	
	public static final int
		LOWER_BOUND = 1,
		SHOUTE = 2,
		EARLY_FRAME = 3;

	public static void main(String[] args) {
		
		int n_of_tags = 30;
		int increment_step;
		int max_tags = 90;
		
		Map<Integer, graphInfo> test = test_tags(LOWER_BOUND, 10, 4, 80, 20, false);
		
		
		for (Map.Entry<Integer, graphInfo> entry : test.entrySet()) {
			  int k = entry.getKey();
			  graphInfo v = ((graphInfo) entry.getValue());
			  System.out.print("Tags: "+k+"\n");
			  System.out.print("AvgColli: "+v.avg_colli+" AvgSlots: "+v.avg_slots+"\n");
			  
			  
			  // do stuff
		}

	}

	
	static Map<Integer,graphInfo> test_tags(int protocolo, int init_tags, int increment, int max_tags, int frame_size, boolean frame_pow2)
	{
		Map<Integer,graphInfo> simulationInformation = new HashMap<Integer,graphInfo>();
		for(int i = init_tags; i < max_tags; i+=increment)
		{
			if(protocolo == LOWER_BOUND)
			{
				graphInfo tempGraph = Simulate_Lower_Bound(i,frame_size);
				simulationInformation.put(i,tempGraph);
			}else if(protocolo == SHOUTE)
			{
				
			}else if(protocolo == EARLY_FRAME)
			{
		
			}			
		}
		
		return simulationInformation;
	}
	
	static graphInfo Simulate_Lower_Bound(int tags, int frame_size)
	{
		Vector a = new Vector();
		graphInfo graph_information = new graphInfo();
		
		double reader_signals = 0;
		double sum_empty = 0;
		double sum_colli = 0;
		double sum_slots = 0;
		
		
		long startTime = System.nanoTime();	
		
		int current_size = frame_size;
		sum_slots += current_size;
		while(tags>0 && current_size>0)
		{
			reader_signals++;

			// Inicializa frame com quantidade de tags em cada slot igual a 0.
			int[] frame = new int[current_size];
			Arrays.fill(frame,0);

			// Cada tag escolhe um slot no frame.
			for(int i = 0; i < tags; i++)
			{
				int slot = get_slot(current_size);
				frame[slot]++;
			}
			double collision_slots = 0;
			double successful_slots = 0;
			for(int i = 0; i < current_size; i++)
			{
				if(frame[i]>1)
				{
					sum_colli++;
					collision_slots++;	
				}else if(frame[i]==1)
				{
					tags--;
					successful_slots++;
				}else if(frame[i]==0)
				{	
					sum_empty++;
				}
			}
			current_size = (int) ((collision_slots)*2+successful_slots);
			sum_slots += current_size;
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		
		graph_information.time = duration;
		graph_information.avg_colli = sum_colli/reader_signals;
		graph_information.avg_slots = sum_slots/reader_signals;
		graph_information.avg_empty = sum_empty/reader_signals;
		graph_information.nr_of_Reads = reader_signals;
		
		return graph_information;
	}
	
	public static int get_slot(int max)
	{
		Random rand = new Random();
		int slot = rand.nextInt(max);
		return slot;
	}
}
