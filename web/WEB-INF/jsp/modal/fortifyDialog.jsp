<%
	Territory source = risk.getMove().getSource();
%>
<div
	id="fortifyDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="fortifyLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="fortifyLabel">Select a Territory to Fortify</h3>
	</div>
	<form
		action="app"
		method="POST"
	>
		<div class="modal-body">
			<p>Select a Neighboring Territory to Fortify:</p>
			<%
				for (Territory neighboringTerritory : source
							.getNeighboringTerritories()) {
			%>
			<%
				if (neighboringTerritory.getOwner().equals(currentPlayer)) {
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
				value="Fortify"
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
			value="Cancel"
		/>
	</form>
	</div>
</div>