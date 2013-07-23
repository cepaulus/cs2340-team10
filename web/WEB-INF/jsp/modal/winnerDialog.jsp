<%
	String winnerName = risk.getPlayers().get(0).getPlayerName();
%>
<div
	id="winnerDialog"
	class="modal hide fade"
	tabindex="-1"
	role="dialog"
	aria-labelledby="winnerLabel"
	aria-hidden="true"
	data-backdrop="static"
>
	<div class="modal-header">
		<h3 id="winnerLabel">Global Domination!</h3>
	</div>
	<div class="modal-body">
		<h2><%="Winner: " + winnerName%></h2>
		<form
			method="POST"
			action="app"
		>
	</div>
	<div class="modal-footer">
		<input
			type="submit"
			class="btn btn-primary"
			value="Okay"
		/>
		</form>
	</div>
</div>
