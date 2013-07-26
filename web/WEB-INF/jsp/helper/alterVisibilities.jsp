<%

boolean drawn;
switch (show) {
	case 0:%>
		<input class="territoryButton btn btn-link"
			type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%break;
	case 1:
		if(player == currentPlayer) {%>
			<input class="territoryButton btn btn-link" type="submit"
				value="<%=territory.getNumberOfArmies()%>" />
		<%} else {%>
			<input class="territoryButton btn btn-link"
				type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%}
		break;
	case 2:
		if(player == currentPlayer && TerritoryUtil.validAttackTerritory(territory)) {%>
			<input class="territoryButton btn btn-link" type="submit"
				value="<%=territory.getNumberOfArmies()%>" />
		<%} else {%>
			<input class="territoryButton btn btn-link"
				type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%}
		break;
	case 3:
		Territory attackingTerritory = risk.getAttack().getAttackingTerritory();
		drawn = false;
		for (Territory neighboringTerritory : attackingTerritory.getNeighboringTerritories()) {
			if (!neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
				<input
					onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('attacksubmit').click()"
					class="territoryButton btn btn-link" type="button"
					value="<%=territory.getNumberOfArmies()%>"
				>
				<%drawn = true;
			}
		}
		if(!drawn) {%>
			<input class="territoryButton btn btn-link"
				type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%}
		break;
	case 4:
		if(player == currentPlayer && TerritoryUtil.validFortifyTerritory(territory)) {%>
			<input class="territoryButton btn btn-link" type="submit"
				value="<%=territory.getNumberOfArmies()%>" />
		<%} else {%>
			<input class="territoryButton btn btn-link"
				type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%}
		break;
	case 5:
		Territory source = risk.getMove().getSource();
		drawn = false;
		for (Territory neighboringTerritory : source.getNeighboringTerritories()) {
			if (neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
				<input
					onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('fortifysubmit').click()"
					class="territoryButton btn btn-link" type="button"
					value="<%=territory.getNumberOfArmies()%>"
				>
				<%drawn = true;
			}
		}
		if(!drawn) {%>
			<input class="territoryButton btn btn-link"
				type="submit" value="<%=territory.getNumberOfArmies()%>" />
		<%}
		break;
	case 6:%>
		<input class="territoryButton btn btn-link" type="submit"
			value="<%=territory.getNumberOfArmies()%>" />
		<%break;
	}%>