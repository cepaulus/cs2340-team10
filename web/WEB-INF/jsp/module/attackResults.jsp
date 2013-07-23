<%
	int[] attackingArmyDice = (int[]) request
			.getAttribute("attackingArmyDice");
	int[] defendingArmyDice = (int[]) request
			.getAttribute("defendingArmyDice");
	String attackResultsMessage = (String) request
			.getAttribute("attackResultsMessage");
	directionsText = "Attack Results: " + attackResultsMessage;
	String alerttype = "alert-info";
	if(attackResultsMessage.equals("Attack Successful! Territory acquired.")) {
		alerttype = "alert-success";
	}
%>
<script type="text/javascript">
	$(function() {
		showalert(
	<%out.write("'" + directionsText + "'");%>
	, "<%=alerttype%>");
	});
</script>
<p>Attacker Rolled</p>
<div class="row attackRolls dice">
	<%
		for (int dieValue : attackingArmyDice) {
	%>
	<div class="value<%=dieValue%>"></div>
	<%
		}
	%>
</div>
<p>Defender Rolled</p>
<div class="row defenceRolls dice">
	<%
		for (int dieValue : defendingArmyDice) {
	%>
	<div class="value<%=dieValue%>"></div>
	<%
		}
	%>
</div>
<hr />
<form method="POST" action="app">
	<input type="submit" class="btn btn-primary" value="Continue" />
</form>