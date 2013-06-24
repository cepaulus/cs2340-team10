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

    <div class="text-center">
<h1>Game of Risk</h1>

<div class="table-container"><div class="container-display">
<table id="player-container">
<tr>
<th class="adjust-padding-strong">Players: </th>


	<%-- Write Current Player heading --%>
<th class="adjust-padding">Current player: 
<span id=<%out.write("player"+currentPlayer.getRollOrder());%>>
	<%out.write(currentPlayer.getPlayerName());%></span> 

</th>

</tr>
<tr>
	<%-- Write Players and roll order --%>
<% for (Player player : players){ %>
     <tr><td class="adjust-padding-strong"> <% out.write(player.getRollOrder() + ". " + player.getPlayerName() 
    		 + " - " + player.getNumberOfArmies() + " armies"); %> 
     <span id=<%out.write("player"+player.getRollOrder());%>> &#9679;</span>  </td></tr>
<% } %>

</table></div>
<div class="container-display"><table>
<tr><th class="adjust-padding">Current territory: </th></tr>
<tr><th class="adjust-padding">Territory owner: </th></tr>
<tr><th class="adjust-padding">Number of armies: </th></tr>
<tr><th class="adjust-padding">Neighboring territories: </th></tr>
</table></div>
</div>

<br>
<table>

	<%-- Write Country names --%>
<% for (Country country : countries) { %>
<tr><th> <% out.write(country.getCountryName() + ":"); %> <br></th>

	<%-- Write Territories next to their respective countries --%>
<% for (Territory territory : territoryMap.get(country.getCountryId())) { %>
<td class="territory-block">
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
<% if ( players.size() == 6 && players.get(5).getTerritories().contains(territory) ) { %>
	<div id="player6">
<% } %>
  <form action="app" method="POST">
    <input type="hidden" name="operation" value="POST"/>
   <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
   <input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>"/>
    <a href="javascript:;" onclick="parentNode.submit();">
    	<%=territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")"%></a>
   </form>
   </div>
<% } %>
</td>
</tr>
<% } %>
</table></div>
</body>
</html>