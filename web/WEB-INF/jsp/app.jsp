<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% TerritoryServiceImpl territoryService = new TerritoryServiceImpl(); %>
<% ArrayList<Player> players = 
	(ArrayList<Player>) request.getAttribute("players"); %>
<% Player currentPlayer = (Player) request.getAttribute("currentPlayer"); %>
<% ArrayList<Country> countries = 
	(ArrayList<Country>) request.getAttribute("countries"); %>
<% HashMap<Integer, ArrayList<Territory>> territoryMap = 
	(HashMap<Integer, ArrayList<Territory>>) request.getAttribute("territoryMap"); %>

<html>
	<head>
		<title>Game of Risk</title>
		<link rel="stylesheet" type="text/css" href="css/app.css" /> 
	    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	</head>
<body>

      <div class="span12 text-center">
<h1>Game of Risk</h1>

<table>
<tr>
<th>Players: </th>

<td></td><td></td><td></td>
<% System.out.println("Player name: " + currentPlayer.getPlayerName()); %>
<th>Current player: 
<% switch (currentPlayer.getRollOrder()) {
case (1): %><span id="player1"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (2): %><span id="player2"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (3): %><span id="player3"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (4): %><span id="player4"><%currentPlayer.getPlayerName();%></span><% break; 
case (5): %><span id="player5"><%currentPlayer.getPlayerName();%></span><% break; 
case (6): %><span id="player6"><%currentPlayer.getPlayerName();%></span><% break; 
} %>
</th>

</tr>
<tr>

 <td><% out.write("1. " + players.get(0).getPlayerName()  
		 + " - " + players.get(0).getNumberOfArmies() + " armies"); %>
	<span id="player1">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("2. " + players.get(1).getPlayerName()  
		 + " - " + players.get(1).getNumberOfArmies() + " armies"); %>
	<span id="player2">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("3. " + players.get(2).getPlayerName()  
		 + " - " + players.get(2).getNumberOfArmies() + " armies"); %>
	<span id="player3">&#8226;</span>
</td></tr>
<% if (players.size() >= 4) { %>
 <tr><td><% out.write("4. " + players.get(3).getPlayerName()  
		 + " - " + players.get(3).getNumberOfArmies() + " armies"); %>
	<span id="player4">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() >= 5) { %>
 <tr><td><% out.write("5. " + players.get(4).getPlayerName()  
		 + " - " + players.get(4).getNumberOfArmies() + " armies"); %>
	<span id="player5">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() == 6) { %>
 <tr><td><% out.write("6. " + players.get(5).getPlayerName()  
		 + " - " + players.get(5).getNumberOfArmies() + " armies"); %>
	<span id="player6">&#8226;</span>
</td></tr>
<% } %>

</table>
<br>
<table>
<tr>
<% for (Country country : countries) { %>
<th> 
<% out.write(country.getCountryName() + ":"); %>
<br></th>
<% for (Territory territory : territoryMap.get(country.getCountryId())) { %>

<td>
<% if (players.get(0).getTerritories().contains(territory)) { %>
	<div id="player1">
<% } %>
<% if (players.get(1).getTerritories().contains(territory)) { %>
	<div id="player2">
<% } %>
<% if (players.get(2).getTerritories().contains(territory)) { %>
	<div id="player3">
<% } %>
<% if ( players.size() >= 4 && players.get(3).getTerritories().contains(territory) ) { %>
	<div id="player4">
<% } %>
<% if ( players.size() >= 5 && players.get(4).getTerritories().contains(territory) ) { %>
	<div id="player5">
<% } %>
<% if ( players.size() >= 6 && players.get(5).getTerritories().contains(territory) ) { %>
	<div id="player6">
<% } %>
  <form action="app" method="POST">
    <input type="hidden" name="operation" value="POST"/>
   <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
   <input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>"/>
    <input class="territoryButton" type="submit" 
    	value="<%=territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")"%>"/>
   </form>
   </div>
<% } %>
</td>
<td></td>
</tr>
<% } %>
</table>
</div>
</body>
</html>
