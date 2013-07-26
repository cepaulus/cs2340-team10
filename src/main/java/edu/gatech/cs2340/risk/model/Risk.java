package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;

/**
 * Basic class that holds all the data essential to a game of risk
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class Risk {

	private AppController appController;
	
	private ArrayList<Player> players; 
	private Player currentPlayer;
	
	private int stage;
	private int step;
	private int directions;
	
	private Attack attack;
	private Move move;
	
	
	public Risk(AppController appController, ArrayList<Player> players) {
		this.setAppController(appController);
		setPlayers(players);
		currentPlayer = players.get(0);
	}

	public AppController getAppController() {
		return appController;
	}

	public void setAppController(AppController appController) {
		this.appController = appController;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void setCurrentPlayer(int currentPlayerId) {
		currentPlayer = PlayerUtil.getPlayerById(players, currentPlayerId);
	}
	
	public void moveToNextPlayer() {
		currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayer.getPlayerId());
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getDirections() {
		return directions;
	}

	public void setDirections(int directions) {
		this.directions = directions;
	}

	public Attack getAttack() {
		return ( attack == null ? new Attack() : attack );
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}

	public Move getMove() {
		return ( move  == null ? new Move() : move );
	}

	public void setMove(Move move) {
		this.move = move;
	}

}
