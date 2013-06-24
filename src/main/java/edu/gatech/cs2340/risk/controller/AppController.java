package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import main.java.edu.gatech.cs2340.risk.service.impl.ArmyServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.CountryServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.TerritoryServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;

/** 
 * @author Caroline Paulus
 * 
 * This class receives and handles user input for the Risk game UI
 */
@WebServlet("/app")
public class AppController extends HttpServlet {

	private static Logger log = Logger.getLogger(AppController.class);

	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private ArmyServiceImpl armyService = new ArmyServiceImpl();
	private CountryServiceImpl countryService = new CountryServiceImpl();
	private TerritoryServiceImpl territoryService = new TerritoryServiceImpl();

	private ArrayList<Player> players; 
	private Player currentPlayer;
	private ArrayList<Country> countries;
	private HashMap<Integer, ArrayList<Territory>> territoryMap;
	

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
		
		players = armyService.addArmies(players);
		players = territoryService.addTerritories(players);
		request.setAttribute("players", players);

		countries = countryService.getCountries();
		request.setAttribute("countries", countries);
		
		territoryMap = new HashMap<Integer, ArrayList<Territory>>();
		for (Country country : countries) {
			ArrayList<Territory> territories = 
					territoryService.getTerritories(country.getCountryId());
			territoryMap.put(country.getCountryId(), territories);
		}
		request.setAttribute("territoryMap", territoryMap);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doPost()");
		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = territoryService.getTerritory(territoryId);
		log.debug("Current territory: " + territory);
                
		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
	       
                // check current player owns the selected territory, and that the player
	        // has armies left	
		log.debug("Current player ID: " + currentPlayerId);
		if (PlayerUtil.getPlayerById(players, currentPlayerId).getTerritories().contains(territory)
				&& PlayerUtil.getPlayerById(players, currentPlayerId).getNumberOfArmies() > 0) {

			log.debug("Territory belongs to player " + currentPlayer + ".");
			int countryId = territory.getCountry().getCountryId();
			log.debug("Country ID: " + countryId);
			for ( Territory t : territoryMap.get(countryId) ) {
				if (t.equals(territory)) {
					log.debug("Adding army to territory " + territory);
					t.addArmy();
					PlayerUtil.getPlayerById(players, currentPlayerId).removeArmy();
				}
			}
                       currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayerId);
                       currentPlayerId = currentPlayer.getPlayerId();

		}
		else {
			log.debug("Territory does not belong to player");
		}


		log.debug("New current player: " + currentPlayer);
		request.setAttribute("currentPlayer", currentPlayer);
		
		request.setAttribute("players", players);
		
		// send the updated list back to login.jsp
		request.setAttribute("countries", countries);
		request.setAttribute("territoryMap", territoryMap);
		request.setAttribute("currentPlayerId", currentPlayerId);
                RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

}
