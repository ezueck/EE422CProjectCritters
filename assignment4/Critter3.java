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
 

public class Critter3 extends Critter{

	private int dir; 
	
	public Critter3(){
		dir = 0;
	}
	
	@Override
	public String toString(){ return "3";}
	
	@Override 
	public void doTimeStep(){
	
		//only walks horizontally
		int rand = getRandomInt(2);
		if(rand%2 ==0){ dir  = 0;}
		else{dir = 5;}
		walk(dir);
		
		//reproduce 
		if (getEnergy() > 150) {
			Critter4 child = new Critter4();
			reproduce(child, Critter.getRandomInt(8));
		}
		
	}

	public boolean fight(String enemy){
		
		//50:50 chance of running or fighting 
		int rand = getRandomInt(20);
		if(rand%2 == 0){
			return true;
		}
		
		//decided to run
		run(dir);
		return false;
	}
}
