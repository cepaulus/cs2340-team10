package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;
import java.util.Random;
import org.apache.log4j.Logger;
import main.java.edu.gatech.cs2340.risk.dao.TerritoryDAO;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * Creates and adds territories
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class TerritoryDAOMock implements TerritoryDAO {

	private static Logger log = Logger.getLogger(TerritoryDAOMock.class);
	private static final int TERRITORY_COUNT = 42;
	private static final String TERRITORY_FILE_PATH = "/territory/territory";
	private static final String COUNTRY_FILE_PATH = "/country/country";

	/**
	 * Creates territories using each territory's json files and returns
	 * them in an ArrayList<Territory>
	 * 
	 * @return territories
	 */
	@Override
	public ArrayList<Territory> getTerritories() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		String fileName;
		
		for (int i = 1; i <= TERRITORY_COUNT; i++) {
			fileName = TERRITORY_FILE_PATH + i + ".json";
			log.debug("Territory file path: " + fileName);
			Territory territory = (Territory) 
					RiskUtil.convertJsonFileToObject(fileName, Territory.class);
			territories.add(territory);
		}

		log.debug("Returning territories " + territories);
		return territories;
	}
	/**
	 * Creates territories using each of the territory's json file if its ID =
	 * country ID
	 * 
	 * @param countryID
	 * @return territories
	 */
	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		String fileName = COUNTRY_FILE_PATH + countryId + ".json";
		Country country = (Country) 
				RiskUtil.convertJsonFileToObject(fileName, Country.class);
		ArrayList<Territory> territories = new ArrayList<Territory>();

		for (Territory t : country.getTerritories()) {
			Territory territory = getTerritory(t.getTerritoryId());
			territories.add(territory);
		}
		log.debug("Returning territories " + territories);
		return territories;
	}
	/**
	 * Creates a territory the territory's json file and returns it
	 * 
	 * @param territoryID
	 * @return territories
	 */
	@Override
	public Territory getTerritory(int territoryId) {
		String fileName = TERRITORY_FILE_PATH + territoryId + ".json";
		Territory territory = (Territory) 
				RiskUtil.convertJsonFileToObject(fileName, Territory.class);

		log.debug("Returning territory " + territory);
		return territory;
	}


	@Override
	public Territory getTerritory(Player currentPlayer, int territoryId) {
		return TerritoryUtil.getTerritoryById(currentPlayer, territoryId);
	}

	/**
	 * Assigns all territories to players given
	 * 
	 * @param players
	 * @return players
	 */
	@Override
	public ArrayList<Player> addTerritories(ArrayList<Player> players) {
		// get all territories from jsons
		ArrayList<Territory> territories = getTerritories();
		addNeighboringTerritories(territories);

		Random rand = new Random();

		while (territories.size() > 0) {
			for (Player player : players) {
				if (territories.size() > 0) {
					int territoryIndex = rand.nextInt(territories.size());
					Territory territory = territories.get(territoryIndex);
					player.addTerritory(territory);
					territories.remove(territoryIndex);
				}
				else {
					break;
				}
			}
		}
		for (int i = 0; i < players.size(); i ++) {
			// sort territories by ID
			players.get(i).setTerritories(TerritoryUtil.sort(players.get(i).getTerritories()));
			log.debug("Player " + players.get(i) 
					+ " has territories " + players.get(i).getTerritories());
		}
		return players;
	}
	/**
	 * Assigns neighboring territories to each territory in the given ArrayList
	 * 
	 * @param territories
	 */
	private static void addNeighboringTerritories(ArrayList<Territory> territories) {
		ArrayList<Territory> tempNeighboringTerritories;
		for (Territory territory : territories) {
			tempNeighboringTerritories = new ArrayList<Territory>();
			
			for (Territory neighboringTerritory : territory.getNeighboringTerritories()) 
				tempNeighboringTerritories.add(territories.get(neighboringTerritory.getTerritoryId()-1));
			
			territory.setNeighboringTerritories(tempNeighboringTerritories);
		}
	}

	/**
	 * Assigns players territories at the start of the game
	 * 
	 * @param players
	 */
	@Override
	public ArrayList<Player> addWinCaseTerritories(ArrayList<Player> players) {
		
		Player winningPlayer = players.get(0);
		ArrayList<Territory> territories = getTerritories();
		addNeighboringTerritories(territories);
		Random rand = new Random();
		while (territories.size() > players.size() -1) {
				int territoryIndex = rand.nextInt(territories.size());
				Territory territory = territories.get(territoryIndex);
				winningPlayer.addTerritory(territory);
				territories.remove(territoryIndex);
		}
		for (int i = 1; i < players.size(); i++) {
			players.get(i).addTerritory(territories.get(0));
			territories.remove(0);
		}
		for (int i = 0; i < players.size(); i ++) {
			players.get(i).setTerritories(TerritoryUtil.sort(players.get(i).getTerritories()));
			log.debug("Player " + players.get(i) 
					+ " has territories " + players.get(i).getTerritories());
		}
		return players;
	}

}
