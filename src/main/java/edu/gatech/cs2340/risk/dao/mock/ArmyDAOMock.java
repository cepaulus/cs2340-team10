package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.ArmyDAO;
import main.java.edu.gatech.cs2340.risk.model.Army;
import main.java.edu.gatech.cs2340.risk.model.Player;
/**
 * @author Caroline Paulus
 *
 */
public class ArmyDAOMock implements ArmyDAO {
	
	private static Logger log = Logger.getLogger(ArmyDAOMock.class);

	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		int armyCount = 50 - (players.size() * 5);
		log.debug("Each player receives " + armyCount + " armies");
		int armyIdCount = 0;
		
		for (Player player : players) {
			for (int i = 0; i < armyCount; i++) {
				Army army = new Army(armyIdCount + i, player);
				player.addArmy(army);
			}
			armyIdCount = armyIdCount + armyCount;
		}
		return players;
	}

}
