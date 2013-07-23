<%
	String territoryName = risk.getAttack().getDefendingTerritory().getTerritoryName();
	int minArmies = 1;
	int maxArmies = 2;
%>
<div
	id="defendingArmyNumDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="defendingArmyNumLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="defendingArmyNumLabel">Select Number of Armies</h3>
	</div>
	<div class="modal-body">
		<h2><%=territoryName%></h2>
		<form
			method="POST"
			action="app"
		>
			<p>Select number of armies to defend with</p>
			<span class="sliderContext minArmies"><%=minArmies%></span> <input
				type="text"
				name="defendingArmyNum"
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

