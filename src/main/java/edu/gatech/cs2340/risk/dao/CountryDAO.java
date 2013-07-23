package main.java.edu.gatech.cs2340.risk.dao;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.Country;
/**
 * Country Interface
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public interface CountryDAO {
	/**
	 * Returns a list of all countries in the game
	 * 
	 * @return
	 */
	public ArrayList<Country> getCountries();
	/**
	 * Returns the country with ID countryId
	 * 
	 * @param countryId
	 * @return
	 */
	public Country getCountry(int countryId);

}
