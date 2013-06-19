package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.gatech.cs2340.risk.model.Country;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.impl.ArmyServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.CountryServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskDatabaseUtil;

//import org.apache.log4j.Logger; TODO figure out how to get this to work

/** 
 * @author Caroline Paulus
 * 
 * This class receives and handles user input for the Risk game UI
 */
@WebServlet("/app")
public class AppController extends HttpServlet {
	
	//private static Logger log = Logger.getLogger(AppServlet.class);
	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private ArmyServiceImpl armyService = new ArmyServiceImpl();
	private CountryServiceImpl countryService = new CountryServiceImpl();
	
	private ArrayList<Player> players; 
	private ArrayList<Country> countries;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            		throws IOException, ServletException {
		
		players = playerService.getPlayers();
		players = PlayerUtil.setPlayerOrder(players);
		players = armyService.addArmies(players);
		request.setAttribute("players", players);
		
		countries = countryService.getCountries();
		request.setAttribute("countries", countries);
		
        RequestDispatcher dispatcher = 
            getServletContext().getRequestDispatcher("/app.jsp");
        dispatcher.forward(request,response);
	}


}
