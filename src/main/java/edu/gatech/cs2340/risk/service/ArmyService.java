package main.java.edu.gatech.cs2340.risk.service;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;

/**
 * @author Caroline Paulus
 *
 */
public interface ArmyService {
	/**
	 * Adds a list of armies to each player in the list of players
	 * 
	 * @param players
	 * @return the players passed as a parameter, with a designated number of armies
	 */
	public ArrayList<Player> addArmies(ArrayList<Player> players);

}
