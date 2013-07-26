package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.CountryUtil;

public class ArmyUtil {
	
	private static Logger log = Logger.getLogger(ArmyUtil.class);
	
	/**
	 * Calculates number of armies to assign based on number of players 
	 * Updates each player's availableArmies and returns all players
	 * 
	 * @param players
	 * @return list of players with armies assigned
	 */
	public static ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		// determine number of armies each player should be initially assigned
		int armyCount = 3;//50 - (players.size() * 5); 
		log.debug("Each player receives " + armyCount + " armies");
		
		for (Player player : players) {
			// assign the player's army count to the determined value
			player.setAvailableArmies(armyCount);
		}
		// return the updated list of players
		return players;
	}
	
	public static ArrayList<Player> addArmies(ArrayList<Player> players, int numArmies) {
		
		// determine number of armies each player should be initially assigned
		int armyCount = numArmies; 
		log.debug("Each player receives " + armyCount + " armies");
		
		for (Player player : players) {
			// assign the player's army count to the determined value
			player.setAvailableArmies(armyCount);
		}
		// return the updated list of players
		return players;
	}

	/**
	 * Determines the number of armies a player has earned and returns it
	 * 
	 * @param currentPlayer
	 * @return number of armies a player is given to distribute at the beginning of a turn
	 */
	public static int getArmiesToAssign(Player currentPlayer) {
		// if player has less than 9 territories, assign 3
		int armiesFromTerritories = ( currentPlayer.getTerritories().size() < 9 
				? 3 : currentPlayer.getTerritories().size()/3 );
		// get armies earned from owning countries
		int armiesFromCountries = CountryUtil.getPointsForCountries(currentPlayer); 
		int totalArmies = armiesFromTerritories + armiesFromCountries;
		return totalArmies;
	}

}
