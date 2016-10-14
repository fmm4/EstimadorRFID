package back;
import java.util.Arrays;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import back.graphInfo;

import java.util.Random;

public class Simulador {
	public int tags;
	public int frame_size;
	public Estimator estimator;

	public int[] frame;
    public int current_size;

	public Simulador(int tags, int frame_size, Estimator estimator) {
		this.tags = tags;
		this.frame_size = frame_size;
		this.estimator = estimator;
	}

	public graphInfo simulate() {
		graphInfo graph_information = new graphInfo();

		double reader_signals = 0;
		double sum_empty = 0;
		double sum_colli = 0;
		double sum_slots = 0;

		long startTime = System.nanoTime();

		current_size = frame_size;
		sum_slots += current_size;
		while(tags>0 && current_size>0)
		{
			reader_signals++;

			// Inicializa frame com quantidade de tags em cada slot igual a 0.
			frame = frame_init();

			// Cada tag escolhe um slot no frame.
			tag_slot_allocation();

			// Contagem de slots com colisao e sucesso.
			double collision_slots = 0;
			double successful_slots = 0;
			for(int i = 0; i <current_size; i++)
			{
				if(frame[i]>1) {
					sum_colli++;
					collision_slots++;
				} else if(frame[i]==1) {
					tags--;
					successful_slots++;
				} else if(frame[i]==0) {
					sum_empty++;
				}
			}
			sum_slots += current_size;

			// estima o tamo do próximo frame
			current_size = estimator.estimate(collision_slots, successful_slots);
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

	public int[] frame_init() {
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
}
