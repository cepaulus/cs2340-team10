<%@ page import="main.java.edu.gatech.cs2340.risk.model.Player"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%
	ArrayList<Player> players = (ArrayList<Player>) request.getAttribute("players");
%>
<html>
<head>
<title>Risk - New Game</title>
<link rel="stylesheet" type="text/css" href="risk/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="risk/css/login.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="span12 text-center">
				<h1>Add Players</h1>
				<div class="row">
					<!-- INPUT FORMS -->
					<%
						if (players.size() > 0) {
					%>
					<%
						for (int id = 0; id < players.size(); id++) {
					%>
					<div class="span4">
						<%
							Player player = players.get(id);
						%>
						<form action="/risk" method="POST" class="prevSubmissionName">
							<!-- hidden operation element to simulate HTTP PUT method in server -->
							<input type="hidden" name="operation" value="PUT" /> <input
								type="hidden" name="name" value="<%=player.getPlayerName()%>" />
							<span class="input-large uneditable-input"><%=player.getPlayerName()%></span>
						</form>
						<form action="/risk" method="POST">
							<!-- hidden operation element to simulate HTTP DELETE method in server -->
							<input type="hidden" name="operation" value="DELETE" /> <input
								type="hidden" name="id" value="<%=id%>" /> <input
								class="btn btn-danger span2 offset1" type="submit"
								value="Delete" />
						</form>
					</div>
					<%
						}
						}
					%>
					<div class="span4">
						<%
							if (players.size() < 6) {
						%>
						<form action="/risk" method="POST" class="newSubmissionName"
							name="newPlayer">
							<input class="input-large" type="text" name="name" /> <input
								class="btn btn-primary span2 offset1" type="submit" value="Add"
								tabindex="-1" />
						</form>
						<%
							}
						%>
					</div>
				</div>
				<!-- INPUT FORMS -->
				<div class="row">
					<%
						if (players.size() >= 3 && players.size() <= 6) {
					%>
					<form action="risk/app" method="GET" class="span12 text-center">
						<a class="btn btn-success btn-large" id="btnStart"
							href="javascript:;" onclick="parentNode.submit();"><%="Start Game"%></a>
					</form>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript" language="JavaScript">
	<%
	if (players.size() < 6) {
	%>
	document.forms['newPlayer'].elements['name'].focus();
	<%
	} else {
	%>
	document.getElementById("btnStart").focus();
	<%
	}
	%>
</script>
</html>
