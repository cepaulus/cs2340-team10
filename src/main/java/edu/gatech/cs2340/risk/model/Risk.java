package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;

public class Risk {

	private AppController appController;
	
	private ArrayList<Player> players; 
	private Player currentPlayer;
	
	private int stage;
	private int step;
	private int directionsNum;
	
	private Attack attack;
	private Fortify fortify;
	
	
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

	public int getDirectionsNum() {
		return directionsNum;
	}

	public void setDirectionsNum(int directionsNum) {
		this.directionsNum = directionsNum;
	}

	public Attack getAttack() {
		return attack;
	}

	public void setAttack(Attack attack) {
		this.attack = attack;
	}

	public Fortify getFortify() {
		return fortify;
	}

	public void setFortify(Fortify fortify) {
		this.fortify = fortify;
	}

}
