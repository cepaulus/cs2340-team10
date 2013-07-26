package main.java.edu.gatech.cs2340.risk.service.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.service.TerritoryService;

public class TerritoryServiceImpl implements TerritoryService {
	
	private static Logger log = Logger.getLogger(TerritoryServiceImpl.class);
	
	private TerritoryDAOMock territoryDAO = new TerritoryDAOMock();

	@Override
	public ArrayList<Territory> getTerritories() {
		return territoryDAO.getTerritories();
	}

	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		return territoryDAO.getTerritories(countryId);
	}

	@Override
	public Territory getTerritory(int territoryId) {
		return territoryDAO.getTerritory(territoryId);
	}

	@Override
	public Territory getTerritory(Player currentPlayer, int territoryId) {
		return territoryDAO.getTerritory(currentPlayer, territoryId);
	}
	
	@Override
	public ArrayList<Player> addTerritories(ArrayList<Player> players) {
		log.debug("Adding territories to players");
		return territoryDAO.addTerritories(players);
	}

	@Override
	public ArrayList<Player> addWinCaseTerritories(ArrayList<Player> players) {
		return territoryDAO.addWinCaseTerritories(players);
	}

}
