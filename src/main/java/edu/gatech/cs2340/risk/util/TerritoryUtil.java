package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;

public class TerritoryUtil {

	public static ArrayList<Territory> sort(ArrayList<Territory> territories) {

		Collections.sort(territories, new Comparator<Territory>() {

			@Override
			public int compare(Territory t1, Territory t2) {
				return (t1.getTerritoryId() > t2.getTerritoryId() ? 1 : -1);
			}
		});
		return territories;

	}

	public static Territory getTerritoryById(Player player, int territoryId) {
		
		for (Territory territory : player.getTerritories()) {
			if (territory.getTerritoryId() == territoryId) {
				return territory;
			}
		}
		return null;
	}
}
