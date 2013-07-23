package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

/**
 * Basic class for a country that owns territories and has an owner and
 * point value 
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class Country {
	
	private int countryId;
	private String countryName;
	private ArrayList<Territory> territories;
	private Player owner;
	private int pointValue;
	
	public Country(int countryId, String countryName, 
			ArrayList<Territory> territories, int pointValue) {
		this.setCountryId(countryId);
		this.setCountryName(countryName);
		this.setTerritories(territories);
		this.pointValue = pointValue;
		
		setOwner(null);
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Territory> territories) {
		this.territories = territories;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getPointValue() {
		return pointValue;
	}
	
	@Override
	public String toString() {
		return "[" + countryName + ", ID: " + countryId + "]";
	}

}
