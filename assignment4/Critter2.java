package assignment4;

/*
 * Author: Pierre Follini
 * UT EID: pf4974
 * Date: 16 - OCT - 16
 * Project 4 - Critters
 * Critter 2
 */
 
 public class Critter2 extends Critter{
	
	@Override
	public String toString(){
		return "2";
	}

	private int direction;
	private int prev_direction;
	private boolean prev_run;
	
	public Critter2(){
		direction = 4;
		prev_direction = 4;
		prev_run = false;
	}
	@Override
	public void doTimeStep() {
		// will at most do a 90 degree turn from last direction (ie most he's gonna do is direction + 2)
		direction = prev_direction + Critter.getRandomInt(4) - 2;
		// will run when going in direction 0-3 walk when going in direction 4-7
		if(direction > 4){
			this.run(direction);
			prev_run = true;
		}
		else{
			this.walk(direction);
			prev_run = false;
		}
		
		//child will follow and walk in same direction
		if(this.getEnergy() > Params.min_reproduce_energy){
			Critter2 offspring = new Critter2();
			this.reproduce(offspring, direction);
		}
		
	}
	@Override
	public boolean fight(String oponent) {
		// will fight he ran last step
		if(prev_run){
			return true;
		}
		return false;
	}	
}
