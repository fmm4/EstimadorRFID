package back;

import java.lang.Math;

/**
 * Estimador Lower Bound
 */
public class eomlee implements Estimator {

    public int estimate (double collision_slots, double empty_slots, double successful_slots, double current_size, int[] frame){
    	double L = current_size;
    	double gamak;
    	double gamak_minus_1;
    	double betak;
    	
    	gamak = 2;
    	do{
    		
    		gamak_minus_1 = gamak;
    		
    		betak = betak(current_size,successful_slots,collision_slots,gamak_minus_1);
    		gamak = gamak(current_size,successful_slots, collision_slots, betak);
    		    		
    		
    	} while (Math.abs(gamak_minus_1 - gamak) < 0.0001);
    	
    	
    	//System.out.println("Gamak: "+gamak);
		return (int) (gamak*collision_slots);
    }
    
    public String getName(){
    	return "EOM-LEE";
    }
    
    public double betak(double current_frame,double success_slot, double collision_slot, double gamak_minus_1){
    	return current_frame / (gamak_minus_1 * collision_slot + success_slot);
    }
    
    public double gamak(double current_frame,double success_slot, double collision_slot, double betak){
//    	double betak = betak(current_frame, success_slot, collision_slot, gamak_minus_1);
    	return (1 - Math.exp(-1/betak)) / 
    			( betak * ( 1 - ( 1 + ( 1 / betak ) ) * Math.exp(-1/betak) ) );
    }
    

}
