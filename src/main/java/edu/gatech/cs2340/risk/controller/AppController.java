package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.service.impl.CountryServiceImpl;
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

	private int stage = 1;
	


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doGet()");
		players = playerService.getPlayers();
		players = PlayerUtil.setPlayerOrder(players);

		currentPlayer = players.get(0);
		log.debug("Current player: " + currentPlayer);
		request.setAttribute("currentPlayer", currentPlayer);

		players = ArmyUtil.addArmies(players);
		players = territoryService.addTerritories(players);
		request.setAttribute("players", players);

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
			case 1: doInitialStage(request, response);
					break;
			case 2: doSecondaryStage(request, response);
					break;
			case 3: doAttackStage(request, response);
					break;
		}
	}

	protected void doInitialStage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In doInitialStage()");
		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));

		// check current player owns the selected territory, and that the player
		// has armies left	
		log.debug("Current player ID: " + currentPlayerId);
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
			if (currentPlayer.getAvailableArmies() == 0) {
				log.debug("Entering secondary stage!");
				stage = 2;
				 assignSecondaryArmies(request, response);
				 return;
			}
			currentPlayerId = currentPlayer.getPlayerId();

		}
		else {
			log.debug("Territory does not belong to player");
		}

		log.debug("New current player: " + currentPlayer);
		request.setAttribute("currentPlayer", currentPlayer);

		request.setAttribute("players", players);

		// send the updated list back to login.jsp
		request.setAttribute("currentPlayerId", currentPlayerId);
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}
	
	protected void assignSecondaryArmies(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		// determine the number of armies the player should receive 
		int armiesToAssign = ArmyUtil.getArmiesToAssign(currentPlayer);
		
		currentPlayer.setAvailableArmies(armiesToAssign);
		PlayerUtil.getPlayerById(players, currentPlayer.getPlayerId()).setAvailableArmies(armiesToAssign);
		
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}
	
	protected void doSecondaryStage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("In doSecondaryStage()");

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		
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
			}
		}
		request.setAttribute("currentPlayer", currentPlayer);

		request.setAttribute("players", players);
		
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}
	
	protected void doAttackStage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("In doAttackStage()");
		
		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));

		// check current player owns the selected territory, and that the territory
		// has armies left	
		log.debug("Current player ID: " + currentPlayerId);
		if (PlayerUtil.getPlayerById(players, currentPlayerId).getTerritories().contains(territory)) {
			
			territory = TerritoryUtil.getTerritoryById(currentPlayer, territoryId);
			if (territory.getNumberOfArmies() > 1) {
				
				log.debug("Territory belongs to player " + currentPlayer + ".");
				for ( Territory t : currentPlayer.getTerritories() ) {
					if (t.equals(territory)) {
						log.debug("Attacking territory: " + t);
						attackingTerritory = t;
						request.setAttribute("attackingTerritory", attackingTerritory);
					}
				}
			}
		}
		else {
			if (attackingTerritory != null) { // check if territories are neighbors
				defendingTerritory = territory;
				log.debug("Defending territory: " + defendingTerritory);
				//showAttackOptions
				request.setAttribute("defendingTerritory", defendingTerritory);
			}
			else {
				log.debug("Else condition?");
			}
		}
		
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}


}
