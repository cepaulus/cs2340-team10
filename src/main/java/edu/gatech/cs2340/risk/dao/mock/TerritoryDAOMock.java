package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.TerritoryDAO;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;

/**
 * @author Caroline Paulus
 *
 */
public class TerritoryDAOMock implements TerritoryDAO {
	
	private static final int TERRITORY_COUNT = 42;
	private static final String TERRITORY_FILE_PATH = "/territory/territory";
	private static final String COUNTRY_FILE_PATH = "/country/country";

	@Override
	public ArrayList<Territory> getTerritories() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		
		String fileName;
		for (int i = 1; i <= TERRITORY_COUNT; i++) {
			fileName = TERRITORY_FILE_PATH + i + ".json";
			Territory territory = (Territory) 
					RiskMockUtil.convertJsonFileToObject(fileName, Territory.class);
			territories.add(territory);
		}
		return territories;
	}

	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		String fileName = COUNTRY_FILE_PATH + countryId + ".json";
		Country country = (Country) 
				RiskMockUtil.convertJsonFileToObject(fileName, Country.class);
		
		ArrayList<Territory> territories = new ArrayList<Territory>();
		for (Territory t : country.getTerritories()) {
			// build territory from json file
			Territory territory = getTerritory(t.getTerritoryId());
			territories.add(territory);
		}
		return territories;
	}

	@Override
	public Territory getTerritory(int territoryId) {
		String fileName = TERRITORY_FILE_PATH + territoryId + ".json";
		Territory territory = (Territory) 
				RiskMockUtil.convertJsonFileToObject(fileName, Territory.class);
		return territory;
	}

}
