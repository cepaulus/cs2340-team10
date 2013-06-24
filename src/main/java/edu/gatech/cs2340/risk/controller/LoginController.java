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
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskMockUtil;

/** 
 * @author Caroline Paulus
 *
 * This class receives and handles user input for the home page
 * Responsible for initializing database and adding players
 */
@WebServlet("")
public class LoginController extends HttpServlet {

	private static Logger log = Logger.getLogger(LoginController.class); 
	
	private AppController appController = new AppController();
	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	ArrayList<Player> players = new ArrayList<Player>();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		// determines which operation has been requested
		String operation = (String) request.getParameter("operation");

		// if no operation has been requested, it indicates a name was added
		if (operation == null) {
			// set equal to POST so we don't get a null pointer exception
			operation = "POST";
			// if the user pressed Delete, sends request to Delete method
		} if (operation.equalsIgnoreCase("DELETE")) {
			doDelete(request, response);
		} else if (operation.equalsIgnoreCase("LAUNCH")) {
			// loads the game application at a different URL
			appController.doGet(request, response);
		} else {
			String name = request.getParameter("name");
			// create a new player
			Player player = new Player(players.size() + 1, name);
			log.debug("Creating player " + player);
			
			try {
				playerService.addPlayer(player);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO add error handling
				e.printStackTrace();
			}
			players.add(player); 

			// send the updated list back to login.jsp
			request.setAttribute("players", players);
			RequestDispatcher dispatcher = 
					getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);
		}
	}

	/**
	 * Called when page is first loaded
	 * Initializes "players" variable for login.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		
		RiskMockUtil.restoreDefaults();
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
		int id = getId(request);
		players.remove(id);
		// delete player from list
		// TODO this method has not been written yet
		try {
			playerService.deletePlayer(id);
		} catch (ClassNotFoundException | SQLException e) {
			// Add error handling
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
	 * @return
	 */
	private int getId(HttpServletRequest request) {
		String uri = request.getPathInfo();
		// Strip off the leading slash, e.g. "/2" becomes "2"
		String idStr = uri.substring(1, uri.length()); 
		return Integer.parseInt(idStr);
	}

}
