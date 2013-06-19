package main.java.edu.gatech.cs2340.risk.service.impl;

import java.sql.SQLException; 
import java.util.ArrayList;
import main.java.edu.gatech.cs2340.risk.dao.mock.ArmyDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.ArmyService;

public class ArmyServiceImpl implements ArmyService {
	
	//private ArmyDAOImpl armyDAO = new ArmyDAOImpl();
	private ArmyDAOMock armyDAO = new ArmyDAOMock();
	
	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		players = armyDAO.addArmies(players);
		System.out.println("Players being returned: " + players);
		return players;
	}

	@Override
	public Player addArmies(Player player) {
		return armyDAO.addArmies(player);
	}

}
