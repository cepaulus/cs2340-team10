package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.controller.AppController;
import main.java.edu.gatech.cs2340.risk.model.Attack;
import main.java.edu.gatech.cs2340.risk.model.Move;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * Stage 3 (RiskConstants.ATTACK)
 *
 */
@SuppressWarnings("serial")
public class AttackController extends HttpServlet {

	private static Logger log;

	private TurnController turnController;
	public AttackController() {
		this(new TurnController());
	}

	public AttackController(TurnController turnController) {
		this(turnController, new MoveController());
	}

	public AttackController(MoveController moveController) {
		this(new TurnController(), moveController);
	}

	public AttackController(TurnController turnController, MoveController moveController) {
		super();
		log = Logger.getLogger(AttackController.class);
		this.turnController = turnController;
	}


 
	/**
	 * Processes the attack by calling helper methods contained
	 * within AttackController
	 * @param request
	 * @param response
	 * @param risk
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response,
			Risk risk) throws ServletException, IOException {

		switch (risk.getStep()) {
		case RiskConstants.SELECT_ATTACKING_TERRITORY: 
			selectAttackingTerritory(request, response, risk);
			break;
		case RiskConstants.SELECT_DEFENDING_TERRITORY: 
			selectDefendingTerritory(request, response, risk);
			break;
		case RiskConstants.SELECT_DEFENDING_ARMIES: 
			selectDefendingNumberOfArmies(request, response, risk);
			break;
		case RiskConstants.DO_ATTACK: 
			processAttackRequest(request, response, risk);
			break;
		case RiskConstants.PROCESS_ATTACK: 
			processAttackRequest(request, response, risk);
			break;
		}
	}

	/**
	 * Called when a player has selected a territory to attack from
	 * Corresponds to Stage ATTACK, Step SELECT_ATTACKING_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void selectAttackingTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In selectAttackingTerritory()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory attackingTerritory = TerritoryUtil.getTerritoryById(risk.getCurrentPlayer(), territoryId);

		if (TerritoryUtil.validAttackTerritory(attackingTerritory)) {

			log.debug("Current territory: " + attackingTerritory);
			risk.setAttack(new Attack(attackingTerritory));

			log.debug("Attacking territory: " + attackingTerritory);
			request.setAttribute("attackingTerritory", attackingTerritory);

			log.debug("Changing step to SELECT_DEFENDING_TERRITORY");
			risk.setStep(RiskConstants.SELECT_DEFENDING_TERRITORY);

		} else {
			if (! TerritoryUtil.validAttacksExist(risk.getCurrentPlayer())) {
				log.debug("No valid attacks exist. Displaying options.");
				risk.setStage(RiskConstants.SETUP_TURN);
				risk.setStep(RiskConstants.SHOW_OPTIONS);
			}
			else {
				risk.setStage(RiskConstants.ATTACK);
				risk.setStep(RiskConstants.SELECT_ATTACKING_TERRITORY);
				risk.setDirections(RiskConstants.SELECT_TERRITORY_DIRECTIONS);
				log.debug("Attacking territory not satisfactory");
			}
			
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Called when a player has selected another player's territory to attack
	 * Corresponds to Stage ATTACK, Step SELECT_DEFENDING_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In selectDefendingTerritory()");

		boolean cancelled = Boolean.parseBoolean(request.getParameter("cancelled"));

		if (cancelled) {
			risk.setStage(RiskConstants.SETUP_TURN);
			risk.setStep(RiskConstants.SHOW_OPTIONS);
			risk.getAppController().forwardUpdatedVariables(request, response, risk);
			return;
		}
		if (request.getParameter("attackingArmyNum") == null) {
			risk.setStage(RiskConstants.ATTACK);
			risk.setStep(RiskConstants.SELECT_ATTACKING_TERRITORY);
			risk.setDirections(RiskConstants.SELECT_TERRITORY_DIRECTIONS);
			risk.getAppController().forwardUpdatedVariables(request, response, risk);
			return;
		}

		risk.getAttack().setAttackingArmyNum(Integer.parseInt(request.getParameter("attackingArmyNum")));

		int neighboringTerritoryId = Integer.parseInt(request.getParameter("neighboringTerritoryId"));
		Territory defendingTerritory = TerritoryUtil.getTerritoryFromNeighborById(
				risk.getAttack().getAttackingTerritory(), neighboringTerritoryId);

		risk.getAttack().setDefendingTerritory(defendingTerritory);
		log.debug("Defending territory: " + defendingTerritory);

		if (defendingTerritory.getNumberOfArmies() > 1) {
			log.debug("Changing step to SELECT_DEFENDING_ARMIES");
			risk.setStep(RiskConstants.SELECT_DEFENDING_ARMIES);
			request.setAttribute("defendingTerritory", defendingTerritory);
		} else {
			risk.getAttack().setDefendingArmyNum(1);
			log.debug("Changing stage to 5");
			risk.setStep(RiskConstants.DO_ATTACK);
			doAttack(request, response, risk);
			return;
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Called when the defending player has selected the number of armies to defend with
	 * Corresponds to Stage ATTACK, Step SELECT_DEFENDING_ARMIES
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingNumberOfArmies(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		risk.getAttack().setDefendingArmyNum(Integer.parseInt(request.getParameter("defendingArmyNum")));
		log.debug("Changing step to DO_ATTACK");
		risk.setStep(RiskConstants.DO_ATTACK);
		doAttack(request, response, risk);
		return;

	}

	/**
	 * Determines outcome of an attack by "rolling" dice and updating variables
	 * Corresponds to Stage ATTACK, Step DO_ATTACK
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doAttack(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In doAttack()");

		String attackResultsMessage;
		if (AppController.WIN_CASE) {
			attackResultsMessage = risk.getAttack().doBiasedAttack();
		}
		else {
			attackResultsMessage = risk.getAttack().doAttack();
		}
		log.debug(attackResultsMessage);

		request.setAttribute("attackingArmyDice", risk.getAttack().getAttackingArmyDice());
		request.setAttribute("defendingArmyDice", risk.getAttack().getDefendingArmyDice());
		request.setAttribute("attackResultsMessage", attackResultsMessage);

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Determines player's options after 
	 * Corresponds to Stage ATTACK, Step PROCESS_ATTACK
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processAttackRequest(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		if (risk.getAttack().defendingTerritoryIsConquered()) {
			risk.setMove(new Move(risk.getAttack().getAttackingTerritory(), risk.getAttack().getDefendingTerritory()));
			log.debug("Move: " + risk.getMove());
			risk.setDirections(RiskConstants.NO_DIRECTIONS);
			log.debug("Changing stage to MOVE and step to ATTACK_MOVE");
			risk.setStage(RiskConstants.MOVE_ARMIES);
			risk.setStep(RiskConstants.ATTACK_MOVE);
			risk.getAppController().forwardUpdatedVariables(request, response, risk);
		} else { // attacking territory was conquered or cannot transfer armies
			risk.setDirections(RiskConstants.NO_DIRECTIONS);
			risk.setStage(RiskConstants.SETUP_TURN);
			risk.setStep(RiskConstants.SHOW_OPTIONS);
			log.debug("Changing stage to SETUP_TURN and stage to SHOW_OPTIONS");
			turnController.determineNextMove(request, response, risk);
			return;
		}

	}


}
