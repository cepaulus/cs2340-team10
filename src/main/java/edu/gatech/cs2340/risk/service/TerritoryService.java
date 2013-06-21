package main.java.edu.gatech.cs2340.risk.service;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;

/**
 * @author Caroline Paulus
 *
 */
public interface TerritoryService {
	/**
	 * Returns a list of all territories in the game
	 * 
	 * @return
	 */
	public ArrayList<Territory> getTerritories();
	/**
	 * Returns a list of all territories belonging to the county with ID countryId
	 * 
	 * @param countryId
	 * @return
	 */
	public ArrayList<Territory> getTerritories(int countryId);
	/**
	 * Returns the territory with ID territoryId
	 * 
	 * @param territoryId
	 * @return
	 */
	public Territory getTerritory(int territoryId);
	/**
	 * Adds a randomly selected set of territories to each player and returns all players
	 * 
	 * @param players
	 * @return
	 */
	public ArrayList<Player> addTerritories(ArrayList<Player> players);
	/**
	 * Adds a randomly selected set of territories to the player and returns it
	 * 
	 * @param player
	 * @return
	 */
	public Player addTerritories(Player player);

}
