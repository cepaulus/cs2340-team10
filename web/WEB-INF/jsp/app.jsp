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
  <div class="container">

    <div class="row">
      <div class="span12 text-center">
<h1>Game of Risk</h1>

<table>
<tr>
<th>Players: </th>

<td></td><td></td><td></td>
<% System.out.println("Player name: " + currentPlayer.getPlayerName()); %>
<th>Current player: </th><td><% out.write(currentPlayer.getPlayerName()); %></td>

</tr>
<tr>

 <td><% out.write("1. " + players.get(0).getPlayerName()  
		 + " - " + players.get(0).getArmies().size() + " armies"); %>
	<span id="player1">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("2. " + players.get(1).getPlayerName()  
		 + " - " + players.get(2).getArmies().size() + " armies"); %>
	<span id="player2">&#8226;</span>
</td>
</tr><tr>
 <td><% out.write("3. " + players.get(2).getPlayerName()  
		 + " - " + players.get(2).getArmies().size() + " armies"); %>
	<span id="player3">&#8226;</span>
</td></tr>
<% if (players.size() >= 4) { %>
 <tr><td><% out.write("4. " + players.get(3).getPlayerName()  
		 + " - " + players.get(3).getArmies().size() + " armies"); %>
	<span id="player4">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() >= 5) { %>
 <tr><td><% out.write("5. " + players.get(4).getPlayerName()  
		 + " - " + players.get(4).getArmies().size() + " armies"); %>
	<span id="player5">&#8226;</span>
</td></tr>
<% } %>
<% if (players.size() == 6) { %>
 <tr><td><% out.write("6. " + players.get(5).getPlayerName()  
		 + " - " + players.get(5).getArmies().size() + " armies"); %>
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
  <form action="app" method="POST">
    <input type="hidden" name="operation" value="POST"/>
   <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
   <input type="hidden" name="playerId" value="0"/>
   <input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()-1%>"/>
    <input class="territoryButton" type="submit" 
    	value=<%=(territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")")%>/>
   </form>
   </div>
<% } %>
<% if (players.get(1).getTerritories().contains(territory)) { %>
<div id="player2">
 <form action="app" method="POST">
   <input type="hidden" name="operation" value="POST"/>
   <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
   <input type="hidden" name="playerId" value="1"/>
     <input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()-1%>"/>
     <input class="territoryButton" type="submit" 
    	value=<%=(territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")")%>/>
   </form>
 </div>
<% } %>
<% if (players.get(2).getTerritories().contains(territory)) { %>
	<div id="player3">
  <form action="app" method="POST">
      <input type="hidden" name="operation" value="POST"/>
   	  <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
      <input type="hidden" name="playerId" value="2"/>
        <input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()-1%>"/>
       <input class="territoryButton" type="submit" 
    	value=<%=(territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")")%>/>
   </form>
   </div>
<% } %>
<% if ( players.size() >= 4 && players.get(3).getTerritories().contains(territory) ) { %>
	<div id="player4">
  <form action="/app" method="POST">
     <a  href="javascript:;" onclick="parentNode.submit();">
     	<%=territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")"%></a>
   </form>
   </div>
<% } %>
<% if ( players.size() >= 5 && players.get(4).getTerritories().contains(territory) ) { %>
	<div id="player5">
  <form action="/app" method="POST">
     <a  href="javascript:;" onclick="parentNode.submit();">
		<%=territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")"%></a>
   </form>
   </div>
<% } %>
<% if ( players.size() == 6 && players.get(5).getTerritories().contains(territory) ) { %>
	<div id="player6">
  <form action="/app" method="POST">
  <input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
     <a  href="javascript:;" onclick="parentNode.submit();">
     	<%=territory.getTerritoryName() + " (" + territory.getNumberOfArmies() + ")"%></a>
   </form>
   </div>
<% } %>
<% } %>
</td>
<td></td>
</tr>
<% } %>
</table>
</div>
</div>
</div>
</body>
</html>
