<%
	if (TerritoryUtil.canAttack(currentPlayer)) {
%>
<form method="POST" action="app">
	<input type="hidden" name="option" value="attack" /> <input type="submit" class="optionBtn btn btn-large btn-primary " value="Attack">
</form>
<%
	}
%>
<%
	if (TerritoryUtil.canFortify(currentPlayer)) {
%>
<form method="POST" action="app">
	<input type="hidden" name="option" value="fortify" /> <input type="submit" class="optionBtn btn btn-large btn-success" value="Fortify">
</form>
<%
	}
%>
<form method="POST" action="app">
	<input type="hidden" name="option" value="end turn" /> <input type="submit" class="optionBtn btn btn-large btn-danger" value="End Turn">
</form>