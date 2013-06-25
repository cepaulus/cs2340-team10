package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

/**
 * @author Caroline Paulus
 *
 */
public class Player {

	private String playerName;
	private int playerId;
	private ArrayList<Territory> territories;
	private int numberOfArmies;
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
		territories.add(territory);
	}

	public int getNumberOfArmies() {
		return numberOfArmies;
	}

	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}
	
	public void addArmy() {
		numberOfArmies++;
	}
	
	public void removeArmy() {
		numberOfArmies--;
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
