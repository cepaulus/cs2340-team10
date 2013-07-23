<%
	Territory attackingTerritory = risk.getAttack().getAttackingTerritory();
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
%>
<div
	id="attackDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="attackLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="attackLabel">Attack a Territory</h3>
	</div>
	<form
		action="app"
		method="POST"
	>
		<div class="modal-body">
			<h2><%=territoryName%></h2>
			<%
				if (maxArmies > 1) {
			%>
			<p>Select number of armies to attack with</p>
			<span class="sliderContext minArmies"><%=minArmies%></span> <input
				type="text"
				name="attackingArmyNum"
				class="slider"
				value="1"
				data-slider-min="<%=minArmies%>"
				data-slider-max="<%=maxArmies%>"
				data-slider-value="1"
			> <span class="sliderContext maxArmies"><%=maxArmies%></span>
			<hr />
			<%
				} else {
			%>
			<input
				type="hidden"
				name="attackingArmyNum"
				value="1"
			/>
			<%
				}
			%>
			<p>Select the neighboring Territory to Attack</p>
			<%
				for (Territory neighboringTerritory : attackingTerritory
							.getNeighboringTerritories()) {
			%>
			<%
				if (!neighboringTerritory.getOwner().equals(currentPlayer)) {
			%>
			<label class="radio neighboringTerritory<%=neighboringTerritory.getTerritoryId()%> owner<%=neighboringTerritory.getOwner().getPlayerId()%>"> <input
				type="radio"
				name="neighboringTerritoryId"
				value="<%=neighboringTerritory.getTerritoryId()%>"
				checked
			> <span><%=neighboringTerritory.getTerritoryName()%> (<%=neighboringTerritory.getNumberOfArmies()%>)</span>
			</label>
			<%
				}
			%>
			<%
				}
			%>
		</div>
		<div class="modal-footer">
			<input
				type="hidden"
				name="currentPlayerId"
				value="<%=currentPlayer.getPlayerId()%>"
			/> <input
				type="hidden"
				name="cancelled"
				value="false"
			/> <input
				type="submit"
				class="btn btn-primary"
				value="Attack!"
			/>
	</form>
	<form
		class="cancelAttack"
		action="app"
		method="POST"
	>
		<input
			type="hidden"
			name="currentPlayerId"
			value="<%=currentPlayer.getPlayerId()%>"
		/> <input
			type="hidden"
			name="cancelled"
			value="true"
		/> <input
			type="submit"
			class="btn btn-danger"
			value="Cancel Attack"
		/>
	</form>
</div>
</div>
