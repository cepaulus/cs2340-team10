<%
	Territory source = risk.getMove().getSource();
	String territoryName = source.getTerritoryName();
	int minArmies = 1;
	int maxArmies = source.getNumberOfArmies() - 1;
	ArrayList<Territory> neighboringTerritories = source .getNeighboringTerritories();
	directionsText = currentPlayer.getPlayerName() + ", click a teritory to fortify with " + territoryName + ".";
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
		<p>Select number of armies to fortify with</p>
		<span class="sliderContext minArmies"><%=minArmies%></span>
		<input type="text" name="numArmies" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
		<span class="sliderContext maxArmies"><%=maxArmies%></span>
		<hr />
		<%
			} else {
		%>
		<input type="hidden" name="numArmies" value="1" />
		<%
			}
		%>
		<%
			for (Territory neighboringTerritory : source
						.getNeighboringTerritories()) {
		%>
		<%
			if (neighboringTerritory.getOwner().equals(currentPlayer)) {
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
		<input style="display:none;" type="submit" id="fortifysubmit" class="btn btn-primary" value="Fortify" />
</form>
<form class="cancelFortify" action="app" method="POST">
	<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
	<input type="hidden" name="cancelled" value="true" />
	<input type="submit" class="btn btn-danger" value="Cancel Fortify" />
</form>