package main.java.edu.gatech.cs2340.risk.dao;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;

/**
 * Territory Interface 
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public interface TerritoryDAO {
	/**
	 * Returns a list of all territories in the game
	 * 
	 * @return
	 */
	public ArrayList<Territory> getTerritories();
	/**
	 * Returns a list of all territories belonging to the county with 
	 * ID = countryId
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
	
	public Territory getTerritory(Player currentPlayer, int territoryId);
	
	/**
	 * Adds a randomly selected set of territories to each player and 
	 * returns all players
	 * 
	 * @param players
	 * @return
	 */
	public ArrayList<Player> addTerritories(ArrayList<Player> players);
	
	public ArrayList<Player> addWinCaseTerritories(ArrayList<Player> players);
	

}
