package main.java.edu.gatech.cs2340.risk.model;

/**
 * Basic class to hold the origin, destination, and the number of armies during 
 * a movement
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class Move {

	Territory source;
	Territory destination;
	int numArmies;

	public Move (Territory source, Territory destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public Move (Territory source) {
		this(source, null);
	}
	
	public Move() {
		this(null, null);
	}
	
	public Territory getDestination() {
		return destination;
	}

	public void setDestination(Territory destination) {
		this.destination = destination;
	}

	public void setNumArmies(int numArmies) {
		this.numArmies = numArmies;
	}

	public Territory getSource() {
		return source;
	}
	
	public void setSource(Territory source) {
		this.source = source;
	}

	public void doMove() {
		destination.addNumberOfArmies(numArmies);
		source.removeNumberOfArmies(numArmies);
	}

	public boolean isValidSource() {
		return source.getNumberOfArmies() > 1;
	}

	public boolean isValidDestination() {
		return source.getOwner().equals(destination.getOwner());
	}

	public boolean oneArmyLeftToMove() {
		return source.getNumberOfArmies() == 2;
	}

	@Override
	public String toString() {
		return "[Source: " + source + ", destination: " + destination + "]";
	}

}