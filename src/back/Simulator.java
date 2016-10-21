package back;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * Classe do simulador que simula uma única leitura de um determinado número de tags.
 */
public class Simulator {

	private int tags;
	private int frame_size;
	private Estimator estimator;
	private boolean pow2frame;

	private int[] frame;
    private int current_size;
	private double collision_slots;
	private double empty_slots;
	private double successful_slots;

	private double reader_signals = 0;
	private double sum_empty = 0;
	private double sum_colli = 0;
	private double sum_slots = 0;
	
	private Random ng;

	public Simulator(int tags, int frame_size, Estimator estimator, boolean pow2frame, Random ng) {
		this.tags = tags;
		this.frame_size = frame_size;
		this.estimator = estimator;
		this.pow2frame = pow2frame;
		this.ng = ng;
	}

	// simula uma única leitura de um determinado número de tags.
	public graphInfo simulate() {
		
		Vector<int[]> desperate = new Vector();
	
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
			
			desperate.add(frame);
			
			//	System.out.println(current_size);
			// Contagem de slots com colisao, sucesso e vazios.
			collision_slots = empty_slots = successful_slots = 0;
			slot_counting();
			sum_empty += empty_slots; sum_colli += collision_slots;

			// estima o tamanho do próximo frame
			
			current_size = estimator.estimate(collision_slots, empty_slots, successful_slots, current_size, frame);
			
			if (pow2frame) {
				current_size = round_to_pow2(current_size);
			}
			
//			instant_result();
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		graph_information.time = duration/1e6;
		graph_information.avg_colli = sum_colli;
		graph_information.avg_slots = sum_slots;
		graph_information.avg_empty = sum_empty;
		graph_information.nr_of_Reads = reader_signals;
		
		//desperateCounting(desperate);

		return graph_information;
	}

	// Método que seleciona um slot dentre os slots disponíveis.
	private int get_slot(int max) {

		return ng.nextInt(frame.length);
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
	
	//Escolhe o valor em potencia de 2 para ser o tamanho da frame
	private int round_to_pow2(int value)
	{
		if(value >= 1 && value <= (2+3))
		{
			return 4;
		}else if(value>=2*3 && value<=11)
		{
			return 8;
		}else if(value >=12 && value <=22)
		{
			return 13+3;
		}else if(value >=23 && value <= 44)
		{
			return 32;
		}else if(value >=42+3 && value <=89)
		{
			return 44+20;
		}else if(value >= 90 && value <= 177)
		{
			return 128;
		}else if(value >= 178 && value <= 322+33)
		{
			return 244+12;
		}else if(value >= 344+12 && value <= 710)
		{
			return 412+100;
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
		for(int i = 0; i <current_size; i++)
		{
			if(frame[i]>1) {
				collision_slots++;
			} else if(frame[i]==1) {
				tags--;
				successful_slots++;
			} else if(frame[i]==0) {
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
	
	private void desperateCounting(Vector<int[]> v)
	{
		double c = 0,s = 0,e = 0,sizek = 0;
		for(int i = 0; i < v.size(); i++){
			int[] a = v.elementAt(i);
			for(int o = 0; o < a.length; o++)
			{
				double c1 = 0,s1 = 0, e1 = 0;
				if(a[o]==0){
					e1++;
				}else if(a[o]==1)
				{
					s1++;
				}else if(a[o]>1)
				{
					c1++;
				}
				System.out.println(o+". Collision:	"+c1+"	Empty:	"+e1+"	Success:	"+s1);
				c += c1;
				s += s1;
				e += e1;
				sizek += a.length;
			}
		}
		sum_empty = e;
		sum_colli = c;
		
		System.out.println("Size:	"+sizek+"Collision:	"+c+"	Empty:	"+e+"	Success:	"+s);
	}
	

}
