package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.sql.SQLException; 
import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.PlayerDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;
/**
 * @author Caroline Paulus
 *
 */
public class PlayerDAOMock implements PlayerDAO {
	
	private static Logger log = Logger.getLogger(PlayerDAOMock.class);
	
	private static final String PLAYER_FILE_PATH = "player/player";

	@Override
	public ArrayList<Player> getPlayers() {
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		// get existing number of players in player package
		int numPlayers = RiskMockUtil.getFileCountInPackage("player");
		log.debug("Current player count in player json package: " + numPlayers);
		
		for (int i = 1; i <= numPlayers; i++) {
			
			// get the location of each player's json file
			// naming convention is player1.json, player2.json, etc
			String fileName = PLAYER_FILE_PATH + i + ".json";  
			
			// create a player object from the player json file
			Player player = (Player) RiskMockUtil.convertJsonFileToObject(fileName, Player.class);
			players.add(player);
		}
		return players;
	}

	@Override
	public Player getPlayer(int playerId) {
		// get the location of the player json file
		String fileName = PLAYER_FILE_PATH + playerId + ".json";
		// create a player object from the player json file
		Player player = (Player) 
				RiskMockUtil.convertJsonFileToObject(fileName, Player.class);
		
		log.debug("Returning country " + player); 
		return player;
	}

	@Override
	public Player addPlayer(Player player) 
			throws SQLException, ClassNotFoundException {
		
		// create file for new player at a location designated by the player's ID
		String newFile = PLAYER_FILE_PATH + player.getPlayerId() + ".json";
		log.debug("Creating new json file for player at " + newFile);
		RiskMockUtil.createFileFromJson(newFile, player);
		
		// return the same player that was passed as a parameter
		log.debug("Returning player " + player);
		return player;
	}

	@Override
	public void deletePlayer(int playerId) throws SQLException,
			ClassNotFoundException {
		
		RiskMockUtil.deleteJsonFromPackage(playerId);
	}

}
