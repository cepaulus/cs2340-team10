package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.model.Player;

public class PlayerUtil {
	
	private static Logger log = Logger.getLogger(PlayerUtil.class);
	
	private static final Random RANDOM = new Random();
	
	public static int rollDie() {
		return RANDOM.nextInt(6) + 1;
	}
	
	public static int[] rollDie(int numTimes) {
		int[] rollResults = new int[numTimes];
		for (int i = 0; i < numTimes; i ++) {
			rollResults[i] = rollDie();
		}
		return rollResults;
	}
	
	public static ArrayList<Player> setPlayerOrder(ArrayList<Player> players) {
		log.debug("Sorting players " + players);
		Collections.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player p1, Player p2) {
				return (p1.getRollOrder() > p2.getRollOrder() ? -1 : 1);
			}
		});
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setRollOrder(i + 1);
		}
		log.debug("Returning players " + players);
		return players;
	}

}
