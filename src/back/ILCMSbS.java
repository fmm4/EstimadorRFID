package back;

/**
 * Estimador ILCMSbS
 */
public class ILCMSbS implements Estimator {

	public double e = 0;
	public double s = 0;
	public double c = 0;

	double C;
	
	double i;
	double Qn;
	double Qc;
	double Qt;

	double L1;
	double L2;
	double ps1;
	double ps2;
	
	//RETRIER//
	int retrier = 0;

    public int estimate (double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame){
    	i = 0;
    	C = collision_slots;
    	Qn = -1;
    	Qc = current_size;

    	while (Qn == -1 && i <= Math.pow(2, Qc)){
//    		readSlot(frame);
    		c = collision_slots;
    		e = empty_slots;
    		s = successful_slots;
    		i++;

    		if (i >= 1 && R(i) - R(i - 1) <= 1){
    			L1 = Math.pow(2, Qc);
    			Qt = find_optimal_Q(R(i));
    			L2 = Math.pow(2, Qt);
    			ps1 = Math.pow((R(i)/L1) * (1 - (1/L1)), R(i) - 1);
    			ps2 = Math.pow((R(i)/L2) * (1 - (1/L2)), R(i) - 1);
    			
    			if ((L1 * ps1 - s) < L2 * ps2){
    				Qn = Qt;
    			}
    		}
    	}
    	
    	if (Qn != -1){
    		 return (int) Math.round(Qn);
    	} else {
    		Qn = Qc;
    		return (int) Math.round(Qn);
    	}
    }

    public double N (double i){
    	return k_line(i) * s + l_line(i);
    }
    
    public double R (double i){
    	double R;
    	R = (N(i) * Math.pow(2, Qc)) / i;
		if (C == 0){
			R = (s * Math.pow(2, Qc)) / i;
		}
		
		return R;
    }
    
    public double k_line (double i){
    	double k_line_i = c/((4.344 * i - 16.28) + (i/(- 2.282 - 0.273 * i) * c)) + 0.2407 * Math.log(i + 42.56);

		if (k_line_i < 0){
			k_line_i = 0;
		}
    	return k_line_i;
    }
    
    public double l_line (double i){
    	return (1.2592 + 1.513 * i) * Math.tan(1.234 * Math.pow(i, -0.9907) * c);
    }
    
    public void readSlot(int[] frame){
//    	System.out.print(i + " - ");
//    	System.out.print((int) Math.ceil(i) + " - ");
//    	System.out.println(frame.length);
//    	if(frame[(int) Math.round(i)] > 1) {
//			c++;
//		} else if(frame[(int) Math.round(i)] == 1) {
//			s++;
//		} else if(frame[(int) Math.round(i)] == 0) {
//			e++;
//		}
    	
    }
    
    public double log_2 (double a){
    	return (Math.log10(a) / Math.log10(2));
    }
    
    public double find_optimal_Q (double R){
    	return log_2 ( Math.round(2.39 * Math.pow(2, Qc)) );
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ILCMSbS";
	}


    
}
