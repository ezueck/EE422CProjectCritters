package assignment4;

/*
 * Author: Pierre Follini
 * UT EID: pf4974
 * Date: 16 - OCT - 16
 * Project 4 - Critters
 * Critter 1
 */

public class Critter1 extends Critter{

	@Override
	public String toString(){
		return "1";
	}
	
	private int direction;
	
	public Critter1(){
		direction = Critter.getRandomInt(12) % 8;
	}
	@Override
	public void doTimeStep() {
		// walks or run with equal chance
		// 2x more likely to go up (direction 1-3)
		int[] prob_dir = new int[8];
		for(int i = 0; i < 8; i++){
			prob_dir[i] = Critter.getRandomInt(50);
		}
		for(int i = 1; i < 4; i++){
			prob_dir[i] = Critter.getRandomInt(100);
		}
		int dir = 0;
		for(int i = 0; i < 8; i++){
			if(prob_dir[i] > dir){
				dir = prob_dir[i];
				direction = i;
			}
		}
		
		if((Critter.getRandomInt(8) % 2) == 0){
			this.walk(direction);
		}
		else{
			this.run(direction);
		}
		
		// child will walk off in random location (more likely to go up)
		if(this.getEnergy() > Params.min_reproduce_energy){
			Critter1 offspring = new Critter1();
			this.reproduce(offspring, (Critter.getRandomInt(12) % 8));
		}
	
		
	}

	@Override
	public boolean fight(String oponent) {
		// fights 90% of the time
		int prob = Critter.getRandomInt(100);
		if(prob <= 90){
			return true;
		}
		// runs 10% of the time
		run(direction);
		return false;
	}
}
