<%@ page import="main.java.edu.gatech.cs2340.risk.model.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>

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
<% Territory defendingTerritory = (Territory) request.getAttribute("defendingTerritory"); %>


<html>
<head>
<title>Game of Risk</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/app.css" />
<link rel="stylesheet" type="text/css" href="css/slider.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-slider.js"></script>
<script type="text/javascript">
	<% if (!( stage > 3) && (directionsList != 0 )) { %>
		$(function() {
    		$('#directions').modal('show');
		});
	<% } %>
	<% if (stage == 4) { %>
		$(function() {
			$('#attackDialog').modal({
  				keyboard : false,
  				show : true

			});
    		$('.slider').slider();
		});
	<% } %>
	<% if (stage == 6) { %>
		$(function() {
			$('#defendingArmyNumDialog').modal({
  				keyboard : false,
  				show : true

			});
			$('.slider').slider();
		});
	<% } %>
	<% if (stage == 5) { %>
		$(function() {
			$('#attackResultsDialog').modal({
  				keyboard : false,
  				show : true

			});
		});
	<% } %>
	<% if (stage == 7) { %>
		$(function() {
			$('#optionsDialog').modal({
  				keyboard : false,
  				show : true

			});
		});
	<% } %>

	</script>
</head>
<body>

	<div id="directions" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="directionsLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">x</button>
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

	if (stage == 4) {
		String territoryName = attackingTerritory.getTerritoryName();
		int minArmies = 1;
		int maxArmies = attackingTerritory.getNumberOfArmies() - 1;
		ArrayList<Territory> neighboringTerritories = attackingTerritory.getNeighboringTerritories();

	%>

	<div id="attackDialog" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="attackLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-header">
			<h3 id="attackLabel">Attack a Territory</h3>
		</div>
		<form action="app" method="POST">
			<div class="modal-body">
				<h2><%= territoryName %></h2>
				<% if (maxArmies > 1) { %>
				<p>Select number of armies to attack with</p>
				<span class="sliderContext minArmies"><%= minArmies %></span> <input
					type="text" name="attackingArmyNum" class="slider" value="1"
					data-slider-min="<%= minArmies %>"
					data-slider-max="<%= maxArmies %>" data-slider-value="1"> <span
					class="sliderContext maxArmies"><%= maxArmies %></span>
				<hr />
				<% } else { %>
				<input type="hidden" name="attackingArmyNum" value="1" />
				<% } %>

				<p>Select the neighboring Territory to Attack</p>

				<% for (Territory neighboringTerritory : attackingTerritory.getNeighboringTerritories()) { %>
				<% if (!neighboringTerritory.getOwner().equals(currentPlayer)) { %>
				<label
					class="radio neighboringTerritory<%= neighboringTerritory.getTerritoryId() %> owner<%= neighboringTerritory.getOwner().getPlayerId() %>">
					<input type="radio" name="neighboringTerritoryId"
					value="<%= neighboringTerritory.getTerritoryId() %>" checked>
					<span><%= neighboringTerritory.getTerritoryName() %> (<%= neighboringTerritory.getNumberOfArmies() %>)</span>
				</label>
				<% } %>
				<% } %>

			</div>
			<div class="modal-footer">
				<input type="hidden" name="currentPlayerId"
					value="<%=currentPlayer.getPlayerId()%>" /> <input type="hidden"
					name="cancelled" value="false" /> <input type="submit"
					class="btn btn-primary" value="Attack!" />
		</form>
		<form class="cancelAttack" action="app" method="POST">
			<input type="hidden" name="currentPlayerId"
				value="<%=currentPlayer.getPlayerId()%>" /> <input type="hidden"
				name="cancelled" value="true" /> <input type="submit"
				class="btn btn-danger" value="Cancel Attack" />
		</form>
	</div>
	</div>

	<% } %>

	<% 
		if (stage == 5) {
			int[] attackingArmyDice = (int[])request.getAttribute("attackingArmyDice");
			int[] defendingArmyDice = (int[])request.getAttribute("defendingArmyDice");
			String attackResultsMessage = (String)request.getAttribute("attackResultsMessage");
	%>

	<div id="attackResultsDialog" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="attackResultsLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-header">
			<h3 id="attackResultsLabel">Attack Results</h3>
		</div>
		<div class="modal-body">
			<!--<h2>Attacker Rolled</h2>-->
			<div class="row attackRolls dice">
				<% for (int dieValue : attackingArmyDice) { %>
				<div class="value<%= dieValue %>"></div>
				<% } %>
			</div>
			<!--<h2>Defender Rolled</h2>-->
			<div class="row defenceRolls dice">
				<% for (int dieValue : defendingArmyDice) { %>
				<div class="value<%= dieValue %>"></div>
				<% } %>
			</div>
		</div>
		<div class="modal-footer">
			<h4 id="attackResultsMessage"><%= attackResultsMessage %></h4>
			<form method="POST" action="app">
				<input type="submit" class="btn btn-primary" value="Continue" />
			</form>
		</div>
	</div>

	<% } %>

	<% 
	if (stage == 6) {
		String territoryName = defendingTerritory.getTerritoryName();
		int minArmies = 1;
		int maxArmies = defendingTerritory.getNumberOfArmies();
	%>

	<div id="defendingArmyNumDialog" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="defendingArmyNumLabel"
		aria-hidden="true" data-backdrop="static">
		<div class="modal-header">
			<h3 id="defendingArmyNumLabel">Attack Results</h3>
		</div>
		<div class="modal-body">
			<h2><%= territoryName %></h2>
			<form method="POST" action="app">
				<p>Select number of armies to defend with</p>
				<span class="sliderContext minArmies"><%= minArmies %></span> <input
					type="text" name="defendingArmyNum" class="slider"
					value="<%= maxArmies %>" data-slider-min="<%= minArmies %>"
					data-slider-max="<%= maxArmies %>"
					data-slider-value="<%= maxArmies %>"> <span
					class="sliderContext maxArmies"><%= maxArmies %></span>
		</div>
		<div class="modal-footer">
			<input type="submit" class="btn btn-primary" value="Continue" />
			</form>
		</div>
	</div>

	<% } %>

	<% 
	if (stage == 7) {
	%>

	<div id="optionsDialog" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="optionsLabel" aria-hidden="true"
		data-backdrop="static">
		<div class="modal-header">
			<h3 id="optionsLabel">Select Your Next Move</h3>
		</div>
		<div class="modal-body">
			<% if (TerritoryUtil.canAttack(currentPlayer)) {  %>
			<form method="POST" action="app">
				<input type="hidden" name="option" value="attack" /> <input
					type="submit" class="optionBtn btn btn-large btn-primary "
					value="Attack">
			</form>
			<% } else { %>
			<a href="#" class="optionBtn btn btn-large btn-primary disabled">Attack</a>
			<% } %>
			<form method="POST" action="app">
				<input type="hidden" name="option" value="fortify" /> <input
					type="submit" class="optionBtn btn btn-large btn-success"
					value="Fortify">
			</form>
			<form method="POST" action="app">
				<input type="hidden" name="option" value="end turn" /> <input
					type="submit" class="optionBtn btn btn-large btn-danger"
					value="End Turn">
			</form>
		</div>
	</div>

	<% } %>

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

			<div
				class="<% if (oddOffset) out.write("offset1 "); out.write(span); %> player <% out.write("player" + (player.getPlayerId())); %> <% if (currentPlayer.equals(player)) out.write("active"); %>">
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
			<div
				class="territory <% out.write("player" + (player.getPlayerId())); %> <% out.write("territory" + territory.getTerritoryId()); %>">
				<form action="app" method="POST">
					<input type="hidden" name="operation" value="POST" /> <input
						type="hidden" name="territoryId"
						value="<%= territory.getTerritoryId() %>" /> <input type="hidden"
						name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
					<input class="territoryButton btn btn-link" type="submit"
						value="<%= territory.getNumberOfArmies() %>" />
				</form>
			</div>
			<% }
	} %>
		</div>

	</div>
</body>
</html>