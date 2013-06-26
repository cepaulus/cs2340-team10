package main.java.edu.gatech.cs2340.risk.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.PlayerDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * @author Caroline Paulus
 *
 */
public class PlayerDAOImpl implements PlayerDAO {

	@Override
	public ArrayList<Player> getPlayers() throws SQLException, ClassNotFoundException {
		
		ArrayList<Player> players = new ArrayList<Player>();
		Connection conn = DriverManager.getConnection(RiskConstants.DB_URL 
				+ RiskConstants.DB_NAME, RiskConstants.DB_USER, RiskConstants.DB_PASSWORD);
		Class.forName(RiskConstants.JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "SELECT * FROM Players;";
		System.out.println(sql); // needs to be log eventually
		ResultSet rs = s.executeQuery(sql);
		
		while (rs.next()) {
			Player player = new Player(rs.getInt("id"), rs.getString("name"));
			players.add(player);
		}
		
		s.close();
		conn.close();
		
		return players;
	}

	@Override
	public Player getPlayer(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player addPlayer(Player player) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(RiskConstants.DB_URL 
				+ RiskConstants.DB_NAME, RiskConstants.DB_USER, RiskConstants.DB_PASSWORD);
		Class.forName(RiskConstants.JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "INSERT INTO Players(id, name) VALUES (" 
				+ player.getPlayerId() + ", '" + player.getPlayerName() + "')";
		System.out.println(sql); // needs to be log eventually
		s.execute(sql);
		s.close();
		conn.close();
		
		return player;
	}

	@Override
	public void deletePlayer(int playerId) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(RiskConstants.DB_URL 
				+ RiskConstants.DB_NAME, RiskConstants.DB_USER, RiskConstants.DB_PASSWORD);
		Class.forName(RiskConstants.JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "SELECT * FROM Players WHERE id = " + playerId + ";";
		System.out.println(sql); // needs to be log eventually
		ResultSet rs = s.executeQuery(sql);
		
		Player player = new Player(rs.getInt("id"), rs.getString("name"));
		if (rs.next()) {
			// this is not good - means the program found two players with the same id
			// handle this
		}
		
		sql = "DELETE * FROM PLayers WHERE id = " + playerId + ";";
		s.execute(sql);
		s.close();
		conn.close();
	}

	
}