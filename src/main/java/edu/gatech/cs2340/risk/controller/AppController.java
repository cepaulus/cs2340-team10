package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.helper.AttackController;
import main.java.edu.gatech.cs2340.risk.controller.helper.MoveController;
import main.java.edu.gatech.cs2340.risk.controller.helper.InitializeController;
import main.java.edu.gatech.cs2340.risk.controller.helper.TurnController;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.TerritoryServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;

/** 
 * 
 * This class receives and handles user input for the Risk game UI
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
@SuppressWarnings("serial")
@WebServlet("/app")
public class AppController extends HttpServlet {

	private static Logger log = Logger.getLogger(AppController.class);
	private Risk risk;
	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private TerritoryServiceImpl territoryService = new TerritoryServiceImpl();
	
	private TurnController turnController = new TurnController();
	private InitializeController initializeController = new InitializeController(turnController);
	private MoveController moveController = new MoveController(turnController);
	private AttackController attackController = new AttackController(turnController, moveController);

	public static final boolean WIN_CASE = false;
	private static final int NUMBER_OF_ARMIES = 3;
	
	/**
	 * Retrieves players, puts them in a random order, distributes
	 * armies and territories to players. 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Servlet Exception
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doGet()");

		ArrayList<Player> players = playerService.getPlayers();
		players = PlayerUtil.setPlayerOrder(players);
		log.debug("players after setPlayerOrder: " + players);

		log.debug("Adding armies to players");
		players = ArmyUtil.addArmies(players, NUMBER_OF_ARMIES); 
		log.debug("players after addArmies: " + players);

		if (WIN_CASE) {
			log.debug("Using win case to speed up game play");
			players = territoryService.addWinCaseTerritories(players);
		}
		else {
			log.debug("Adding territories to players");
			players = territoryService.addTerritories(players);
			log.debug("players after addTerritories: " + players);
		}
		risk = new Risk(this, players);
		risk.setStage(RiskConstants.INITIALIZE);
		risk.setStep(RiskConstants.NO_STEP);
		risk.setDirections(RiskConstants.INITIAL_DIRECTIONS);

		forwardUpdatedVariables(request, response, risk);
	}
	
	/**
	 * If there are players remaining then doPost continues the game
	 * by initiating stages of the game. 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Servlet Exception
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		if (risk.getStage() == RiskConstants.DECLARE_WINNER) {
			log.debug("Doing nothing. The game is over");
			risk.setStage(RiskConstants.GAME_OVER);
		}
		else {
			log.debug("In doPost()");
			if (! playersRemaining() ) {
				risk.setStage(RiskConstants.DECLARE_WINNER);
				risk.setStep(RiskConstants.NO_STEP);
				forwardUpdatedVariables(request, response, risk);
			}
			else {
				risk.setDirections(RiskConstants.NO_DIRECTIONS);
				switch (risk.getStage()) {
				case RiskConstants.INITIALIZE: 
					initializeController.doPost(request, response, risk);
					break;
				case RiskConstants.SETUP_TURN: 
					turnController.doPost(request, response, risk);
					break;
				case RiskConstants.ATTACK: 
					attackController.doPost(request, response, risk);
					break;
				case RiskConstants.MOVE_ARMIES: 
					moveController.doPost(request, response, risk);
					break;
				}
			}
		}
	}
	
	/**
	 * Checks to see if more than one player is remaining in the game
	 * 
	 * @return true if players > 1
	 */
	private boolean playersRemaining() {
		
		ArrayList<Player> playersCopy = new ArrayList<Player>(risk.getPlayers());

		for (Player player : risk.getPlayers()) {
			if (player.getTerritories().size() == 0) {
				
				log.debug("Removing json for player " + player);
				RiskUtil.deleteJsonFromPackage(player.getPlayerId());
				
				log.debug("Players before: " + risk.getPlayers());
				playersCopy.remove(player);
				
				log.debug("Remaining players: " + risk.getPlayers());
			}
		}
		
		risk.setPlayers(playersCopy);
		
		if (playersCopy.size() == 1) 
			return false;
		return true;
	}

	/**
	 * Sends updated variables back to app.jsp
	 * Called by all helper controllers
	 * 
	 * @param request
	 * @param response
	 * @param risk-Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
	public void forwardUpdatedVariables(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException,
			ServletException {

		request.setAttribute("currentPlayer", risk.getCurrentPlayer());
		request.setAttribute("players", risk.getPlayers());
		request.setAttribute("risk", risk);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		
		dispatcher.forward(request,response);
	}
	
}