package back;
import java.util.Arrays;
import java.util.Vector;

import back.graphInfo;

import java.util.Random;

interface Estimator {
	int estimate(int collision_slots, int successful_slots);
}

public class Simulador {
	public int tags;
	public int frame_size;

	public int[] frame;
    public int current_size;

	public Simulador(int tags, int frame_size) {
		this.tags = tags;
		this.frame_size = frame_size;
	}

	public simulate(Estimator estimator) {
		int reader_signals = 0;
		int sum_empty = 0;
		int sum_colli = 0;
		int sum_slots = 0;

		int current_size = frame_size;
		while(tags>0)
		{
			reader_signals++;

			// Inicializa frame com quantidade de tags em cada slot igual a 0.
			frame = frame_init(current_size);

			// Cada tag escolhe um slot no frame.
			tag_slot_allocation();

			// Contagem de slots com colisao e sucesso.
			int collision_slots = 0;
			int successful_slots = 0;
			for(int i = 0; i < current_size; i++)
			{
				if(frame[i]>1)
				{
					sum_colli++;
					collision_slots = 0;
				}else if(frame[i]==1)
				{
					tags--;
					successful_slots = 0;
				}else if(frame[i]==0)
				{
					sum_empty++;
				}
			}
			sum_slots += current_size;

			// estima o tamo do próximo frame
			current_size = collision_slots*2+successful_slots;
//			current_size = estimator.estimate(collision_slots, successful_slots);
		}

	}

	public int[] frame_init(int current_size) {
        int[] frame = new int[current_size];
        Arrays.fill(frame,0);
        return frame;
    }

    public void tag_slot_allocation() {
        for(int i = 0; i < tags; i++){
			int slot = get_slot(current_size);
            frame[slot]++;
        }
    }
	
	public int get_slot(int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}

	public counting () {
		int collision_slots = 0;
		int successful_slots = 0;
		for(int i = 0; i < current_size; i++)
		{
			if(frame[i]>1)
			{
				sum_colli++;
				collision_slots = 0;
			}else if(frame[i]==1)
			{
				tags--;
				successful_slots = 0;
			}else if(frame[i]==0)
			{
				sum_empty++;
			}
		}
		sum_slots += current_size;

	}



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

		int[] frame;
		int reader_signals = 0;
		int sum_empty = 0;
		int sum_colli = 0;
		int sum_slots = 0;


		long startTime = System.nanoTime();

		int current_size = frame_size;
		while(tags>0)
		{
			reader_signals++;

			// Inicializa frame com quantidade de tags em cada slot igual a 0.
			frame = frame_init(current_size);

			// Cada tag escolhe um slot no frame.
			for(int i = 0; i < tags; i++)
			{
				int slot = get_slot(current_size);
				frame[slot]++;
			}

			// Contagem de slots com colisao e sucesso.
			int collision_slots = 0;
			int successful_slots = 0;
			for(int i = 0; i < current_size; i++)
			{
				if(frame[i]>1)
				{
					sum_colli++;
					collision_slots = 0;
				}else if(frame[i]==1)
				{
					tags--;
					successful_slots = 0;
				}else if(frame[i]==0)
				{
					sum_empty++;
				}
			}
			sum_slots += current_size;

			// estimador
			current_size = collision_slots*2+successful_slots;
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
}
