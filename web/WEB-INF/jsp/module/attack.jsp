<%
	Territory attackingTerritory = risk.getAttack()
			.getAttackingTerritory();
	String territoryName = attackingTerritory.getTerritoryName();
	int minArmies = 1;
	int maxArmies = 1;
	if (attackingTerritory.getNumberOfArmies() > 3) {
		maxArmies = 3;
	} else {
		maxArmies = attackingTerritory.getNumberOfArmies() - 1;
	}
	ArrayList<Territory> neighboringTerritories = attackingTerritory
			.getNeighboringTerritories();
	directionsText = currentPlayer.getPlayerName() + ", click a teritory to attack with " + territoryName + ".";

%>
<form action="app" method="POST">
	<script type="text/javascript">
		$(function() {
			showalert(
		<%out.write("'" + directionsText + "'");%>
		, "alert-info");
		});
	</script>
		<%
			if (maxArmies > 1) {
		%>
		<p>Select number of armies to attack with</p>
		<span class="sliderContext minArmies"><%=minArmies%></span>
		<input type="text" name="attackingArmyNum" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
		<span class="sliderContext maxArmies"><%=maxArmies%></span>
		<hr />
		<%
			} else {
		%>
		<input type="hidden" name="attackingArmyNum" value="1" />
		<%
			}
		%>
		<%
			for (Territory neighboringTerritory : attackingTerritory
						.getNeighboringTerritories()) {
		%>
		<%
			if (!neighboringTerritory.getOwner().equals(currentPlayer)) {
		%>
		<input style="display:none;" type="radio" id="<%=neighboringTerritory.getTerritoryId()%>" name="neighboringTerritoryId" value="<%=neighboringTerritory.getTerritoryId()%>" checked>
		<%
			}
		%>
		<%
			}
		%>
		<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
		<input type="hidden" name="cancelled" value="false" />
		<input style="display:none;" id="attacksubmit" type="submit" class="btn btn-primary" value="Attack!" />
</form>
<form class="cancelAttack" action="app" method="POST">
	<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
	<input type="hidden" name="cancelled" value="true" /> <input type="submit" class="btn btn-danger" value="Cancel Attack" />
</form>