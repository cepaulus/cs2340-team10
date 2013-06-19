package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.PlayerDAO;
import main.java.edu.gatech.cs2340.risk.exception.PackageNotFoundException;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;
/**
 * @author Caroline Paulus
 *
 */
public class PlayerDAOMock implements PlayerDAO {
	
	private static final String PLAYER_JSON = "player/player";

	@Override
	public ArrayList<Player> getPlayers() {
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		// get existing number of players in player package
		int numPlayers = RiskMockUtil.getFileCountInPackage("player");
		System.out.println("Current player count in player json package: " + numPlayers);
		
		for (int i = 1; i <= numPlayers; i++) {
			String fileName = PLAYER_JSON + i + ".json";  
			Player player = (Player) RiskMockUtil.convertJsonFileToObject(fileName, Player.class);
			players.add(player);
		}
		return players;
	}

	@Override
	public ArrayList<Player> getPlayer(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player addPlayer(Player player) 
			throws SQLException, ClassNotFoundException {
		
		// create file for new player
		String newFile = PLAYER_JSON + player.getPlayerId() + ".json";
		System.out.println("Creating new json file for player at " + newFile);
		RiskMockUtil.createFileFromJson(newFile, player);
		
		// return player
		System.out.println("Returning player " + player);
		return player;
	}

	@Override
	public Player deletePlayer(int playerId) throws SQLException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
