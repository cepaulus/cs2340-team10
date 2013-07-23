package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.RiskUtil;

/** 
 *
 * This class receives and handles user input for the home page
 * Responsible for initializing database and adding players
 * 
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
@SuppressWarnings("serial")
@WebServlet("")
public class LoginController extends HttpServlet {

	private static Logger log = Logger.getLogger(LoginController.class); 
	private AppController appController = new AppController();
	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private ArrayList<Player> players = new ArrayList<Player>(); 

	/**
	 * Sets up the players in the game
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		String operation = (String) request.getParameter("operation");
		log.debug("Operation: " + operation);

		if (operation == null) 
			operation = "POST";
		if (operation.equalsIgnoreCase("DELETE")) 
			doDelete(request, response);
		else if (operation.equalsIgnoreCase("LAUNCH")) 
			appController.doGet(request, response);
		else {
			String name = request.getParameter("name");
			
			if(name.equals("")) 
				name = "Player "+(players.size()+1);
			
			Player player = new Player(players.size() + 1, name);
			log.debug("Creating player " + player);
			
			try {
				playerService.addPlayer(player);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			players.add(player); 

			request.setAttribute("players", players);
			RequestDispatcher dispatcher = 
					getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);
		}
	}

	/**
	 * Called when page is first loaded
	 * Initializes "players" variable for login.jsp
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		
		RiskUtil.restoreDefaults();
		players = new ArrayList<Player>();
		request.setAttribute("players", players);
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Deletes a player name from the list of players and the database
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		log.debug("In doDelete()");
		int id = Integer.parseInt(request.getParameter("id"));
		players.remove(id);
		try {
			playerService.deletePlayer(id);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("players", players);
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request,response);
	}
	
	/**
	 * Returns the number at the end of the URL corresponding to current player ID
	 * 
	 * @param request
	 * @return (int) playerID
	 */
	@SuppressWarnings("unused")
	private int getId(HttpServletRequest request) {
		String uri = request.getPathInfo();
		// Strip off the leading slash, e.g. "/2" becomes "2"
		String playerID = uri.substring(1, uri.length()); 
		return Integer.parseInt(playerID);
	}

}
