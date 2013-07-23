package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.sql.SQLException; 
import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.PlayerDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;

/**
 * Manages players
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */

public class PlayerDAOMock implements PlayerDAO {
	
	private static Logger log = Logger.getLogger(PlayerDAOMock.class);
	private static final String PLAYER_FILE_PATH = "player/player";

	/**
	 * Returns an arraylist of players
	 * 
	 * @return ArrayList<Player> player
	 */
	@Override
	public ArrayList<Player> getPlayers() {
		
		ArrayList<Player> players = new ArrayList<Player>();
		int numPlayers = RiskUtil.getFileCountInPackage("player");
		log.debug("Current player count in player json package: " + numPlayers);
		
		for (int i = 1; i <= numPlayers; i++) {
			String fileName = PLAYER_FILE_PATH + i + ".json";  
			Player player = 
					(Player) RiskUtil.convertJsonFileToObject(fileName, Player.class);
			players.add(player);
		}
		return players;
	}

	/**
	 * Returns the player with the given player ID
	 * 
	 * @param playerId
	 * @return player
	 */
	@Override
	public Player getPlayer(int playerId) {
		String fileName = PLAYER_FILE_PATH + playerId + ".json";
		Player player = (Player) 
				RiskUtil.convertJsonFileToObject(fileName, Player.class);
		log.debug("Returning country " + player); 
		return player;
	}
	
	/**
	 * @param player
	 * @throws SQLException
	 * @throws ClassNotFoundException	 
	 * @return player
	 */
	@Override
	public Player addPlayer(Player player) 
			throws SQLException, ClassNotFoundException {
		
		String newFile = PLAYER_FILE_PATH + player.getPlayerId() + ".json";
		log.debug("Creating new json file for player at " + newFile);
		RiskUtil.createFileFromJson(newFile, player);
		log.debug("Returning player " + player);
		return player;
	}
	
	/**
	 * @param playerId
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void deletePlayer(int playerId) throws SQLException,
			ClassNotFoundException {
		
		RiskUtil.deleteJsonFromPackage(playerId);
	}

}
