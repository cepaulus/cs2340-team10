package edu.gatech.cs2340.risk.service;

import static org.junit.Assert.*; 

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() {
		//RiskUtil.deleteDatabaseIfExists(); 
		//RiskUtil.buildDatabase();
		//RiskUtil.checkForExistingTable("Players");
		//RiskUtil.checkForExistingColumn("Players", "ArmyCount", "Integer");
		RiskMockUtil.restoreDefaults();

		PlayerServiceImpl playerService = new PlayerServiceImpl();

		Player rebecca = new Player(1, "Rebecca"); // these are my siblings' names...haha
		Player john = new Player(2, "John");
		Player anna = new Player(3, "Anna");
		Player david = new Player(4, "David");

		try {
			rebecca = playerService.addPlayer(rebecca);
			john  = playerService.addPlayer(john);
			anna = playerService.addPlayer(anna);
			david = playerService.addPlayer(david);
		}
		catch (SQLException | ClassNotFoundException e) {
			// error handle?
		}
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(rebecca);
		players.add(john);
		players.add(anna);
		players.add(david);

		players = ArmyUtil.addArmies(players);
		assertNotNull(players.get(1).getAvailableArmies());
	}

}
