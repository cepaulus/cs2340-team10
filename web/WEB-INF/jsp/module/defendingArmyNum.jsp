<%
	String territoryName = risk.getAttack().getDefendingTerritory().getTerritoryName();
	String attackingTerritoryName = risk.getAttack().getAttackingTerritory().getTerritoryName();
	int minArmies = 1;
	int maxArmies = 2;
	directionsText = risk.getAttack().getDefendingTerritory().getOwner().getPlayerName() + ", " + territoryName + " is being attacked by " + attackingTerritoryName + ".";
%>
<script type="text/javascript">
	$(function() {
		showalert(
	<%out.write("'" + directionsText + "'");%>
	, "alert-info");
	});
</script>
<form method="POST" action="app">
	<p>Select number of armies to defend with</p>
	<span class="sliderContext minArmies"><%=minArmies%></span>
	<input type="text" name="defendingArmyNum" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
	<span class="sliderContext maxArmies"><%=maxArmies%></span>
<hr />
<input type="submit" class="btn btn-primary" value="Continue" />
</form>
