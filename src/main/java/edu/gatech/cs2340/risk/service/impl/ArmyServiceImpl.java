package main.java.edu.gatech.cs2340.risk.service.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.ArmyDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.ArmyService;
import main.java.edu.gatech.cs2340.risk.util.RiskDatabaseUtil;

/**
 * @author Caroline Paulus
 *
 */
public class ArmyServiceImpl implements ArmyService {
	
	private static Logger log = Logger.getLogger(ArmyServiceImpl.class);
	
	//private ArmyDAOImpl armyDAO = new ArmyDAOImpl();
	private ArmyDAOMock armyDAO = new ArmyDAOMock();
	
	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		players = armyDAO.addArmies(players);
		log.debug("Players being returned: " + players);
		return players;
	}

}
