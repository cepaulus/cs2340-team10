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
		case 2: directionsText = currentPlayer.getPlayerName() + ", you have " 
								+ currentPlayer.getAvailableArmies() + " additional " 
								+ (currentPlayer.getAvailableArmies() > 1 ? "armies" : "army") + " to distribute.";
				break;
		case 3: directionsText = "Select a Territory to Attack from";
				break;
	}
%>

<% int stage = (Integer) request.getAttribute("stage"); %>
<% Territory attackingTerritory = (Territory) request.getAttribute("attackingTerritory"); %>


<html>
<head>
	<title>Game of Risk</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/app.css" /> 
	<link rel="stylesheet" type="text/css" href="css/slider.css" /> 
	<script type="text/javascript" src="js/jquery.min.js" ></script>
	<script type="text/javascript" src="js/bootstrap.min.js" ></script>
	<script type="text/javascript" src="js/bootstrap-slider.js" ></script>
	<script type="text/javascript">
	<% if (directionsList != 0 && stage != 5) { %>
		$(function() {
    		$('#directions').modal('show');
		});
	<% } %>
	<% if (stage == 5) { %>
		$(function() {
			//$('#attackDialog').modal('show');
			$('#attackDialog').modal({
  				keyboard : false,
  				show : true

			});
    		$('.slider').slider();
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

	<%

	String territoryName = "Not Available";
	int minArmies = 1;
	int maxArmies = 10;
	ArrayList<Territory> neighboringTerritories = currentPlayer.getTerritories();

	if (stage == 5) {
		territoryName = attackingTerritory.getTerritoryName();
		maxArmies = attackingTerritory.getNumberOfArmies();
		neighboringTerritories = attackingTerritory.getNeighboringTerritories();
	}

	%>

	<div id="attackDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="directionsLabel" aria-hidden="true">
		<div class="modal-header">
			<h3 id="directionsLabel">Attack a Territory</h3>
		</div>
		<form action="app" method="POST">
		<div class="modal-body">
			<h2><%= territoryName %></h2>
			<p>Select number of armies to attack with</p>
			<span class="sliderContext minArmies"><%= minArmies %></span>
			<input type="text" class="slider" value="" data-slider-min="<%= minArmies %>" data-slider-max="<%= maxArmies %>" data-slider-value="1">
			<span class="sliderContext maxArmies"><%= maxArmies %></span>
			<hr/>
			<p>Select the neighboring Territory to Attack</p>
		</div>
		<div class="modal-footer">
			<input type="submit" class="btn btn-primary" value="Attack!" /> 
		</form>
			<form class ="cancelAttack" action="app" method="POST">
				<input type="submit" class="btn btn-danger" value="Cancel Attack" /> 
			</form>
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
