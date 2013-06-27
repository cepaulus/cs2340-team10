package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;

public class PlayerUtil {

	private static Logger log = Logger.getLogger(PlayerUtil.class);

	private static final Random RANDOM = new Random();
	private static Player selectedPlayer;
	private static Player nextPlayer;

	/**
	 * Simulates rolling a dice
	 * 
	 * @return Random number between 1 and 6
	 */
	public static int rollDie() {
		return RANDOM.nextInt(6) + 1;
	}

	/**
	 * Returns the results for rolling a certain number of dice
	 * 
	 * @param numDice  Number of dice being rolled
	 * @return List of results corresponding to the number of dice
	 */
	public static int[] rollDice(int numDice) {
		int[] rollResults = new int[numDice];
		for (int i = 0; i < numDice; i ++) {
			rollResults[i] = rollDie();
		}
		return rollResults;
	}

	/**
	 * Returns a list of players randomly sorted
	 * Used to determine roll order
	 * 
	 * @param players  Unsorted list of players
	 * @return Sorted list of players
	 */
	public static ArrayList<Player> setPlayerOrder(ArrayList<Player> players) {
		
		log.debug("Setting player order for " + players.size() + " players");
		// each player rolls the dice and is assigned a temporary roll value
		for (Player player : players) {
			player.setRollOrder(PlayerUtil.rollDie());
		}
		// sort the list of players based on the values they rolled
		Collections.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player p1, Player p2) {
				return (p1.getRollOrder() > p2.getRollOrder() ? 1 : -1);
			}
		});
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setRollOrder(i + 1);
		}
		log.debug("Returning players " + players);
		return players;
	}

	/**
	 * Returns the player with ID matching playerId
	 * @param players
	 * @param playerId
	 * @return
	 */
	public static Player getPlayerById(ArrayList<Player> players, int playerId) {
		for (Player player : players) {
			if (player.getPlayerId() == playerId){
				selectedPlayer = player;
			}
		}
		return selectedPlayer;
	}

	/**
	 * Returns the next player in the determined roll order
	 * 
	 * @param players
	 * @param currentPlayerId
	 * @return
	 */
	public static Player getNextPlayer(ArrayList<Player> players, int currentPlayerId) {
		int counter = 0;
		for (Player player : players) {
			counter++;
			if (counter >= players.size()) {
				counter = 0;
			}
			if (player.getPlayerId() == currentPlayerId){
				nextPlayer = players.get(counter);
			}
		}
		return nextPlayer;
	}
	
	/**
	 * Returns the player who owns the provided territory
	 * 
	 * @param players
	 * @param territory
	 * @return
	 */
	public static Player getPlayerByTerritory(ArrayList<Player> players, Territory territory) {
		
		for (Player player : players) {
			if (player.getTerritories().contains(territory)) {
				return player;
			}
		}
		return null;
	}

}
