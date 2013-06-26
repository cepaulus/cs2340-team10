package main.java.edu.gatech.cs2340.risk.dao;

import java.sql.SQLException; 
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;
/**
 * @author Caroline Paulus
 *
 */
public interface PlayerDAO {
	/**
	 * Returns list of all players currently participating in the game
	 * 
	 * @return 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Player> getPlayers() 
			throws SQLException, ClassNotFoundException; // returns all players
	/**
	 * Returns the player with ID matching playerId
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayer(int playerId); // returns player with id = playerId
	/**
	 * Adds the player to the database of players and returns it
	 * 
	 * @param player
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Player addPlayer(Player player)
			throws SQLException, ClassNotFoundException;
	/**
	 * Deletes the player from the database of players and returns it
	 * 
	 * @param playerId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void deletePlayer(int playerId) 
			throws SQLException, ClassNotFoundException;
}
