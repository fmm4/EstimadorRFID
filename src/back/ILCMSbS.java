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

    public int estimate (double collision_slots, double empty_slots, double successful_slots, int[] frame){
    	i = 0;
    	C = collision_slots;
    	Qn = -1;
    	Qc = frame.length;
    	
    	while (Qn == -1 && i <= Math.pow(2, Qc)){
//    		readSlot(frame);
    		
    		i++;
    		if (i > 1 && R(i) - R(i - 1) <= 1){
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
    		 return (int) Qn;
    	} else {
    		Qn = Qc;
    		return (int) Qn;
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
    	double k_line_i = c/((4.344 * i - 16.28) + (i/(- 2282 - 0.273 * i) * c)) + 0.2407 * Math.log(i + 42.56);

		if (k_line_i < 0){
			k_line_i = 0;
		}
    	return k_line_i;
    }
    
    public double l_line (double i){
    	return (1.2592 + 1.513 * i) * Math.tan(1.234 * Math.pow(i, -0.9907) * c);
    }
    
    public void readSlot(int[] frame){
    	if(frame[(int) i]>1) {
			c++;
		} else if(frame[(int) i]==1) {
			s++;
		} else if(frame[(int) i]==0) {
			e++;
		}
    }
    
    public double log_2 (double a){
    	return (Math.log10(a) / Math.log10(2));
    }
    
    public double find_optimal_Q (double R){
    	return log_2 ( 2.39 * Math.pow(2, Qc) );
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ILCMSbS";
	}
    
}
