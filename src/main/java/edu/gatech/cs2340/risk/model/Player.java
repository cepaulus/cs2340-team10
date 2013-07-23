package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

/**
 * Basic class that holds information about a player in the game
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class Player {

	private String playerName;
	private int playerId;
	private ArrayList<Territory> territories;
	private int availableArmies;
	private int rollOrder; 
	

	public Player( int playerId, String playerName) {
		this.playerId = playerId;
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Territory> territories) {
		this.territories = territories;
	}

	public void addTerritory(Territory territory) {
		if (territories == null) {
			territories = new ArrayList<Territory>();
		}
		territory.setOwner(this);
		territories.add(territory);
	}

	public void removeTerritory(Territory territory) {
		for (int i = 0; i < territories.size(); i++) {
			if (territories.get(i).equals(territory)) {
				territories.remove(i);
				break;
			}
		}
	}

	public int getAvailableArmies() {
		return availableArmies;
	}

	public void setAvailableArmies(int availableArmies) {
		this.availableArmies = availableArmies;
	}
	
	public void addArmy() {
		availableArmies++;
	}
	
	public void removeArmy() {
		availableArmies--;
	}

	public void setRollOrder(int rollOrder) {
		this.rollOrder = rollOrder;
	}        	

	public int getRollOrder() {
		return rollOrder;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Player.class) {
			return false;
		}
		if ( ((Player) obj).getPlayerId() == this.playerId
				&& ((Player) obj).getPlayerName().equals(this.playerName) ) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + playerName + ", ID: " + playerId + "]";
	}

}
