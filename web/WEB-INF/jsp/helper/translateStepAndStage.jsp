<%
int show = 6;
// 0 = none
// 1 = current players territories
// 2 = territories that can attack
// 3 = territories that can be attacked
// 4 = territories that can transfer
// 5 = territories that can accept transfers
// 6 = all
switch (risk.getStage()) {
	case RiskConstants.INITIALIZE:
		show = 1;
		break;
	case RiskConstants.SETUP_TURN: 
		switch (risk.getStep()) {
			case RiskConstants.BEFORE_TURN:
				show = 1;
				break;
			case RiskConstants.BEGINNING_OF_TURN:
				show = 1;
				break;
			case RiskConstants.SHOW_OPTIONS:
				//moduleName = "options";
				show = 0;
				break;
		}
		break;
	case RiskConstants.ATTACK: 
		switch (risk.getStep()) {
			case RiskConstants.SELECT_ATTACKING_TERRITORY:
				show = 2;
				break;
			case RiskConstants.SELECT_DEFENDING_TERRITORY:
				//moduleName = "attack";
				show = 3;
				break;
			case RiskConstants.SELECT_DEFENDING_ARMIES:
				//moduleName = "defendingArmyNum";
				show = 0;
				break;
			case RiskConstants.DO_ATTACK:
				//moduleName = "attackResults";
				show = 0;
				break;
		}
		break;
	case RiskConstants.MOVE_ARMIES: 
		switch (risk.getStep()) {
			case RiskConstants.SELECT_SOURCE_TERRITORY:
				show = 4;
				break;
			case RiskConstants.ATTACK_MOVE:
				//moduleName = "attackMove";
				show = 0;
				break;
			case RiskConstants.SELECT_DESTINATION_TERRITORY:
				show = 5;
				//moduleName = "fortify";
				break;
		}
		break;
	case RiskConstants.DECLARE_WINNER: 
		//moduleName = "winner";
		break;
} 
%>