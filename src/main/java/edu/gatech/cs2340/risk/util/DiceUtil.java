package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


import org.apache.log4j.Logger;

/**
 * A class that creates a list of dice that can be 
 * compared to other dice during attacks
 */
public class DiceUtil {
	
	private static final Random RANDOM = new Random();
	private static Logger log = Logger.getLogger(DiceUtil.class);
	
	/**
	 * Simulates rolling a dice
	 * 
	 * @return Random number between 1 and 6
	 */
	public static int rollDie() {
		return RANDOM.nextInt(6) + 1;
	}

	/**
	 * Randomizes dice and resorts them from greatest to least
	 */
	public static int[] rollDice(int count){
		int[] rollResults = new int[count];
		
		for (int i = 0; i < count; i++){
			rollResults[i] = rollDie();
		}
		rollResults = sort(rollResults);
		return rollResults;
	}
	
	/**
	 * Sorts dice rolls from largest to smallest
	 * 
	 * @param rollResults
	 * @return
	 */
	public static int[] sort(int[] rollResults) {
		
		ArrayList<Integer> rollResultsList = new ArrayList<Integer>();
		for (int i = 0; i < rollResults.length; i++) {
			rollResultsList.add(rollResults[i]);
		}
		
		Collections.sort(rollResultsList, new Comparator<Integer>() {

			@Override
			public int compare(Integer i1, Integer i2) {
				return (i1 > i2 ? -1 : 1);
			}
		});
		
		log.debug("Sorted dice list: " + rollResultsList);
		
		int[] sortedRollResults = new int[rollResults.length];
		for (int i = 0; i < sortedRollResults.length; i++) {
			sortedRollResults[i] = rollResultsList.get(i);
		}
		return sortedRollResults;
	}

}
