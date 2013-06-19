package main.java.edu.gatech.cs2340.risk.service.impl;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.mock.CountryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.service.CountryService;

/**
 * @author Caroline Paulus
 *
 */
public class CountryServiceImpl implements CountryService {
	
	private CountryDAOMock countryDAO = new CountryDAOMock();

	@Override
	public ArrayList<Country> getCountries() {
		return countryDAO.getCountries();
	}

	@Override
	public Country getCountry(int countryId) {
		return countryDAO.getCountry(countryId);
	}

}
