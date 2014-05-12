	
<%@page import="java.util.Calendar" import="java.util.GregorianCalendar;"%>
<table class="footer-table">
	<tr>
		<td>
			<% 	Calendar cal=Calendar.getInstance();
				int year =cal.get(Calendar.YEAR);				
				out.print("<p class='copyright'>Platybox &copy " + year +"</p>");
			%>
		</td>
	</tr> 
 </table>
