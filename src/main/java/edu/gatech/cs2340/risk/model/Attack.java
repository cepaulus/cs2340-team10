package main.java.edu.gatech.cs2340.risk.model;

import main.java.edu.gatech.cs2340.risk.util.DiceUtil;

/**
 * A class that contains the parameters needed for an attack, provides getters
 * and setters, and has an attack method.
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class Attack {

	Territory attackingTerritory;
	Territory defendingTerritory;
	int[] attackingArmyDice;
	int[] defendingArmyDice;
	int attackingArmyNum;
	int defendingArmyNum;

	public Attack() {
		this.attackingTerritory = new Territory();
		this.defendingTerritory = new Territory();
	}

	public Attack (Territory attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}
	
	public void setDefendingTerritory (Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	public void setAttackingArmyNum (int attackingArmyNum) {
		this.attackingArmyNum = attackingArmyNum;
	}

	public void setDefendingArmyNum (int defendingArmyNum) {
		this.defendingArmyNum = defendingArmyNum;
	}

	public Territory getAttackingTerritory() {
		return (attackingTerritory == null ? new Territory() : attackingTerritory);
	}

	public Territory getDefendingTerritory() {
		return (defendingTerritory == null ? new Territory() : defendingTerritory);
	}

	public int[] getAttackingArmyDice() {
		return attackingArmyDice;
	}

	public int[] getDefendingArmyDice() {
		return defendingArmyDice;
	}


	/**
	 * Roll attacking and defending dice and produce a results message to return
	 * as a string
	 * 
	 * @return results
	 */
	public String doAttack() {

		attackingArmyDice = DiceUtil.rollDice(Math.min(attackingArmyNum, 3));
		defendingArmyDice = 
				DiceUtil.rollDice(Math.min(defendingTerritory.getNumberOfArmies(), 2));

		int[] results = calculateAttackWinners();
		this.removeArmies(results);

		return createResultsMessage(results);
	}
	
	/**
	 * Method to test attacking by creating non-randomized dice
	 * 
	 * @return results
	 */
	public String doBiasedAttack() {
		attackingArmyDice = new int[Math.min(attackingArmyNum, 3)];
		defendingArmyDice = 
				new int[Math.min(defendingTerritory.getNumberOfArmies(), 2)];
		
		for (int i = 0; i < attackingArmyDice.length; i++) {
			attackingArmyDice[i] = 6;
		}
		for (int i = 0; i < defendingArmyDice.length; i++) {
			defendingArmyDice[i] = 1;
		}
		
		int[] results = calculateAttackWinners();
		this.removeArmies(results);

		return createResultsMessage(results);
	}
	/**
	 * Calcuates number of wins for the attacker
	 * @return results (int[] results = {defendingLoss, attackingLoss})
	 */
	private int[] calculateAttackWinners() {

		int defendingLoss = 0;
		int attackingLoss = 0;

		for (int i = 0; i < Math.max(attackingArmyDice.length, defendingArmyDice.length); i++) {
			if (i == Math.min(attackingArmyDice.length, defendingArmyDice.length)) {
				break;
			}
			if (attackingArmyDice[i] > defendingArmyDice[i]) {
				defendingLoss++;
			} else {
				attackingLoss++;
			}
		}

		int[] results = {defendingLoss, attackingLoss};
		return results;
	}

	/**
	 * Creates a prompt message for the result of the attack 
	 * @param results
	 * @return resultsMessage
	 */
	private String createResultsMessage(int[] results) {
		if (defendingTerritoryIsConquered()) {
			return "Attack Successful! Territory acquired.";
		}
		String resultsMessage = "";
		String attackSide;
		for (int i = 0; i < results.length; i++) {
			attackSide = (i == 0 ? "defending" : "attacking");
			if (results[i] > 0) {
				resultsMessage += "" + results[i] + " " + 
						attackSide + " arm" + (results[i] < 2 ? "y" : "ies") 
						+ " removed. ";
			}
		}

		return resultsMessage;
	}

	/**
	 * removes losses
	 * @param results
	 */
	private void removeArmies(int[] results) {
		defendingTerritory.removeNumberOfArmies(results[0]);
		attackingTerritory.removeNumberOfArmies(results[1]);

		if (defendingTerritoryIsConquered()) {
			this.conquerTerritory();
		}
	}
	
	/**
	 * Changes owner of territory
	 */
	private void conquerTerritory() {
		defendingTerritory.getOwner().removeTerritory(defendingTerritory);
		attackingTerritory.getOwner().addTerritory(defendingTerritory);
	}
	/**
	 * @return true if numArmies < 1, false otherwise
	 */
	public boolean defendingTerritoryIsConquered() {
		return defendingTerritory.getNumberOfArmies() < 1;
	}

}