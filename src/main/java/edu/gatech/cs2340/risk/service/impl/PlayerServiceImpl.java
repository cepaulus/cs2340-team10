package main.java.edu.gatech.cs2340.risk.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import main.java.edu.gatech.cs2340.risk.dao.impl.PlayerDAOImpl;
import main.java.edu.gatech.cs2340.risk.dao.mock.PlayerDAOMock;
import main.java.edu.gatech.cs2340.risk.exception.PackageNotFoundException;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.PlayerService;

/**
 * @author Caroline Paulus
 *
 */
public class PlayerServiceImpl implements PlayerService {
	
	private PlayerDAOMock playerDAO = new PlayerDAOMock();

	@Override
	public ArrayList<Player> getPlayers() {
		return playerDAO.getPlayers();
	}

	@Override
	public ArrayList<Player> getPlayer(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player addPlayer(Player player) 
			throws ClassNotFoundException, SQLException {

		return playerDAO.addPlayer(player);

	}

	@Override
	public Player deletePlayer(int playerId) 
			throws ClassNotFoundException, SQLException {
			
		return playerDAO.deletePlayer(playerId);
	}


}
