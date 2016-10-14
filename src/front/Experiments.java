package front;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

        Experiments experiments = new Experiments();
        LowerBound lowerBound = new LowerBound();
        Schoute schoute = new Schoute();

        int n_of_tags = 30;
        int increment_step = 10;
        int max_tags = 10000;
        int frame_size = 1000;
        boolean frame_pow2 = false;

        Map<Integer, graphInfo> simulationInformation = new TreeMap<Integer, graphInfo>();
        simulationInformation = experiments.test_tags(
                schoute, n_of_tags, increment_step, max_tags, frame_size, frame_pow2);

//        System.out.print(simulationInformation);

        for(Map.Entry<Integer, graphInfo> entry : simulationInformation.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
