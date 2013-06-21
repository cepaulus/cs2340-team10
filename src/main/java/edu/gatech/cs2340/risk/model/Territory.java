package main.java.edu.gatech.cs2340.risk.model;

/**
 * @author Caroline Paulus
 *
 */
public class Territory {
	
	private int territoryId;
	private String territoryName;
	private Country country;
	private Player owner;
    private int numberOfArmies;        
	
	public Territory(int territoryId, String territoryName) {
		this.setTerritoryId(territoryId);
		this.territoryName = territoryName;
		
		setNumberOfArmies(1);
	}
	
	public int getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(int territoryId) {
		this.territoryId = territoryId;
	}

	public String getTerritoryName() {
		return territoryName;
	}
	
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

    public int getNumberOfArmies(){
    	return numberOfArmies;
    }
    
    public void setNumberOfArmies(int numberOfArmies) {
    	this.numberOfArmies = numberOfArmies;
    }
	
    public void addArmy() {
    	numberOfArmies++;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Territory.class) {
			return false;
		}
		if ( ((Territory) obj).getTerritoryId() == this.territoryId
				&& ((Territory) obj).getTerritoryName().equals(this.territoryName) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + territoryName + ", ID: " + territoryId + "]";
	}

}
