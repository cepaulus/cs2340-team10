package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;

public class TerritoryUtil {

	/**
	 * Sorts territories based on their IDs
	 * 
	 * @param territories
	 * @return
	 */
	public static ArrayList<Territory> sort(ArrayList<Territory> territories) {

		Collections.sort(territories, new Comparator<Territory>() {

			@Override
			public int compare(Territory t1, Territory t2) {
				return (t1.getTerritoryId() > t2.getTerritoryId() ? 1 : -1);
			}
		});
		return territories;
	}

	public static Territory getTerritoryById(Player player, int territoryId) {
		
		for (Territory territory : player.getTerritories()) {
			if (territory.getTerritoryId() == territoryId) {
				return territory;
			}
		}
		return null;
	}

	public static Territory getTerritoryById(ArrayList<Player> players, int territoryId) {
		Territory territory;
		for (Player player : players) {
			territory = getTerritoryById(player, territoryId);
			if (territory != null) return territory;
		}
		return null;
	}

	public static Territory getTerritoryFromNeighborById (Territory territory, int neighboringTerritoryId) {
		for (Territory neighboringTerritory : territory.getNeighboringTerritories()) {
			if (neighboringTerritory.getTerritoryId() == neighboringTerritoryId) {
				return neighboringTerritory;
			}
		}
		return null;
	}

	public static boolean validAttackTerritory(Territory territory) {
		return territory != null && territory.getNumberOfArmies() > 1 && TerritoryUtil.anyEnemyTerritories(territory);
	}

	public static boolean canAttack(Player player) {
		for (Territory territory : player.getTerritories()) {
			if (validAttackTerritory(territory)) return true;
		}
		return false;
	}
	
	public static boolean validFortifyTerritory(Territory territory) {
		return territory != null && territory.getNumberOfArmies() > 1 && TerritoryUtil.anyFriendlyTerritories(territory);
	}
	
	public static boolean canFortify(Player player) {
		for (Territory territory : player.getTerritories()) {
			if (validFortifyTerritory(territory)) return true;
		}
		return false;
	}

	public static boolean anyEnemyTerritories (Territory territory) {
		Player owner = territory.getOwner();
		for (Territory neighboringTerritory : territory.getNeighboringTerritories()) {
			if (!neighboringTerritory.getOwner().equals(owner)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean anyFriendlyTerritories (Territory territory) {
		Player owner = territory.getOwner();
		for (Territory neighboringTerritory : territory.getNeighboringTerritories()) {
			if (neighboringTerritory.getOwner().equals(owner)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasValidNeighboringTerritory(Player currentPlayer, Territory territory) {
		
		for (Territory playerTerritory : currentPlayer.getTerritories()) {
			for (Territory neighborTerritory : territory.getNeighboringTerritories()) {
				if (playerTerritory.equals(neighborTerritory)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean validAttacksExist(Player currentPlayer) {
		for (Territory territory : currentPlayer.getTerritories()) {
			if (territory.getNumberOfArmies() > 1) {
				for (Territory neighborTerritory : territory.getNeighboringTerritories()) {
					if (! currentPlayer.getTerritories().contains(neighborTerritory) ) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
