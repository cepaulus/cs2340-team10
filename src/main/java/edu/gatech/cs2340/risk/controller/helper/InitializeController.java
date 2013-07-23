package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * Stage 1 (RiskConstants.INITIALIZE)
 *
 * @author Caroline Paulus
 * @author Brittany Wood
 * @author Julian Popescu
 * @author Alec Fenichal
 * @author Andrew Osborn
 */
public class InitializeController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(InitializeController.class);
	private TurnController rotateController = new TurnController();
	
	/**
	 * Distributes original armies at the beginning of the game
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) 
					throws IOException, ServletException {
		distributeInitialArmies(request, response, risk);
		return;
	}
	
	/**
	 * Called when players are distributing armies before game play begins
	 * Corresponds to Stage INITIALIZE, Step NO_STEP
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void distributeInitialArmies(HttpServletRequest request,
			HttpServletResponse response, Risk risk) 
					throws IOException, ServletException {

		log.debug("In distributeInitialArmies()");

		int currentPlayerID = 
				Integer.parseInt(request.getParameter("currentPlayerId"));
		risk.setCurrentPlayer(currentPlayerID);
		log.debug("Current player: " + risk.getCurrentPlayer());

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory currentTerritory = 
				TerritoryUtil.getTerritoryById(risk.getCurrentPlayer(), territoryId);

		if (currentTerritory != null && 
				risk.getCurrentPlayer().getAvailableArmies() > 0) {

			log.debug("Current territory: " + currentTerritory);

			currentTerritory.addArmy();
			risk.getCurrentPlayer().removeArmy();
			risk.moveToNextPlayer();

			if (risk.getCurrentPlayer().getAvailableArmies() < 1) {
				log.debug("Entering secondary stage!");
				risk.setStage(RiskConstants.SETUP_TURN);
				risk.setStep(RiskConstants.BEFORE_TURN);
				rotateController.doPost(request, response, risk);
				return;
			}
		} else 
			log.debug("Territory does not belong to player");
		
		log.debug("New current player: " + risk.getCurrentPlayer());

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}



}
