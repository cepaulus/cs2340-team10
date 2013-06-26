package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;
import java.util.AbstractCollection;

import main.java.edu.gatech.cs2340.risk.model.*;
import main.java.edu.gatech.cs2340.risk.service.impl.CountryServiceImpl;

/**
 * @author Brittany Wood
 *
 */
public class CountryUtil {
	private static CountryServiceImpl countryService = new CountryServiceImpl();
	private static int countryPoints;

	public static int getPointsForCountries(Player player){
		ArrayList<Territory> territories = player.getTerritories();
		ArrayList<Country> allCountries = countryService.getCountries();
		for (Country country : allCountries){
			ArrayList<Territory> countrysTerritories = country.getTerritories();
			if (territories.containsAll(countrysTerritories)) {
				countryPoints = countryPoints + country.getPointValue();
			}

		}
		return countryPoints;
	}	
	 
}
