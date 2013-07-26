<%@ page import="main.java.edu.gatech.cs2340.risk.model.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%
	TerritoryServiceImpl territoryService = new TerritoryServiceImpl();
%>
<%
	ArrayList<Player> players = (ArrayList<Player>) request
			.getAttribute("players");
%>
<%
	Player currentPlayer = (Player) request
			.getAttribute("currentPlayer");
%>
<%
	Risk risk = (Risk) request.getAttribute("risk");
%>
<%@include file="helper/translateStepAndStage.jsp" %>
<%
	String directionsText = "";
	switch (risk.getDirections()) {
	case 0:
		break;
	case 1:
		directionsText = currentPlayer.getPlayerName()
		+ ", select a territory to fortify. You have "
		+ currentPlayer.getAvailableArmies()
		+ " additional "
		+ (currentPlayer.getAvailableArmies() > 1 ? "armies"
				: "army") + " to distribute.";
		break;
	case 2:
		directionsText = currentPlayer.getPlayerName()
				+ ", select a territory to attack from.";
		break;
	case 3:
		directionsText = currentPlayer.getPlayerName()
				+ ", select a territory to transfer armies from.";
		break;
	}
%>
<html>
<head>
<title>Risk - Game</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/slider.css" />
<link rel="stylesheet" type="text/css" href="css/app.css" />
<link rel="stylesheet" type="text/css" href="css/map.css" />
<link rel="stylesheet" type="text/css" href="css/player.css" />
<link rel="stylesheet" type="text/css" href="css/territoryPositions.css" />
<link rel="stylesheet" type="text/css" href="css/responsive.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-slider.js"></script>
<script type="text/javascript">
	function showalert(message, alerttype) {
		$('#alert_placeholder')
				.append(
						'<div id="alertdiv" class="alert ' +  alerttype + '"><a class="close" data-dismiss="alert">×</a><span>'
								+ message + '</span></div>')
	}

	<%if (risk.getDirections() != 0) {%>
		$(function() {
			showalert(
				<%out.write(
					"'" + directionsText + "'");%>
					, "alert-info"
				);
		});
	<%}%>

	<%if (risk.getStage() == RiskConstants.SETUP_TURN
						&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {
		directionsText = currentPlayer.getPlayerName() + ", select your next move.";%>
		$(function() {
			showalert(
				<%out.write("'" + directionsText + "'");%>
					, "alert-info"
				);
			});
	<% } %>
	
	$(function() {
		$('.slider').slider();
	});
	
	
</script>
</head>
<body>
	<div class="navbar navbar-fixed-top" id="playerContainer">
		<div class="navbar-inner">
			<div class="row-fluid text-center" id="players">
				<%
					String span = "span" + (12 / players.size());
					boolean oddOffset = false;

					if (players.size() * (12 / players.size()) != 12) {
						span = "span" + (10 / players.size());
						oddOffset = true;

					}
				%>
				<%
					for (Player player : players) {
				%>
				<div
					class="<%if (oddOffset)
					out.write("offset1 ");
				out.write(span);%> player <%out.write("player" + (player.getPlayerId()));%> <%if (currentPlayer.equals(player))
					out.write("active");%>">
					<%
						out.write("<h3>" + player.getPlayerName() + "</h3>" + "<h4>"
									+ player.getAvailableArmies() + " armies</h4>");
					%>
				</div>
				<%
					oddOffset = false;
				%>
				<%
					}
				%>
			</div>
		</div>
		<div id="alert_placeholder"></div>
	</div>
	<div id="wrap" class="container-fluid">
		<div class="row-fluid">

			<div id="sidebarPlaceHolder">
				<div id="sidebar" class="span3">
					<%@include file="helper/getSidebarModule.jsp" %>
				</div>
			</div>

			
				<div id="map" class="span9 offset3">
					<%for (Player player : players) {%>
						<%for (Territory territory : player.getTerritories()) {%>
						<div class="territory <%out.write("player" + (player.getPlayerId()));%> <%out.write("territory" + territory.getTerritoryId());%>">
							<form action="app" method="POST">
								<input type="hidden" name="operation" value="POST" />
								<input type="hidden" name="territoryId" value="<%=territory.getTerritoryId()%>" />
								<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
								<%@include file="helper/alterVisibilities.jsp" %>
						</form>
					</div>
					<%}%>
					<%}%>
				</div>
			
		</div>
	</div>
</body>
</html>