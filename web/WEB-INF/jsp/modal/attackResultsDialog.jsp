<%
	int[] attackingArmyDice = (int[]) request
			.getAttribute("attackingArmyDice");
	int[] defendingArmyDice = (int[]) request
			.getAttribute("defendingArmyDice");
	String attackResultsMessage = (String) request
			.getAttribute("attackResultsMessage");
%>
<div
	id="attackResultsDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="attackResultsLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="attackResultsLabel">Attack Results</h3>
	</div>
	<div class="modal-body">
		<!--<h2>Attacker Rolled</h2>-->
		<div class="row attackRolls dice">
			<%
				for (int dieValue : attackingArmyDice) {
			%>
					<div class="value<%=dieValue%>"></div>
			<%
				}
			%>
		</div>
		<!--<h2>Defender Rolled</h2>-->
		<div class="row defenceRolls dice">
			<%
				for (int dieValue : defendingArmyDice) {
			%>
					<div class="value<%=dieValue%>"></div>
			<%
				}
			%>
		</div>
	</div>
	<div class="modal-footer">
		<h5 id="attackResultsMessage"><%=attackResultsMessage%></h5>
		<form
			method="POST"
			action="app"
		>
			<input
				type="submit"
				class="btn btn-primary"
				value="Continue"
			/>
		</form>
	</div>
</div>
