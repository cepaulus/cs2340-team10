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

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.TerritoryServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/** 
 * @author Caroline Paulus
 * 
 * This class receives and handles user input for the Risk game UI
 */
@WebServlet("/app")
public class AppController extends HttpServlet {

	private static Logger log = Logger.getLogger(AppController.class);

	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private TerritoryServiceImpl territoryService = new TerritoryServiceImpl();

	private ArrayList<Player> players; 
	private Player currentPlayer;

	private Territory attackingTerritory = null, defendingTerritory = null;
	String message = "";

	private int stage = 1;



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doGet()");

		// get players from jsons
		players = playerService.getPlayers();
		// put players in a random order
		players = PlayerUtil.setPlayerOrder(players);

		// determine the current player
		currentPlayer = players.get(0);
		log.debug("Current player: " + currentPlayer);

		// distribute armies to players
		log.debug("Adding armies to players");
		players = ArmyUtil.addArmies(players);

		// distribute territories to players
		log.debug("Adding territories to players");
		players = territoryService.addTerritories(players);
		
		message = currentPlayer.getPlayerName() 
				+ ", select a territory to reinforce with additional armies.";

		// set attributes to be displayed in the game
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doPost()");
		switch (stage) {
		case 1: distributeInitialArmies(request, response);
		break;
		case 2: distributeAdditionalArmies(request, response);
		break;
		case 3: selectAttackingTerritory(request, response);
		break;
		case 4: selectDefendingTerritory(request, response);
		break;
		case 5: doAttack(request, response);
		}
	}

	/**
	 * Players assign all their starting armies to territories
	 * Stage 1
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void distributeInitialArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In distributeInitialArmies()");
		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));

		// check current player owns the selected territory, and that the player
		// has armies left	
		log.debug("Current player ID: " + currentPlayerId);

		// player's list of territories contains the territory AND player has armies left 
		if (PlayerUtil.getPlayerById(players, currentPlayerId).getTerritories().contains(territory)
				&& PlayerUtil.getPlayerById(players, currentPlayerId).getAvailableArmies() > 0) {

			log.debug("Territory belongs to player " + currentPlayer + ".");
			for ( Territory t : currentPlayer.getTerritories() ) {
				if (t.equals(territory)) {
					log.debug("Adding army to territory " + territory);
					t.addArmy();
					PlayerUtil.getPlayerById(players, currentPlayerId).removeArmy();
				}
			}
			currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayerId);
			currentPlayerId = currentPlayer.getPlayerId();
			if (currentPlayer.getAvailableArmies() < 1) {
				log.debug("Entering secondary stage!");
				stage = 2;
				assignAdditionalArmies(request, response);
				return;
			}

		}
		else {
			log.debug("Territory does not belong to player");
		}

		log.debug("New current player: " + currentPlayer);
		request.setAttribute("currentPlayer", currentPlayer);
		
		message = currentPlayer.getPlayerName() 
				+ ", select a territory to reinforce with additional armies.";

		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Assign armies to player at the beginning of a turn
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void assignAdditionalArmies(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		// determine the number of armies the player should receive
		int armiesToAssign = ArmyUtil.getArmiesToAssign(currentPlayer);
		log.debug("Player " + currentPlayer + " is receiving " 
				+ armiesToAssign + " additional armies");

		currentPlayer.setAvailableArmies(armiesToAssign);
		
		message = currentPlayer.getPlayerName() + ", you have " 
				+ currentPlayer.getAvailableArmies() + " additional " 
				+ (currentPlayer.getAvailableArmies() > 1 ? "armies" : "army") + " to distribute.";

		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Stage 2
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void distributeAdditionalArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In distributeAdditionalArmies()");

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));

		// player's list of territories contains the territory AND player has armies left
		if (PlayerUtil.getPlayerById(players, currentPlayerId).getTerritories().contains(territory)
				&& PlayerUtil.getPlayerById(players, currentPlayerId).getAvailableArmies() > 0) {

			log.debug("Territory belongs to player " + currentPlayer + ".");
			for ( Territory t : currentPlayer.getTerritories() ) {
				if (t.equals(territory)) {
					log.debug("Adding army to territory " + territory);
					t.addArmy();
					PlayerUtil.getPlayerById(players, currentPlayerId).removeArmy();
				}
			}
			if (currentPlayer.getAvailableArmies() == 0) {
				stage = 3;
				message = currentPlayer.getPlayerName() + ", select" 
						+ " a territory to attack from or press the Attacks "
						+ "Complete button (Note: need to make this button!)";
			}
			else {
				message = currentPlayer.getPlayerName() + ", you have " 
						+ currentPlayer.getAvailableArmies() + " additional " 
						+ (currentPlayer.getAvailableArmies() > 1 ? "armies" : "army") + " to distribute.";
			}
		}
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}


	/**
	 * Stage 3
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectAttackingTerritory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In selectAttackingTerritory()");

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));

		// check current player owns the selected territory, and that the territory
		// has armies left	
		log.debug("Current player ID: " + currentPlayerId);
		if (PlayerUtil.getPlayerById(players, currentPlayerId).getTerritories().contains(territory)) {

			// make sure the territory being updated is the current version of the territory
			territory = TerritoryUtil.getTerritoryById(currentPlayer, territoryId);

			// make sure territory has enough armies to attack with
			if (territory.getNumberOfArmies() > 1) {

				log.debug("Territory belongs to player " + currentPlayer + ".");
				for ( Territory t : currentPlayer.getTerritories() ) {
					if (t.equals(territory)) {
						log.debug("Attacking territory: " + t);
						attackingTerritory = t;
						request.setAttribute("attackingTerritory", attackingTerritory);
						log.debug("Changing stage to 4");
						stage = 4;
					}
				}
			}
		}
		message = currentPlayer.getPlayerName() + ", select a territory "
				+ " to attack from " + attackingTerritory.getTerritoryName();
		
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Stage 4
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingTerritory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In selectDefendingTerritory()");

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		Player defendingPlayer = PlayerUtil.getPlayerByTerritory(players, territory);

		// make sure current player is not attacking one of their own territories
		if (! defendingPlayer.equals(currentPlayer)) {
			// make sure current territory is a neighbor of the attacking territory
			if (attackingTerritory.getNeighboringTerritories().contains(territory)) {

				// make sure the territory being updated is the current version of the territory
				territory = TerritoryUtil.getTerritoryById(defendingPlayer, territoryId);
				if (territory.getNumberOfArmies() > 1) {

					log.debug("Territory belongs to player " + defendingPlayer + ".");
					for ( Territory t : defendingPlayer.getTerritories() ) {
						if (t.equals(territory)) {
							log.debug("Defending territory: " + t);
							defendingTerritory = t;
							request.setAttribute("defendingTerritory", defendingTerritory);
							log.debug("Changing stage to 5");
							stage = 5;
							doAttack(request, response);
							return;
						}
					}
				}
			}
		}
		message = "The selected territory cannot be attacked from " 
				+ attackingTerritory.getTerritoryName() + ". Select another " +
						"territory or press the Attacks Complete button";
		
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		request.setAttribute("message", message);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Stage 5
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doAttack(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In doAttack()");
		log.debug("This method has not been written yet. Moving to next player.");
		
		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayerId);
		
		log.debug("Changing stage to Stage 2");
		stage = 2;
		assignAdditionalArmies(request, response);
		
	}

}
