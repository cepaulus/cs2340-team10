package main.java.edu.gatech.cs2340.risk.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import main.java.edu.gatech.cs2340.risk.model.Player;

/**
 * @author Caroline Paulus
 *
 */
public interface ArmyDAO {
	/**
	 * Adds a list of armies to each player in the list of players
	 * 
	 * @param players
	 * @return the players passed as a parameter, with a designated number of armies
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Player> addArmies(ArrayList<Player> players) 
			throws SQLException, ClassNotFoundException;

}
