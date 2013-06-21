package main.java.edu.gatech.cs2340.risk.dao.impl;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.edu.gatech.cs2340.risk.dao.ArmyDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * 
 * @author Caroline Paulus
 *
 */
public class ArmyDAOImpl implements ArmyDAO {

	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) 
			throws SQLException, ClassNotFoundException {
		
		int armyCount = 50 - (players.size() * 5);
		Connection conn = DriverManager.getConnection(RiskConstants.DB_URL 
				+ RiskConstants.DB_NAME, RiskConstants.DB_USER, RiskConstants.DB_PASSWORD);
		Class.forName(RiskConstants.JDBC_DRIVER);
		
		int armyIdCount = 0;
		Statement s = conn.createStatement();
		for (Player player : players) {
			String sql = "UPDATE Players SET ArmyCount = " + armyCount
					+ " WHERE id = " + player.getPlayerId() + ";";
			s.executeUpdate(sql);
			player.setNumberOfArmies(armyCount);
			armyIdCount = armyIdCount + armyCount;
		}
		
		s.close();
		conn.close();
		
		return players;
	}

}
