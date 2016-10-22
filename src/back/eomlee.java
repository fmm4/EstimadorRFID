package back;

import java.lang.Math;
import java.math.BigDecimal;

/**
 * Estimador Lower Bound
 */
public class eomlee implements Estimator {

    public  double estimate (double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame){
    	double gamak;
    	double gamak_minus_1;
    	double betak;
    	
    	gamak = 2;
    	do{
    		
    		gamak_minus_1 = gamak;
    		
    		betak = betak(current_size, successful_slots, collision_slots, gamak_minus_1);
    		gamak = gamak(current_size, successful_slots, collision_slots, betak);
    		    		
    		
    	} while (Math.abs(gamak_minus_1 - gamak) > 0.0001);
    	
    	
    	//System.out.println("Gamak: "+gamak);
		return (gamak*collision_slots);
    }
    
    public String getName(){
    	return "EOM-LEE";
    }
    
    public double betak(double current_frame,double success_slot, double collision_slot, double gamak_minus_1){
    	return current_frame/(gamak_minus_1*collision_slot + success_slot);	
    	
    }
    
    public double gamak(double current_frame,double success_slot, double collision_slot, double betak){
    	double betaInvert = Math.exp(-1/betak); 
    	return (1-betaInvert)/(betak*(1-(1+(1/betak))*betaInvert));
    }
    

}
