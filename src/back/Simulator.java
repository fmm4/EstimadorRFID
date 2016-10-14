package back;
import java.util.Arrays;
import java.util.Random;

/**
 * Classe do simulador que simula uma única leitura de um determinado número de tags.
 */
public class Simulator {

	private int tags;
	private int frame_size;
	private Estimator estimator;

	private int[] frame;
    private int current_size;
	private double collision_slots;
	private double empty_slots;
	private double successful_slots;

	private double reader_signals = 0;
	private double sum_empty = 0;
	private double sum_colli = 0;
	private double sum_slots = 0;

	public Simulator(int tags, int frame_size, Estimator estimator) {
		this.tags = tags;
		this.frame_size = frame_size;
		this.estimator = estimator;
	}

	// simula uma única leitura de um determinado número de tags.
	public graphInfo simulate() {
		graphInfo graph_information = new graphInfo();

		long startTime = System.nanoTime();

		current_size = frame_size;
		sum_slots += current_size;
		while(tags>0 && current_size>0)
		{
			reader_signals++;

			// Inicializa frame com quantidade de tags em cada slot igual a 0.
			frame_init();

			// Cada tag escolhe um slot no frame.
			tag_slot_allocation();

			// Contagem de slots com colisao, sucesso e vazios.
			slot_counting();

			// estima o tamanho do próximo frame
			current_size = estimator.estimate(collision_slots, successful_slots);

//			instant_result();
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

	// Método que seleciona um slot dentre os slots disponíveis.
	private int get_slot(int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}

	// Inicializa o frame para uma nova leitura.
	private void frame_init() {
        frame = new int[current_size];
        Arrays.fill(frame,0);
    }

	// Escolhe um slot para cada uma das tags existentes.
	private void tag_slot_allocation() {
		for(int i = 0; i < tags; i++){
			int slot = get_slot(current_size);
			frame[slot]++;
		}
	}

	// Escolhe um slot para cada uma das tags existentes.
	private void slot_counting() {
		collision_slots = 0;
		empty_slots = 0;
		successful_slots = 0;
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
				empty_slots++;
			}
		}
		sum_slots += current_size;
	}

	// Método utilizado para ver estado do simulador em um instante (debug).
	private void instant_result() {
		System.out.print("{size: " + current_size);
		System.out.print(", length: " + frame.length);
		System.out.print(", frame: ");
		for (int elem : frame) {
			System.out.print(elem);
		}
		System.out.print(", tags: " + tags);
		System.out.print(", collision_slots: " + collision_slots);
		System.out.print(", empty_slots: " + empty_slots);
		System.out.print(", successful_slots: " + successful_slots);
		System.out.print("}\n");
	}
}
