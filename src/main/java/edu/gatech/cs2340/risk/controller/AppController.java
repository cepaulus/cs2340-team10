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
	private int attackingArmyNum = 0;
	private int defendingArmyNum = 0;

	private int stage;
	private Integer directionsList;


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

		stage = 1;
		directionsList = 1;

		dispatch(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doPost()");
		switch (stage) {
		case 1: directionsList = 0;
				distributeInitialArmies(request, response);
				break;
		case 2: directionsList = 0;
				distributeAdditionalArmies(request, response);
				break;
		case 3: selectAttackingTerritory(request, response);
				break;
		case 4: directionsList = 0;
				selectDefendingTerritory(request, response);
				break;
		case 5: directionsList = 0;
				stage = 7;
				selectOptions(request, response);
				break;
		case 6: directionsList = 0;
				stage = 6;
				selectDefendingNumberOfArmies(request, response);
				break;
		case 7: directionsList = 0;
				stage = 7;
				selectOptions(request, response);
				break;
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

		request.setAttribute("directionsList", directionsList);

		log.debug("In distributeInitialArmies()");

		setCurrentPlayer(request);
		log.debug("Current player: " + currentPlayer);

		Territory territory = getPostedTerritory(request);

		if (territory != null && currentPlayer.getAvailableArmies() > 0) {

			log.debug("Current territory: " + territory);

			territory.addArmy();
			currentPlayer.removeArmy();

			nextPlayer();

			if (currentPlayer.getAvailableArmies() < 1) {
				log.debug("Entering secondary stage!");
				stage = 2;
				assignAdditionalArmies(request, response);
				return;
			}

		} else {
			log.debug("Territory does not belong to player");
		}

		log.debug("New current player: " + currentPlayer);

		dispatch(request, response);
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

		directionsList = 2;

		dispatch(request, response);
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

		setCurrentPlayer(request);

		Territory territory = getPostedTerritory(request);

		if (territory != null && currentPlayer.getAvailableArmies() > 0) {

			log.debug("Current territory: " + territory);

			territory.addArmy();
			currentPlayer.removeArmy();

			if (currentPlayer.getAvailableArmies() == 0) {
				stage = 7;
				directionsList = 0;
			}

		} else {
			log.debug("Territory does not belong to player");
		}

		dispatch(request, response);
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

		setCurrentPlayer(request);

		Territory territory = getPostedTerritory(request);

		if (TerritoryUtil.validAttackTerritory(territory)) {

			log.debug("Current territory: " + territory);

			attackingTerritory = territory;
			log.debug("Attacking territory: " + attackingTerritory);
			request.setAttribute("attackingTerritory", attackingTerritory);
			log.debug("Changing stage to 4");
			stage = 4;

		} else {
			log.debug("Territory not satisfactory");
		}

		dispatch(request, response);
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

		boolean cancelled = Boolean.parseBoolean(request.getParameter("cancelled"));

		if (cancelled) {
			stage = 7;
			directionsList = 0;

			dispatch(request, response);
			return;
		}

		attackingArmyNum = Integer.parseInt(request.getParameter("attackingArmyNum"));

		int neighboringTerritoryId = Integer.parseInt(request.getParameter("neighboringTerritoryId"));
		defendingTerritory = TerritoryUtil.getTerritoryFromNeighborById(attackingTerritory, neighboringTerritoryId);
		log.debug("Defending territory: " + defendingTerritory);

		if (defendingTerritory.getNumberOfArmies() > 1) {
			log.debug("Changing stage to 6");
			stage = 6;
			request.setAttribute("defendingTerritory", defendingTerritory);
		} else {
			defendingArmyNum = 1;
			log.debug("Changing stage to 5");
			stage = 5;
			doAttack(request, response);
			return;
		}
		
		dispatch(request, response);
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

		int[] attackingArmyDice = PlayerUtil.rollDice(Math.min(attackingArmyNum, 3));
		int[] defendingArmyDice = PlayerUtil.rollDice(Math.min(defendingArmyNum, 2));

		boolean attackerWin = PlayerUtil.calculateAttackWinner(attackingArmyDice, defendingArmyDice);
		String attackResultsMessage = PlayerUtil.doAttack(attackerWin, attackingArmyNum, defendingArmyNum, attackingTerritory, defendingTerritory);

		log.debug(attackResultsMessage);

		request.setAttribute("attackingArmyDice", attackingArmyDice);
		request.setAttribute("defendingArmyDice", defendingArmyDice);
		request.setAttribute("attackResultsMessage", attackResultsMessage);

		dispatch(request, response);

	}

	/**
	 * Stage 6
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingNumberOfArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		defendingArmyNum = Integer.parseInt(request.getParameter("defendingArmyNum"));

		log.debug("Changing stage to 5");
		stage = 5;
		doAttack(request, response);


		return;

	}

	/**
	 * Stage 7
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String option = request.getParameter("option");

		if (option != null) {
			switch (option) {
				case "attack":		stage = 3;
									directionsList = 3;	
									break;

				case "fortify":		//MUST BE WRITTEN --> MOVING TO NEXT PLAYER

				case "end turn":	directionsList = 0;
									stage = 2;
									nextPlayer();
									log.debug("New Current Player: " + currentPlayer);
									assignAdditionalArmies(request, response);
									break;
			}
		}

		dispatch(request, response);

	}

	private void dispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	private void setCurrentPlayer(HttpServletRequest request) {
		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		currentPlayer = PlayerUtil.getPlayerById(players, currentPlayerId);
	}

	private void nextPlayer() {
		currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayer.getPlayerId());
	}

	private Territory getPostedTerritory(HttpServletRequest request) {
		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		return TerritoryUtil.getTerritoryById(currentPlayer, territoryId);
	}

}