package front;

import back.Estimator;
import back.GraphPrinter;
import back.eomlee;
import back.graphInfo;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import back.*;

/**
 * Classe utilizada para rodar os experimentos
 */
public class Experiments {
	//PARAMETROS DE EXECUCAO//
	//Protocolos//
	public static boolean schouteEnabled = true;
	public static boolean lowerboundEnabled = true;
	public static boolean ILCMEnabled = false;
	
	public static Random ng;

	
	//Teste gerais//
	public static int initial_number_of_tags = 100;
	public static int incremental_tags = 100;
	public static int maximum_tags = 1000;
	public static int retries_per_n_of_tags = 2000;
	public static int initial_frame_size = 30+34;
	public static boolean use_pow2 = false;
	
	//Testes para ILCM//
//	public static int initial_number_of_tags = 0;
//	public static int incremental_tags = 20+30;
//	public static int maximum_tags = 300;
//	public static int retries_per_n_of_tags = 2000;
//	public static int initial_frame_size = 100;
//	public static boolean use_pow2 = true;
	
	
    public int HASH = 1;
    public int TREE = 2;

    public int type = 2;

    public Map<Integer, graphInfo> test_tags(Estimator estimator, int init_tags, int increment, int max_tags, int frame_size, boolean frame_pow2, Random ng)
    {
        Map<Integer,graphInfo> simulationInformation;

        if (type == HASH){
            simulationInformation = new HashMap<Integer,graphInfo>();
        } else {
            simulationInformation = new TreeMap<Integer,graphInfo>();
        }

        for(int tags = init_tags; tags <= max_tags; tags+=increment)
        {
            Simulator simulator = new Simulator(tags, frame_size, estimator, frame_pow2, ng);
            graphInfo tempGraph = simulator.simulate();
            simulationInformation.put(tags, tempGraph);
        }

        return simulationInformation;
    }

    public static void main(String[] args) {
 
    	simulate(
    			initial_number_of_tags,
    			incremental_tags,
    			maximum_tags,
    			retries_per_n_of_tags,
    			initial_frame_size,
    			use_pow2);
//        System.out.print(simulationInformation);


    }
    
    public static void simulate(int n_of_tags, int increment_step, int max_tags, int retry, int frame_size, boolean frame_pow2)
    {
        Experiments experiments = new Experiments();
        LowerBound lowerBound = new LowerBound();
        Schoute schoute = new Schoute();
        ILCMSbS artig = new ILCMSbS();
        eomlee eomlee = new eomlee();
        
        GraphPrinter printer = new GraphPrinter();

        Vector SchouteVec = new Vector();
        Vector LBVec = new Vector();
        Vector artiVec = new Vector();
        Vector elVec = new Vector();
        for(int i = 0; i<=retry; i++)
        {
	        Map<Integer, graphInfo> simulationSchoute = new TreeMap<Integer, graphInfo>();
	        simulationSchoute = experiments.test_tags(
	                schoute, n_of_tags, increment_step, max_tags, frame_size, frame_pow2, ng);
	        SchouteVec.add(simulationSchoute);
	        
	        Map<Integer, graphInfo> simulationLB = new TreeMap<Integer, graphInfo>();
	        simulationLB = experiments.test_tags(
	                lowerBound, n_of_tags, increment_step, max_tags, frame_size, frame_pow2, ng);
	        LBVec.add(simulationLB);

	        if(ILCMEnabled){
	        Map<Integer, graphInfo> simulationArti = new TreeMap<Integer, graphInfo>();
	        simulationArti = experiments.test_tags(
	                artig, n_of_tags, increment_step, max_tags, frame_size, frame_pow2, ng);
	        artiVec.add(simulationArti);
	        }
	        
	        Map<Integer, graphInfo> simulationEomlee = new TreeMap<Integer, graphInfo>();
	        simulationEomlee = experiments.test_tags(
	        		eomlee, n_of_tags, increment_step, max_tags, frame_size, frame_pow2, ng);
	        elVec.add(simulationEomlee);
	        
	        System.out.println(i+" Run.");
        }
        
        Map<Integer, graphInfo> simulationSchoute = averageValues(SchouteVec);
        Map<Integer, graphInfo> simulationLB = averageValues(LBVec);
        Map<Integer, graphInfo> simulationArti = averageValues(artiVec);
        Map<Integer, graphInfo> simulationEomlee = averageValues(elVec);
        
        Map<Estimator, Map<Integer, graphInfo>> graphMap = new HashMap();
        
        if(schouteEnabled) graphMap.put(schoute, simulationSchoute);
        if(lowerboundEnabled) graphMap.put(lowerBound, simulationLB);
        if(ILCMEnabled) graphMap.put(artig, simulationArti);
        graphMap.put(eomlee, simulationEomlee);

        printer.printChart(graphMap);
    }
    
    static Map<Integer,graphInfo> averageValues(Vector<Map<Integer, graphInfo> > retried_graph)
    {
    	if(retried_graph.isEmpty()){return null;}
    	Map<Integer,graphInfo> averaged = new TreeMap();
    	
    	long time;
    	
		for (Entry<Integer, graphInfo> entry : (retried_graph.elementAt(0)).entrySet())
		{
			averaged.put(entry.getKey(),entry.getValue());
		}
    	
		for(int i = 1; i < retried_graph.size(); i++)
    	{
			for (Entry<Integer, graphInfo> entry : (retried_graph.elementAt(i)).entrySet())
			{
				averaged.put(entry.getKey(), averaged.get(entry.getKey()).add(entry.getValue()));
			}
    	}
		
		int division = retried_graph.size();
		
		for (Entry<Integer, graphInfo> entry : averaged.entrySet())
		{
				averaged.put(entry.getKey(), averaged.get(entry.getKey()).divide(division));
		}
    	
		return averaged;    	
    }
}
