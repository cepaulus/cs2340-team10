package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	//private static Logger log = Logger.getLogger(RiskServlet.class); 
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
		}
		// if a name has been changed, sends request to Put method
		if (operation.equalsIgnoreCase("PUT")) {
			doPut(request, response);
			// if the user pressed Delete, sends request to Delete method
		} else if (operation.equalsIgnoreCase("DELETE")) {
			doDelete(request, response);
		} else if (operation.equalsIgnoreCase("LAUNCH")) {
			appController.doGet(request, response);
		} else {
			String name = request.getParameter("name");
			
			Player player = new Player(players.size(), name);
			player.setRollOrder(PlayerUtil.rollDie());
			playerService.addPlayer(player);
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
	 * 
	 */
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		System.out.println("In doPut()");
		String name = (String) request.getParameter("name");
		int id = getId(request);
		
		Player player = playerService.addPlayer(new Player(id, name));
		players.add(player);
		request.setAttribute("players", players);
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request,response);
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {
		System.out.println("In doDelete()");
		int id = getId(request);
		players.remove(id);
		// delete player from database
		playerService.deletePlayer(id);
		request.setAttribute("players", players);
		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request,response);
	}

	private int getId(HttpServletRequest request) {
		String uri = request.getPathInfo();
		// Strip off the leading slash, e.g. "/2" becomes "2"
		String idStr = uri.substring(1, uri.length()); 
		return Integer.parseInt(idStr);
	}

}
