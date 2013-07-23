package main.java.edu.gatech.cs2340.risk.util;

public interface RiskConstants {
	
	// stages
	public static final int INITIALIZE = 1;
	public static final int SETUP_TURN = 2;
	public static final int ATTACK = 3;
	public static final int MOVE_ARMIES = 4;
	public static final int DECLARE_WINNER = 5;
	public static final int GAME_OVER = 0;
	
	public static final int NO_STEP = 0;
	
	// steps corresponding to stage 2 (setup turn)
	public static final int BEFORE_TURN = 1;
	public static final int BEGINNING_OF_TURN = 2;
	public static final int SHOW_OPTIONS = 3;
	
	// steps corresponding to stage 3 (attack)
	public static final int SELECT_ATTACKING_TERRITORY = 1;
	public static final int SELECT_DEFENDING_TERRITORY = 2;
	public static final int SELECT_DEFENDING_ARMIES = 3;
	public static final int DO_ATTACK = 4;
	public static final int PROCESS_ATTACK = 5;
	
	// steps corresponding to stage 4 (move armies)
	public static final int SELECT_SOURCE_TERRITORY = 1;
	public static final int SELECT_DESTINATION_TERRITORY = 2;
	public static final int ATTACK_MOVE = 3;
	public static final int DO_MOVE = 4;

	// direction numbers
	public static final int NO_DIRECTIONS = 0;
	public static final int INITIAL_DIRECTIONS = 1;
	public static final int ADDITIONAL_ARMIES_DIRECTIONS = 2;
	public static final int SELECT_TERRITORY_DIRECTIONS = 3;
	public static final int SELECT_SOURCE_DIRECTIONS = 4;
}
