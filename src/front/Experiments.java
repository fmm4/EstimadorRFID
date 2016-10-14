package front;

import back.Estimator;
import back.GraphPrinter;
import back.graphInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import back.*;

/**
 * Classe utilizada para rodar os experimentos
 */
public class Experiments {
    public int HASH = 1;
    public int TREE = 2;

    public int type = 2;

    public Map<Integer, graphInfo> test_tags(Estimator estimator, int init_tags, int increment, int max_tags, int frame_size, boolean frame_pow2)
    {
        Map<Integer,graphInfo> simulationInformation;

        if (type == HASH){
            simulationInformation = new HashMap<Integer,graphInfo>();
        } else {
            simulationInformation = new TreeMap<Integer,graphInfo>();
        }

        for(int tags = init_tags; tags < max_tags; tags+=increment)
        {
            Simulator simulator = new Simulator(tags, frame_size, estimator);
            graphInfo tempGraph = simulator.simulate();
            simulationInformation.put(tags, tempGraph);
        }

        return simulationInformation;
    }

    public static void main(String[] args) {
 
    	simulate(100, 100, 1000, 2000,230+20,false);
//        System.out.print(simulationInformation);


    }
    
    public static void simulate(int n_of_tags, int increment_step, int max_tags, int retry, int frame_size, boolean frame_pow2)
    {
        Experiments experiments = new Experiments();
        LowerBound lowerBound = new LowerBound();
        Schoute schoute = new Schoute();
        GraphPrinter printer = new GraphPrinter();

        Vector SchouteVec = new Vector();
        Vector LBVec = new Vector();
        for(int i = 0; i<retry; i++)
        {
	        Map<Integer, graphInfo> simulationSchoute = new TreeMap<Integer, graphInfo>();
	        simulationSchoute = experiments.test_tags(
	                schoute, n_of_tags, increment_step, max_tags, frame_size, frame_pow2);
	        SchouteVec.add(simulationSchoute);
	        
	        Map<Integer, graphInfo> simulationLB = new TreeMap<Integer, graphInfo>();
	        simulationLB = experiments.test_tags(
	                lowerBound, n_of_tags, increment_step, max_tags, frame_size, frame_pow2);
	        
	        LBVec.add(simulationLB);
        }
        
        Map<Integer, graphInfo> simulationSchoute = averageValues(SchouteVec);
        Map<Integer, graphInfo> simulationLB = averageValues(LBVec);
        
        Map<Estimator, Map<Integer, graphInfo>> graphMap = new HashMap();
        
        graphMap.put(schoute, simulationSchoute);
        graphMap.put(lowerBound, simulationLB);
        
        
        printer.printChart(graphMap);
    }
    
    static Map<Integer,graphInfo> averageValues(Vector<Map<Integer, graphInfo> > retried_graph)
    {
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