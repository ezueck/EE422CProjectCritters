/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.List;
import java.util.*;
import java.lang.Math.*;
import java.lang.reflect.Constructor;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static boolean moveFight = false; 
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean moved; 
	
	protected final void walk(int direction) {
		// MERDE
		energy -= Params.walk_energy_cost;
	    my_move_fct(direction, 1);
	}
	
	protected final void run(int direction) {
		// MERDE
		energy -= Params.run_energy_cost;;
	    my_move_fct(direction, 2);
	}
	
	// MERDE
	protected final void my_move_fct(int direction, int amount){
		
		//don't move if you have moved this time step 
		if(moved){ return;}
		
		//record of old x_coord and y_coord 
		int oldX = x_coord;
		int oldY = y_coord;
		
		//move the bug 
		switch(direction){
		case 0:
			x_coord += amount;
			break;
		case 1:
			x_coord += amount;
			y_coord += amount;
			break;
		case 2:
			y_coord += amount;
			break;
		case 3:
			x_coord -= amount;
			y_coord += amount;
			break;
		case 4:
			x_coord -= amount;
			break;
		case 5:
			x_coord -= amount;
			y_coord -= amount;
			break;
		case 6:
			y_coord -= amount;
			break;
		case 7:
			x_coord += amount;
			y_coord -= amount;
			break;
		default:
			break;
		}
		
		//if the movement is during a fight, we can't move to an occupied spot 
		if(moveFight){
			for(Critter c : population){
				if(!(c.equals(this)) && c.x_coord == x_coord && c.y_coord == y_coord){
					x_coord = oldX;
					y_coord = oldY;
					return;
				}
			}
		}
		
		//set the true flag if we actually moved
		moved = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		
		//check if parent has enough energy to reproduce 
		if(energy<Params.min_reproduce_energy){return;}
		
		//change health
		offspring.energy = (int) Math.floor(0.5*energy);
		energy = (int) Math.ceil(0.5*energy);
		
		//give the child a position
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		offspring.moved = false;
		offspring.my_move_fct(direction, 1);
	}
	

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		//try to make the critter if not throw our exception
		try{
		//get constructor for our new Critter
		Class <?> newCritter = Class.forName(critter_class_name);
		Constructor<?> newConstructor = newCritter.getConstructor();
		Object obj = newConstructor.newInstance();
		
		//make the new critter
		Critter newOne = (Critter) obj;
		
		//initialize our values 
		newOne.energy = Params.start_energy;
		newOne.x_coord = getRandomInt(Params.world_width);
		newOne.y_coord = getRandomInt(Params.world_height);
		newOne.moved = false;
		
		//add it to the population
		population.add(newOne);
		
		}
		
		//catch our exceptions 
		catch(Exception e){
			throw new InvalidCritterException(critter_class_name);
		}

	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class <?> newCritter;
		Critter testCrit;
		
		//try to get an object of the class critter_class_name
		try{ 
			newCritter = Class.forName(critter_class_name);	
			Constructor<?> newConstructor = newCritter.getConstructor();
			Object obj = newConstructor.newInstance();
			//test critter
			testCrit = (Critter) obj;
		}
		
		//didn't exist, so throw InvalidCritterException
		catch(Exception e){
			throw new InvalidCritterException(critter_class_name);
		}
		
		//add all the critter of the same class 
		for (Critter crit : population){
			if(crit.getClass().equals(testCrit.getClass())){
				result.add(crit);
			}
		}
		
		//return our list 
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	public static void worldTimeStep() {
		
		//run all of the doTimeSteps 
		for(Critter c : population){
			c.doTimeStep();
		}
		
		moveFight = true;
		//iterate through Critters that need to fight 
		for(Critter fighter : population){
			for(Critter receiver : population){ //check against the whole population
				if(!fighter.equals(receiver) && receiver.x_coord == fighter.x_coord && receiver.y_coord == fighter.y_coord){
					simulateEncounter(fighter, receiver);
					if(fighter.energy <= 0){ //he lost so no more fighting for him
						break;
					}
				}
			}
		}
		
		moveFight = false;
		
		//update rest energy
		for(Critter c : population){
			c.energy -= Params.rest_energy_cost;
		}
		
		//spontaneous algae 
		for(int i =0; i<Params.refresh_algae_count; i++){
			Critter newAlg = new Algae();
			newAlg.x_coord = getRandomInt(Params.world_width);
			newAlg.y_coord = getRandomInt(Params.world_height);
		}
		
		//add babies 
		population.addAll(babies);
		babies.clear();
		
		//get rid of all the dead Critters 
		for(Critter c : population){
			if(c.energy<=0){
				population.remove(c);
			}
		}
		
		//reset the movement flag for all critters
		for(Critter c : population){
			c.moved = false; 
		}
		

	}
	
	public static void simulateEncounter(Critter fighter, Critter receiver){
		
		//dead bugs can't fight 
		if(fighter.energy == 0 || receiver.energy == 0) { return; }
		
		//check if they want to fight 
		boolean fighterRes = fighter.fight(receiver.toString());
		boolean receiverRes = receiver.fight(fighter.toString());
		
		//check if still in same spot 
		if(!(fighter.x_coord == receiver.x_coord) || !(fighter.y_coord == receiver.x_coord)){
			return;
		}
		if(fighter.energy == 0 || receiver.energy == 0) { return; }
	
		//both want to fight then 
		int rollFight = 0;
		if(fighterRes){
			rollFight = getRandomInt(fighter.energy + 1);
		}
		
		int rollRec = 0;
		if(receiverRes){
			rollRec =  getRandomInt(receiver.energy + 1);
		}
		
		//check who won the roll 
		if(rollFight>rollRec){
			fighter.energy += 0.5*receiver.energy;
			receiver.energy = 0;
		}
		else{
			receiver.energy += 0.5*fighter.energy;
			fighter.energy = 0;
		}
	}
	
	public static void displayWorld() {
		// display top boarder
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.print("+");
		// print world row by row. left border first then all critters or blank spaces than right boarder
		for(int i = 0; i < Params.world_height; i++){
			System.out.println();
			System.out.print("|");
			for(int j = 0; j < Params.world_width; j++){
				for(int k = 0; k < population.size(); k++){
					// check if theres a critter  at the location marked (j,i)
					if((population.get(k).x_coord == j) && (population.get(k).y_coord == i)){
						System.out.print(population.get(k).toString());
					}
					else{
						System.out.print(" ");
					}
				}
			}
			System.out.print("|");
		}
		System.out.println();
		
		// print bottom boarder
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.print("+");
	}
}
