<%@page import="java.util.Calendar" import="java.util.GregorianCalendar;"%>
<div id="copyright">
	<% 	Calendar cal=Calendar.getInstance();
		int year =cal.get(Calendar.YEAR);				
		out.print("<p class='copyright'>Platybox &copy " + year +"</p>");
	%>
</div>