package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.dao.TerritoryDAO;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

public class TerritoryDAOMock implements TerritoryDAO {
	
	private static Logger log = Logger.getLogger(TerritoryDAOMock.class);
	
	private static final int TERRITORY_COUNT = 42;
	private static final String TERRITORY_FILE_PATH = "/territory/territory";
	private static final String COUNTRY_FILE_PATH = "/country/country";

	@Override
	public ArrayList<Territory> getTerritories() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		
		String fileName;
		for (int i = 1; i <= TERRITORY_COUNT; i++) {
			// get the location of each territory's json file
			fileName = TERRITORY_FILE_PATH + i + ".json";
			log.debug("Territory file path: " + fileName);
			// create a territory object from each territory json file
			Territory territory = (Territory) 
					RiskUtil.convertJsonFileToObject(fileName, Territory.class);
			// add territories to list 
			territories.add(territory);
		}

		log.debug("Returning territories " + territories);
		return territories;
	}

	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		// get the location of the json file for the country with ID = countryId
		String fileName = COUNTRY_FILE_PATH + countryId + ".json";
		// create a country object from the country's json file
		Country country = (Country) 
				RiskUtil.convertJsonFileToObject(fileName, Country.class);
		
		ArrayList<Territory> territories = new ArrayList<Territory>();
		
		// go through country's territories and get the IDs 
		// json file does not include all information about territories so 
		//    it is necessary to build them from their json files
		for (Territory t : country.getTerritories()) {
			// create a territory object from each territory's json file
			Territory territory = getTerritory(t.getTerritoryId());
			territories.add(territory);
		}
		log.debug("Returning territories " + territories);
		return territories;
	}

	@Override
	public Territory getTerritory(int territoryId) {
		// get the location of the territory's json file
		String fileName = TERRITORY_FILE_PATH + territoryId + ".json";
		// create a territory object from the territory's json file
		Territory territory = (Territory) 
				RiskUtil.convertJsonFileToObject(fileName, Territory.class);
		
		log.debug("Returning territory " + territory);
		return territory;
	}

	@Override
	public ArrayList<Player> addTerritories(ArrayList<Player> players) {
		// get all territories from jsons
		ArrayList<Territory> territories = getTerritories();
		cleanUpNeighboringTerritories(territories);

		Random rand = new Random();
		
		// continue until all territories have been assigned to players
		while (territories.size() > 0) {
			for (Player player : players) {
				
				// make sure there are still territories left to assign
				if (territories.size() > 0) {
					
					// randomly select a value between 0 and territories.size()-1
					int territoryIndex = rand.nextInt(territories.size());
					Territory territory = territories.get(territoryIndex);
					
					// add the territory located at that index to the player's list of territories
					player.addTerritory(territory);
					// remove that territory from the list of territories
					territories.remove(territoryIndex);
				}
				// if all the territories have been assigned, break out of the while loop
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
		// return the updated list of players 
		return players;
	}

	private static void cleanUpNeighboringTerritories(ArrayList<Territory> territories) {
		ArrayList<Territory> tempNeighboringTerritories;
		for (Territory territory : territories) {
			tempNeighboringTerritories = new ArrayList<Territory>();
			for (Territory neighboringTerritory : territory.getNeighboringTerritories()) {
				tempNeighboringTerritories.add(territories.get(neighboringTerritory.getTerritoryId()-1));
			}
			territory.setNeighboringTerritories(tempNeighboringTerritories);
		}
	}


	@Override
	public Player addTerritories(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
