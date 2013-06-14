package edu.gatech.cs2340.risk.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.impl.ArmyServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskUtilMock;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() {
		//RiskUtil.deleteDatabaseIfExists(); 
    	//RiskUtil.buildDatabase();
    	//RiskUtil.checkForExistingTable("Players");
    	//RiskUtil.checkForExistingColumn("Players", "ArmyCount", "Integer");
    	RiskUtilMock.restoreDefaults();
    	
		PlayerServiceImpl playerService = new PlayerServiceImpl();
		ArmyServiceImpl armyService = new ArmyServiceImpl();

		Player rebecca = new Player(1, "Rebecca"); // these are my siblings' names...haha
		Player john = new Player(2, "John");
		Player anna = new Player(3, "Anna");
		Player david = new Player(4, "David");
		
		rebecca = playerService.addPlayer(rebecca);
		john  = playerService.addPlayer(john);
		anna = playerService.addPlayer(anna);
		david = playerService.addPlayer(david);
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(rebecca);
		players.add(john);
		players.add(anna);
		players.add(david);
		
		players = armyService.addArmies(players);
		assertNotNull(players.get(1).getArmies());
	}

}