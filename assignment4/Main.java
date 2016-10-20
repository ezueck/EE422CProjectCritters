/* CRITTERS Main.java
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
package assignment4; // cannot be in default package
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // MERDE
        
        String keyboard_in = new String();
        boolean done = false;
        
        while(!done){
        	System.out.print("critters>");
        	keyboard_in = kb.nextLine();
        	String[] kb_inputs = keyboard_in.split(" ");	// splits the keyboard input into separate strings ie make Craig 25 will result in 3 strings
        	if(kb_inputs.length == 0){
        		// do nothing ie no command was inputed
        	}
        	else if(keyboard_in.contentEquals("quit")){
        		done = true;
        	}
        	else if(keyboard_in.contentEquals("show")){
        		Critter.displayWorld(); // MERDE
        	}
        	else if(kb_inputs[0].contentEquals("step")){
        		// check for the number of steps
        		int step_count = 0;
        		if(kb_inputs.length == 1){
        			step_count = 1;			// the command did not include a count thus set count to 1
        		}
        		else if(kb_inputs.length == 2){		// the command included a count
        			step_count = getInt(kb_inputs[1]);
        			if(step_count == -1){
        				step_count = 0;
        				System.out.println("error processing: " + keyboard_in);
        			}
        		}
        		else{
        			System.out.println("error processing: " + keyboard_in);
        		}
        		for(int i = 0; i < step_count; i++){
        			Critter.worldTimeStep();
        		}
        	}
        	else if(kb_inputs[0].contentEquals("seed")){
        		long seed = 0;
        		// get seed from keyboard
        		if(kb_inputs.length == 1){		// no seed number was included in command, set to 1
        			seed = 1; 
        		}
        		else if(kb_inputs.length == 2){
        			seed = getInt(kb_inputs[1]);
        			if(seed == -1){
        				seed = 1;
        				System.out.println("error processing: " + keyboard_in);
        			}
        		}
        		else{
        			System.out.println("error processing: " + keyboard_in);
        		}

        		Critter.setSeed(seed);
        	}
        	else if(kb_inputs[0].contentEquals("make")){
        		if(kb_inputs.length == 1){
        			System.out.println("error processing: " + keyboard_in);
        		}
        		else{
        			int critter_count = 0;
        			if(kb_inputs.length == 2){
        				critter_count = 1;
        			}
        			else if(kb_inputs.length == 3){
        				critter_count = getInt(kb_inputs[2]);
        				if(critter_count == -1){
        					critter_count = 0;
        					System.out.println("error processing: " + keyboard_in);
        				}
        			}
        			else{
        				System.out.println("error processing: " + keyboard_in);
        			}
        			
            		for(int i = 0; i < critter_count; i++){
            			try {
    						Critter.makeCritter(kb_inputs[1]);
    					} catch (InvalidCritterException e) {
    						// TODO Auto-generated catch block // MERDE
    						e.printStackTrace();
    						System.out.print("\n");
    					}
            		}
            		
        		}
        	}
        	else if(kb_inputs[0].contentEquals("stats")){
        		try {
					List<Critter> my_list = Critter.getInstances(kb_inputs[1]);
					Class<?> c = Class.forName(myPackage + "." + kb_inputs[1]);	
					Method stats = c.getMethod("runStats", List.class);
					stats.invoke(null, my_list);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.print("\n");
				}
        	}
        	else{
        		System.out.println("invalid command: " + keyboard_in);
        	}
        }
        
        /* Write your code above */
        System.out.flush();

    }
    

	// takes a string of integer and converts it to type int
	// returns -1 if string contained non numerical char
    static int getInt(String number){
    	int count = 0;
    	for(int i = 0; i < number.length(); i++){
			if(((number.charAt(i) - '0') >= 0) && ((number.charAt(i) - '0') <= 9)){	// checks that the char is a number
				count *= 10;
				count += number.charAt(i) - '0';
			}
			else{
				return -1;
			}
		}
    	return count;
    }
}
