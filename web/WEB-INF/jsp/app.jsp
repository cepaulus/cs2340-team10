<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% TerritoryServiceImpl territoryService = new TerritoryServiceImpl(); %>
<% ArrayList<Player> players = 
	(ArrayList<Player>) request.getAttribute("players"); %>
<% ArrayList<Country> countries = 
	(ArrayList<Country>) request.getAttribute("countries"); %>

<html>
	<head>
		<title>Game of Risk</title>
		<link rel="stylesheet" type="text/css" href="css/app.css" /> 
	</head>
<body>

<h1>GAME OF RISK</h1>

<table>
<tr>
<th>Players: </th>
</tr>
<tr>

 <td><% out.write("1. " + players.get(0).getPlayerName()  
		 + " - " + players.get(0).getArmies().size() + " armies"); %>
	<span id="player1" class="text">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("2. " + players.get(1).getPlayerName()  
		 + " - " + players.get(2).getArmies().size() + " armies"); %>
	<span style="Color:blue">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("3. " + players.get(2).getPlayerName()  
		 + " - " + players.get(2).getArmies().size() + " armies"); %>
	<span style="Color:green">&#8226;</span>
</td></tr>
<% if (players.size() >= 4) { %>
 <tr><td><% out.write("4. " + players.get(3).getPlayerName()  
		 + " - " + players.get(3).getArmies().size() + " armies"); %>
	<span style="Color:#E9AB17">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() >= 5) { %>
 <tr><td><% out.write("5. " + players.get(4).getPlayerName()  
		 + " - " + players.get(4).getArmies().size() + " armies"); %>
	<span style="Color:purple">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() == 6) { %>
 <tr><td><% out.write("6. " + players.get(5).getPlayerName()  
		 + " - " + players.get(5).getArmies().size() + " armies"); %>
	<span style="Color:orange">&#8226;</span>
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
<% for (Territory territory : territoryService.getTerritories(country.getCountryId())) { %>

<td>
<% if (players.get(0).getTerritories().contains(territory)) { %>
	<div id="player1">
  <form action="/app" method="POST">
     <a  href="javascript:;" onclick="parentNode.submit();"><%=territory.getTerritoryName()%></a>
   </form>
   </div>
<% } %>
<% if (players.get(1).getTerritories().contains(territory)) { %>
<div id="player2">
 <form action="/app" method="POST">
     <a href="javascript:;" onclick="parentNode.submit();"><%=territory.getTerritoryName()%></a>
   </form>
 </div>
<% } %>
<% if (players.get(2).getTerritories().contains(territory)) { %>
<span style="Color:green"><% out.write(territory.getTerritoryName()); %></span>
<% } %>
<% if ( players.size() >= 4 && players.get(3).getTerritories().contains(territory) ) { %>
<span style="Color:pink"><% out.write(territory.getTerritoryName()); %></span>
<% } %>
<% if ( players.size() >= 5 && players.get(4).getTerritories().contains(territory) ) { %>
<span style="Color:purple"><% out.write(territory.getTerritoryName()); %></span>
<% } %>
<% if ( players.size() == 6 && players.get(5).getTerritories().contains(territory) ) { %>
<span style="Color:orange"><% out.write(territory.getTerritoryName()); %></span>
<% } %>
<% } %>
</td>
<td></td>
</tr>
<% } %>
</table>

</body>
</html>
