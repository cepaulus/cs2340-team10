<%
	Boolean hasFortified = (Boolean) request.getAttribute("hasFortified");
%>
<div
	id="optionsDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="optionsLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="optionsLabel">Select Your Next Move</h3>
	</div>
	<div class="modal-body">
		<%
			if (TerritoryUtil.canAttack(currentPlayer) && (hasFortified == null || !hasFortified)) {
		%>
		<form
			method="POST"
			action="app"
		>
			<input
				type="hidden"
				name="option"
				value="attack"
			/> <input
				type="submit"
				class="optionBtn btn btn-large btn-primary "
				value="Attack"
			>
		</form>
		<%
			} else {
		%>
		<a
			href="#"
			class="optionBtn btn btn-large btn-primary disabled"
		>Attack</a>
		<%
			}
		%>
		<form
			method="POST"
			action="app"
		>
			<input
				type="hidden"
				name="option"
				value="fortify"
			/> <input
				type="submit"
				class="optionBtn btn btn-large btn-success"
				value="Fortify"
			>
		</form>
		<form
			method="POST"
			action="app"
		>
			<input
				type="hidden"
				name="option"
				value="end turn"
			/> <input
				type="submit"
				class="optionBtn btn btn-large btn-danger"
				value="End Turn"
			>
		</form>
	</div>
</div>
