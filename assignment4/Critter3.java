package assignment4;
import java.util.*;

public class Critter3 extends Critter{

	private int dir; 
	
	public Critter3(){
		dir = 0;
	}
	
	@Override
	public String toString(){ return "4";}
	
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
