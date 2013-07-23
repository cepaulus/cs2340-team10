<%
	String territoryName = risk.getMove().getSource().getTerritoryName();
	String destinationName;
	if (risk.getMove().getDestination() != null) {
		destinationName = risk.getMove().getDestination().getTerritoryName();
	}
	else {
		destinationName = risk.getAttack().getDefendingTerritory().getTerritoryName();
	}
	int minArmies = 1;
	int maxArmies = risk.getMove().getSource().getNumberOfArmies() - 1;
%>
<div
	id="movingArmyNumDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="movingArmyNumLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="movingArmyNumLabel">Select Number of Armies</h3>
	</div>
	<div class="modal-body">
		<h2><%=territoryName + " to " + destinationName%></h2>
		<form
			method="POST"
			action="app"
		>
			<p>Select number of armies to move to destination</p>
			<span class="sliderContext minArmies"><%=minArmies%></span> <input
				type="text"
				name="numArmies"
				class="slider"
				value="<%=maxArmies%>"
				data-slider-min="<%=minArmies%>"
				data-slider-max="<%=maxArmies%>"
				data-slider-value="<%=maxArmies%>"
			> <span class="sliderContext maxArmies"><%=maxArmies%></span>
	</div>
	<div class="modal-footer">
		<input
			type="submit"
			class="btn btn-primary"
			value="Continue"
		/>
		</form>
	</div>
</div>
