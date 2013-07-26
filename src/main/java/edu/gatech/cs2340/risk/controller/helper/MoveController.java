package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Move;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * Stage 4 (RiskConstants.MOVE_ARMIES)
 * 
 */
@SuppressWarnings("serial")
public class MoveController extends HttpServlet {
	
	private static Logger log;
	private TurnController turnController;

	public MoveController() {
		this(new TurnController());
	}

	public MoveController(TurnController turnController) {
		super();
		log = Logger.getLogger(MoveController.class);
		this.turnController = turnController;
	}
	
	/**
	 * Calls helper methods within MoveController to initialize Stage 4 steps
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 * @throws ServletException
	 * @throws IOException
	 */

	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException,
			IOException {

		switch (risk.getStep()) {
		case RiskConstants.SELECT_SOURCE_TERRITORY:
			selectSourceTerritory(request, response, risk);
			break;
		case RiskConstants.SELECT_DESTINATION_TERRITORY:
			selectDestinationTerritory(request, response, risk);
			break;
		case RiskConstants.DO_MOVE:
			doMove(request, response, risk);
			break;
		case RiskConstants.ATTACK_MOVE:
			attackMove(request, response, risk);
			break;
		}
	}

	/**
	 * Called when armies are being transferred and player is selecting the
	 * source territory Corresponds to Stage MOVE_ARMIES, Step
	 * SELECT_SOURCE_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 *            Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void selectSourceTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException,
			ServletException {

		log.debug("In selectSourceTerritory()");

		risk.setCurrentPlayer(Integer.parseInt(request
				.getParameter("currentPlayerId")));

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory currentTerritory = TerritoryUtil.getTerritoryById(
				risk.getCurrentPlayer(), territoryId);

		if (currentTerritory != null
				&& currentTerritory.getNumberOfArmies() > 1) {

			if (TerritoryUtil.hasValidNeighboringTerritory(
					risk.getCurrentPlayer(), currentTerritory)) {
				log.debug("Current territory: " + currentTerritory);

				risk.setMove(new Move(currentTerritory));
				log.debug("Changing step to SELECT_DESTINATION_TERRITORY");
				risk.setStep(RiskConstants.SELECT_DESTINATION_TERRITORY);
			} else {
				log.debug("Territory does not have any valid neighboring territories");
			}
		} else {
			log.debug("Territory cannot be used as source territory");
		}
		risk.getAppController()
				.forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Called when armies are being transferred and player is selecting the
	 * destination territory Corresponds to Stage MOVE_ARMIES, Step
	 * SELECT_DESTINATION_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 *            Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDestinationTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException,
			IOException {

		log.debug("In selectDestinationTerritory()");

		boolean cancelled = Boolean.parseBoolean(request
				.getParameter("cancelled"));

		if (cancelled) {
			risk.setStage(RiskConstants.SETUP_TURN);
			risk.setStep(RiskConstants.SHOW_OPTIONS);
			turnController.revertHasFortified();
			risk.getAppController().forwardUpdatedVariables(request, response, risk);
			return;
		}
		int neighboringTerritoryId = Integer.parseInt(request
				.getParameter("neighboringTerritoryId"));
		Territory destinationTerritory = TerritoryUtil
				.getTerritoryFromNeighborById(risk.getMove().getSource(),
						neighboringTerritoryId);

		risk.getMove().setDestination(destinationTerritory);
		log.debug("Destination territory: " + destinationTerritory);
		risk.getMove().setNumArmies(
				Integer.parseInt(request.getParameter("numArmies")));
		log.debug("Changing step to DO_MOVE");
		risk.setStep(RiskConstants.DO_MOVE);
		doMove(request, response, risk);

	}

	/**
	 * Updates the variables associated with the current move Corresponds to
	 * Stage MOVE_ARMIES, Step ATTACK_MOVE
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 *            Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void attackMove(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException,
			IOException {

		risk.getMove().setNumArmies(Integer.parseInt(request.getParameter("numArmies")));
		log.debug("Changing step to DO_MOVE");
		risk.setStep(RiskConstants.DO_MOVE);
		doMove(request, response, risk);
	}

	/**
	 * Updates the variables associated with the current move Corresponds to
	 * Stage MOVE_ARMIES, Step DO_MOVE
	 * 
	 * @param request
	 * @param response
	 * @param risk
	 *            Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doMove(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException,
			IOException {

		log.debug("In doMove()");
		risk.getMove().doMove();
		risk.setStage(RiskConstants.SETUP_TURN);
		risk.setStep(RiskConstants.SHOW_OPTIONS);
		log.debug("Changing stage to SETUP_TURN and step to SHOW_OPTIONS");
		turnController.determineNextMove(request, response, risk);
	}
}
