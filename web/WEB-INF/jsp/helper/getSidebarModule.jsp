<%
	if (risk.getStage() == RiskConstants.ATTACK
			&& risk.getStep() == RiskConstants.SELECT_DEFENDING_TERRITORY) {
%>
	<%@include file="../module/attack.jsp" %>

<% } %>

<%
	if (risk.getStage() == RiskConstants.ATTACK
			&& risk.getStep() == RiskConstants.DO_ATTACK) {	
%>
	<%@include file="../module/attackResults.jsp" %>	

<% } %>

<%
	if (risk.getStage() == RiskConstants.ATTACK
			&& risk.getStep() == RiskConstants.SELECT_DEFENDING_ARMIES) {
%>
	<%@include file="../module/defendingArmyNum.jsp" %>

<% } %>

<%
	if (risk.getStage() == RiskConstants.SETUP_TURN
			&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {
%>
	<%@include file="../module/options.jsp" %>

<% } %>

<%
	if (risk.getStage() == RiskConstants.MOVE_ARMIES
			&& risk.getStep() == RiskConstants.SELECT_DESTINATION_TERRITORY) {	
%>
	<%@include file="../module/fortify.jsp" %>
<% } %>

<%
	if (risk.getStage() == RiskConstants.MOVE_ARMIES
			&& risk.getStep() == RiskConstants.ATTACK_MOVE) {
%>
	<%@include file="../module/attackMove.jsp" %>
<% } %>

<%
	if (risk.getStage() == RiskConstants.DECLARE_WINNER) {
%>
	<%@include file="../module/winner.jsp" %>
<% } %>