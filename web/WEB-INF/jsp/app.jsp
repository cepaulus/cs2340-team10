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
	Risk risk = (Risk) request
			.getAttribute("risk");
%>
<%
	String directionsText = "";
	switch (risk.getDirections()) {
	case 0:
		break;
	case 1:
		directionsText = "When it is your turn, click on any territory belonging to you to add an army to it.";
		break;
	case 2:
		directionsText = currentPlayer.getPlayerName()
				+ ", you have "
				+ currentPlayer.getAvailableArmies()
				+ " additional "
				+ (currentPlayer.getAvailableArmies() > 1 ? "armies"
						: "army") + " to distribute.";
		break;
	case 3:
		directionsText = currentPlayer.getPlayerName()
				+ ", select a territory to attack from.";
		break;
	case 4:
		directionsText = currentPlayer.getPlayerName() + ", select a territory to transfer armies from.";
		break;
	}
%>
<%
String modalName = "";
switch (risk.getStage()) {
	case RiskConstants.SETUP_TURN: 
		if (risk.getStep() == RiskConstants.SHOW_OPTIONS) {
			modalName = "optionsDialog";
		}
		break;
	case RiskConstants.ATTACK: 
		switch (risk.getStep()) {
			case RiskConstants.SELECT_DEFENDING_TERRITORY:
				modalName = "attackDialog";
				break;
			case RiskConstants.SELECT_DEFENDING_ARMIES:
				modalName = "defendingArmyNumDialog";
				break;
			case RiskConstants.DO_ATTACK:
				modalName = "attackResultsDialog";
				break;
		}
		break;
	case RiskConstants.MOVE_ARMIES: 
		switch (risk.getStep()) {
			case RiskConstants.SELECT_ARMIES_TRANSFERRED:
				modalName = "movingArmyNumDialog";
				break;
			case RiskConstants.SELECT_DESTINATION_TERRITORY:
				modalName = "fortifyDialog";
				break;
		}
		break;
	case RiskConstants.DECLARE_WINNER: 
		modalName = "winnerDialog";
		break;
} 
%>

<html>
<head>
<title>Risk - Game</title>
<link
	rel="stylesheet"
	type="text/css"
	href="css/bootstrap.min.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/app.css"
/>
<link
	rel="stylesheet"
	type="text/css"
	href="css/slider.css"
/>
<script
	type="text/javascript"
	src="js/jquery.min.js"
></script>
<script
	type="text/javascript"
	src="js/bootstrap.min.js"
></script>
<script
	type="text/javascript"
	src="js/bootstrap-slider.js"
></script>
<script type="text/javascript">

function showalert(message,alerttype) {
    $('#alert_placeholder').append('<div id="alertdiv" class="alert ' +  alerttype + '"><a class="close" data-dismiss="alert">×</a><span>'+ message +'</span></div>')
    setTimeout(function() {
      $("#alertdiv").remove();
    }, 4000);
  }
<%if (risk.getDirections() != 0) {%>
	$(function() {
		showalert("<%=directionsText%>","alert-info");
	});
<%}%>

<%if (!modalName.isEmpty()) {%>
	$(function() {
		$('#<%= modalName %>').modal({
			keyboard : false,
			show : true
		});
		$('.slider').slider();
	});
<%}%>



</script>
</head>
<body>
	
<% if (modalName == "optionsDialog") { %>
	<%@include file="modal/optionsDialog.jsp" %>
<% } %>

<% if (modalName == "attackDialog") { %>
	<%@include file="modal/attackDialog.jsp" %>
<% } %>

<% if (modalName == "defendingArmyNumDialog") { %>
	<%@include file="modal/defendingArmyNumDialog.jsp" %>
<% } %>

<% if (modalName == "attackResultsDialog") { %>
	<%@include file="modal/attackResultsDialog.jsp" %>
<% } %>

<% if (modalName == "fortifyDialog") { %>
	<%@include file="modal/fortifyDialog.jsp" %>
<% } %>

<% if (modalName == "movingArmyNumDialog") { %>
	<%@include file="modal/movingArmyNumDialog.jsp" %>
<% } %>

<% if (modalName == "winnerDialog") { %>
	<%@include file="modal/winnerDialog.jsp" %>
<% } %>


<div
	id="wrap"
	class="container-fluid"
>

	<!-- WRITE PLAYERS IN ROLL ORDER -->
	<div
		class="row-fluid text-center"
		id="players"
	>
		<%
			String span = "span" + (12 / players.size());
			boolean oddOffset = false;

			if (players.size() % 2 != 0) {
				span = "span" + (10 / players.size());
				oddOffset = true;

			}
		%>
		<%
			for (Player player : players) {
		%>
		<div class="<%if (oddOffset)
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
	<div
		class="row-fluid"
		id="map"
	>
		<%
			for (Player player : players) {
		%>
		<%
			for (Territory territory : player.getTerritories()) {
		%>
		<div class="territory <%out.write("player" + (player.getPlayerId()));%> <%out.write("territory" + territory.getTerritoryId());%>">
			<form
				action="app"
				method="POST"
			>
				<input
					type="hidden"
					name="operation"
					value="POST"
				/> <input
					type="hidden"
					name="territoryId"
					value="<%=territory.getTerritoryId()%>"
				/> <input
					type="hidden"
					name="currentPlayerId"
					value="<%=currentPlayer.getPlayerId()%>"
				/> <input
					class="territoryButton btn btn-link"
					type="submit"
					value="<%=territory.getNumberOfArmies()%>"
				/>
			</form>
		</div>
		<%
			}
			}
		%>
	</div>
</div>

<div id="alert_placeholder"></div>
</body>
</html>
