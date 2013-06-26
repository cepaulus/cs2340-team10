<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% TerritoryServiceImpl territoryService = new TerritoryServiceImpl(); %>
<% ArrayList<Player> players = 
(ArrayList<Player>) request.getAttribute("players"); %>
<% Player currentPlayer = (Player) request.getAttribute("currentPlayer"); %>

<html>
<head>
	<title>Game of Risk</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/app.css" /> 
</head>
<body>



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
	<% for (int i = 0; i < players.size(); i++) { %>

		<div class="<% if (oddOffset && i == 0) out.write("offset1 "); out.write(span); %> player <% out.write("player" + i); %> <% if (currentPlayer.equals(players.get(i))) out.write("active"); %>">
			<% out.write(
			"<h3>" + players.get(i).getPlayerName()  + "</h3>"
		  + "<h4>" + players.get(i).getAvailableArmies() + " armies</h4>"); %>
		</div>

	<% } %>

</div>

<div class="row-fluid" id="map">

	<% for (int i = 0; i < players.size(); i++) { %>
		<% for (Territory territory : players.get(i).getTerritories()) { %>
			<div class="territory <% out.write("player" + i); %> <% out.write("territory" + territory.getTerritoryId()); %>">
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
