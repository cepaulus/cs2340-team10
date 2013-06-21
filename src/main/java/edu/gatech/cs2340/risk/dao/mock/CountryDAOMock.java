package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.dao.CountryDAO;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;

/**
 * @author Caroline Paulus
 *
 */
public class CountryDAOMock implements CountryDAO {
	
	private static Logger log = Logger.getLogger(CountryDAOMock.class);
	
	private static final int COUNTRY_COUNT = 6;
	private static final String COUNTRY_FILE_PATH = "/country/country";

	@Override
	public ArrayList<Country> getCountries() {
		ArrayList<Country> countries = new ArrayList<Country>();
		
		String fileName;
		for (int i = 1; i <= COUNTRY_COUNT; i++) {
			fileName = COUNTRY_FILE_PATH + i + ".json";
			Country country = (Country) 
					RiskMockUtil.convertJsonFileToObject(fileName, Country.class);
			countries.add(country);
		}
		log.debug("Returning countries " + countries);
		return countries;
	}

	@Override
	public Country getCountry(int countryId) {
		String fileName = COUNTRY_FILE_PATH + countryId + ".json";
		Country country = (Country) 
				RiskMockUtil.convertJsonFileToObject(fileName, Country.class);
		
		log.debug("Returning country " + country); 
		return country;
	}

}
