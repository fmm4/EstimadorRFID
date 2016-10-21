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
	private boolean pow2frame;

	private int[] frame;
    private double current_size;
	private double collision_slots;
	private double empty_slots;
	private double successful_slots;

	private double reader_signals = 0;
	private double sum_empty;
	private double sum_colli;
    private double sum_slots;
    private double sum_suces;

	public Simulator(int tags, int frame_size, Estimator estimator, boolean pow2frame) {
		this.tags = tags;
		this.frame_size = frame_size;
		this.estimator = estimator;
		this.pow2frame = pow2frame;
	}

	// simula uma única leitura de um determinado número de tags.
	public graphInfo simulate() {

        System.out.println("---------------------- inicio --------------------");

        reader_signals = 0;
		sum_colli = 0;
		sum_empty = 0;
		sum_slots = 0;
        sum_suces = 0;
		
		graphInfo graph_information = new graphInfo();

		long startTime = System.nanoTime();

		current_size = frame_size;
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
			current_size = estimator.estimate(collision_slots, empty_slots, successful_slots, current_size, frame);
			
			if (pow2frame) {
				current_size = round_to_pow2(current_size);
			}

            instant_result();
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		graph_information.time = duration/1e6;
		graph_information.avg_colli = sum_colli;
		graph_information.avg_slots = sum_slots;
		graph_information.avg_empty = sum_empty;
		graph_information.nr_of_Reads = reader_signals;

        System.out.println("------------resultado final:");
        System.out.print(" sum_slots: " + sum_slots);
        System.out.print(", sum_colli: " + sum_colli);
        System.out.print(", sum_empty: " + sum_empty);
        System.out.print(", sum_suces: " + sum_suces);
        System.out.print("}\n");

        System.out.println("---------------------- fim --------------------");
		return graph_information;
	}

	// Método que seleciona um slot dentre os slots disponíveis.
	private int get_slot(double max) {
		Random rand = new Random();
		return rand.nextInt((int) max);
	}

	// Inicializa o frame para uma nova leitura.
	private void frame_init() {
        frame = new int[(int) current_size];
        Arrays.fill(frame,0);
    }

	// Escolhe um slot para cada uma das tags existentes.
	private void tag_slot_allocation() {
		for(int i = 0; i < tags; i++){
			int slot = get_slot(current_size);
			frame[slot]++;
		}
	}
	
	//Escolhe o valor em potencia de 2 para ser o tamanho da frame
	private int round_to_pow2(double value)
	{
		if(value >= 1 && value <= 5)
		{
			return 4;
		}else if(value>=6 && value<=11)
		{
			return 8;
		}else if(value >=12 && value <=22)
		{
			return 16;
		}else if(value >=23 && value <= 44)
		{
			return 32;
		}else if(value >=45 && value <=89)
		{
			return 64;
		}else if(value >= 90 && value <= 177)
		{
			return 128;
		}else if(value >= 178 && value <= 355)
		{
			return 256;
		}else if(value >= 356 && value <= 710)
		{
			return 512;
		}else if(value >= 711 && value <= 1420){
			return 1024;
		}else if(value >=1421){
			return 2048;
		}else{
			return 0;
		}
	}

	// Escolhe um slot para cada uma das tags existentes.
	private void slot_counting() {
		collision_slots = 0;
		empty_slots = 0;
		successful_slots = 0;
		for(int i = 0; i < current_size; i++)
		{
			if(frame[i]>1) {
				sum_colli++;
				collision_slots++;
			} else if(frame[i]==1) {
                sum_suces++;
				reader_signals++;
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
//		System.out.print(", empty_slots: " + empty_slots);
//        System.out.print(", successful_slots: " + successful_slots);
//        System.out.print(", sum_slots: " + sum_slots);
        System.out.print(", sum_colli: " + sum_colli);
//        System.out.print(", sum_empty: " + sum_empty);
		System.out.print("}\n");
	}
}
