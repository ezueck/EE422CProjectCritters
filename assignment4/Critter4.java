/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Eduardo Zueck Garces
 * ez2959
 * Pierre Follini
 * pf4974
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;
import java.util.*;


/*
 * Author: Eduardo Zueck Garces
 * UT EID: ez2959
 * Date: 16 - OCT - 16
 * Project 4 - Critters
 * Critter 2
 */
 

public class Critter4 extends Critter {

	private int dir; 
	
	public Critter4(){
		dir = getRandomInt(8);
	}
	
	@Override
	public String toString(){ return "4";}
	
	@Override 
	public void doTimeStep(){
	
		//walks randomly 
		dir = getRandomInt(8);
		
		//tries to run or walk 
		int random = getRandomInt(2);
		if(random%2==0){
			run(dir);
		}
		else{
			walk(dir);
		}
		
		//reproduce 
		if (getEnergy() > 150) {
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}
		
	}

	public boolean fight(String enemy){
		
		//this little buddy hates Craig :(
		if(enemy.equals("C")){
			return true;
		}
		
		//tries to run or walk 
		int random = getRandomInt(2);
		if(random%2==0){
			run(dir);
		}
		else{
			walk(dir);
		}
		
		return false; 
	}
}
