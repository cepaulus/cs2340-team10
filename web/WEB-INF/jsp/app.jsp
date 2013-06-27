<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% TerritoryServiceImpl territoryService = new TerritoryServiceImpl(); %>
<% ArrayList<Player> players = 
(ArrayList<Player>) request.getAttribute("players"); %>
<% Player currentPlayer = (Player) request.getAttribute("currentPlayer"); %>

<% Integer directionsList = (Integer) request.getAttribute("directionsList"); %>
<%  
	String directionsText = "";
	switch (directionsList) {
		case 0: break;
		case 1: directionsText = "Click on a Territory of Your Color to add one Army to it.";
				break;
	}
%>

<% int stage = (Integer) request.getAttribute("stage"); %>
<% Territory attackingTerritory = (Territory) request.getAttribute("attackingTerritory"); %>
<% Territory defendingTerritory = (Territory) request.getAttribute("defendingTerritory"); %>
<% String message = (String) request.getAttribute("message"); %>


<html>
<head>
	<title>Game of Risk</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/app.css" /> 
	<script type="text/javascript" src="js/jquery.min.js" ></script>
	<script type="text/javascript" src="js/bootstrap.min.js" ></script>
	<script type="text/javascript">
	<% if (directionsList != 0) { %>
		$(function() {
    		$('#directions').modal('show');
		});
	<% } %>
	</script>
</head>
<body>

	<div id="directions" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="directionsLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
			<h3 id="directionsLabel">Directions</h3>
		</div>
		<div class="modal-body">
			<p id="directions-body"><%= directionsText %></p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		</div>
	</div>

<div id="wrap" class="container-fluid">

<!-- WRITE PLAYERS IN ROLL ORDER -->

<div class="row-fluid text-center" id="players">
	<% 
		String span = "span" + (12/players.size());
		boolean oddOffset = false;
		
		if (players.size()%2 != 0) {
			span = "span" + (10/players.size());
			oddOffset = true;

		}

	%>
	<% for (Player player : players) { %>

		<div class="<% if (oddOffset) out.write("offset1 "); out.write(span); %> player <% out.write("player" + (player.getPlayerId()-1)); %> <% if (currentPlayer.equals(player)) out.write("active"); %>">
			<% out.write(
			"<h3>" + player.getPlayerName()  + "</h3>"
		  + "<h4>" + player.getAvailableArmies() + " armies</h4>"); %>
		</div>

		<% oddOffset = false; %>

	<% } %>

</div>
<!-- This can be "cleaned up" at some point but I had wanted to get something down to test with -->
<% if (stage == 1) { // JULIAN!! make this pretty :) %>
<div id="temp-display-box"> <!-- TEMPORARY DISPLAY BECAUSE I'M NOT GOOD AT CSS -->
 <% out.write(message); %>
</div>
<% } %>

<% if (stage == 2) { // this one too! %>
<div id="temp-display-box"> <!-- TEMPORARY DISPLAY BECAUSE I'M NOT GOOD AT CSS -->
<% out.write(message); %>
</div>
<% } %>

<% if (stage == 3) { 
	// and this one!  maybe similar to http://www.game-remakes.com/play.php?id=476 ?  %>
<div id="temp-display-box"> <!-- TEMPORARY DISPLAY BECAUSE I'M NOT GOOD AT CSS -->
 <% out.write(message); %>
</div>
<% } %>

<% if (stage == 4) { %>
<div id="temp-display-box"> <!-- TEMPORARY DISPLAY BECAUSE I'M NOT GOOD AT CSS -->
 <% out.write(message); %>
</div>
<% } %>

<% if (stage == 5) { %>
<div id="temp-display-box"> <!-- TEMPORARY DISPLAY BECAUSE I'M NOT GOOD AT CSS -->
 <% out.write("this shouldn't be happening"); // this hasn't been written yet %>
</div>
<% } %>

<div class="row-fluid" id="map">

	<% for (Player player : players) { %>
		<% for (Territory territory : player.getTerritories()) { %>
			<div class="territory <% out.write("player" + (player.getPlayerId()-1)); %> <% out.write("territory" + territory.getTerritoryId()); %>">
				<form action="app" method="POST">
					<input type="hidden" name="operation" value="POST"/>
					<input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
					<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>"/>
					<input class="territoryButton btn btn-link" type="submit" value="<%= territory.getNumberOfArmies() %>"/>
				</form>
			</div>
	<% }
	} %>
</div>

</div>
</body>
</html>
