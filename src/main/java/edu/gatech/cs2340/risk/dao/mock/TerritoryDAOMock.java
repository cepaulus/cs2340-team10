package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.dao.TerritoryDAO;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * @author Caroline Paulus
 *
 */
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
			fileName = TERRITORY_FILE_PATH + i + ".json";
			Territory territory = (Territory) 
					RiskMockUtil.convertJsonFileToObject(fileName, Territory.class);
			territories.add(territory);
		}
		log.debug("Returning territories " + territories);
		return territories;
	}

	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		String fileName = COUNTRY_FILE_PATH + countryId + ".json";
		Country country = (Country) 
				RiskMockUtil.convertJsonFileToObject(fileName, Country.class);
		
		ArrayList<Territory> territories = new ArrayList<Territory>();
		for (Territory t : country.getTerritories()) {
			// build territory from json file
			Territory territory = getTerritory(t.getTerritoryId());
			territories.add(territory);
		}
		log.debug("Returning territories " + territories);
		return territories;
	}

	@Override
	public Territory getTerritory(int territoryId) {
		String fileName = TERRITORY_FILE_PATH + territoryId + ".json";
		Territory territory = (Territory) 
				RiskMockUtil.convertJsonFileToObject(fileName, Territory.class);
		
		log.debug("Returning territory " + territory);
		return territory;
	}

	@Override
	public ArrayList<Player> addTerritories(ArrayList<Player> players) {
		ArrayList<Territory> territories = getTerritories();
		Random rand = new Random();
		
		while (territories.size() > 0) {
			for (Player player : players) {
				if (territories.size() > 0) {
					int territoryId = rand.nextInt(territories.size());
					player.addTerritory(territories.get(territoryId));
					territories.remove(territoryId);
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

	@Override
	public Player addTerritories(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
