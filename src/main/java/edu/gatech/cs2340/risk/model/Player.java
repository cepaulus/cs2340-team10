package main.java.edu.gatech.cs2340.risk.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Random;

/**
 * @author Caroline Paulus
 *
 */
public class Player {

	private String playerName;
	private int playerId;
	private ArrayList<Territory> territories;
	private ArrayList<Army> armies;
	private int rollOrder;
	private Color playerColor; 
	

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

	public ArrayList<Army> getArmies() {
		return armies;
	}

	public void setArmies(ArrayList<Army> armies) {
		this.armies = armies;
	}

	public void addArmy(Army army) {
		if (armies == null) {
			armies = new ArrayList<Army>();
		}
		armies.add(army);
	}

	public void setRollOrder(int rollOrder) {
		this.rollOrder = rollOrder;
	}        	

	public int getRollOrder() {
		return rollOrder;
	}
	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	@Override
	public String toString() {
		return "[Name: " + playerName + ", ID: " + playerId + "]";
	}

}
