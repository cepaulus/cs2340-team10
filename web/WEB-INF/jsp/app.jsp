<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.util.*" %>
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

<tr><th>Players: </th></tr>
<% for (Player player : players){ %>
     <tr><td> <% out.write(player.getPlayerId() + " - " + player.getPlayerName() + " has  " + player.getNumberOfArmies() + " armies."); %> <span id=<%out.write("player"+player.getPlayerId());%>> &#9679;</span>  </td></tr>
<% } %>

<tr><th>Current player: 

<% switch (currentPlayer.getPlayerId()) {
case (1): %><span id="player1"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (2): %><span id="player2"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (3): %><span id="player3"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (4): %><span id="player4"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (5): %><span id="player5"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
case (6): %><span id="player6"><%out.write(currentPlayer.getPlayerName());%></span><% break; 
}%> 
</th></tr>

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
<% if (PlayerUtil.getPlayerById(players, 1).getTerritories().contains(territory)) { %>
	<div id="player1">
<% } %>
<% if (PlayerUtil.getPlayerById(players, 2).getTerritories().contains(territory)) { %>
	<div id="player2">
<% } %>
<% if (PlayerUtil.getPlayerById(players, 3).getTerritories().contains(territory)) { %>
	<div id="player3">
<% } %>
<% if ( players.size() >= 4 && PlayerUtil.getPlayerById(players, 4).getTerritories().contains(territory) ) { %>
	<div id="player4">
<% } %>
<% if ( players.size() >= 5 && PlayerUtil.getPlayerById(players, 5).getTerritories().contains(territory) ) { %>
	<div id="player5">
<% } %>
<% if ( players.size() >= 6 && PlayerUtil.getPlayerById(players, 6).getTerritories().contains(territory) ) { %>
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
