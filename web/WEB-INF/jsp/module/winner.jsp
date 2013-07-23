<%
	String winnerName = risk.getPlayers().get(0).getPlayerName();
	directionsText = risk.getPlayers().get(0).getPlayerName() + ", you won!";
%>
<script type="text/javascript">
	$(function() {
		showalert(
	<%out.write("'" + directionsText + "'");%>
	, "alert-sucess");
	});
</script>
<h3>Global Domination!</h3>
<form method="POST" action="app">
</form>