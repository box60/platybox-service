    
<%@page import="com.platybox.provider.servlets.CookieMap" %>
<%@page import="com.platybox.utils.database.DatabaseUtils" %>

<%
    String appDesc = (String)request.getAttribute("CONS_DESC");
    String token = (String)request.getAttribute("TOKEN");
    String callback = (String)request.getAttribute("CALLBACK");
    if(callback == null)
        callback = "";
    
%>

<table class="login-table">

	<tr>
		<td>
		</td>
	</tr>

	<tr> 
		<td>


<%
	String check = (String)(session.getAttribute("THOR"));
	
	if(check!=null) {		
		String usernameOrEmail = DatabaseUtils.getUsername(Integer.parseInt(check));				
%>

     <div class="authorize-greeting">Hi <%= usernameOrEmail %>! </div>
     <br><br>
	 <div class="authorize">"<%=appDesc%>" wants to access your Platybox account.
     <br>No, we won't access your contacts, your friends can miss out on all the fun.</div>
     </br>
    	
    <form name="authZForm" action="authenticate" method="POST">
        <input type="hidden" name="usernameOrEmail" value="<%= usernameOrEmail %>"/><br>
        <input type="hidden" name="oauth_token" value="<%= token %>"/>
        <input type="hidden" name="oauth_callback" value="<%= callback %>"/>
        <input type="submit" name="Authorize" value="Authorize" class="btnAuthorize" />
        <input type="submit" name="Authorize" value="Deny" class="btnAuthorize"/>    
    </form>

<%

	} else { 
	//A special login form that contains both username and consumerKey to checkup
%>
		<form name="authZForm" action="authenticate" method="POST">
	        <div class="login">Username or Email</div>
	        <input type="text" name="usernameOrEmail" value=""  class="inputAuthorize" /><br>
			<div class="login">Password</div>
	        <input type="password" name="password" value=""  class="inputAuthorize" /><br>
	        <input type="hidden" name="oauth_token" value="<%= token %>"/>
	        <input type="hidden" name="oauth_callback" value="<%= callback %>"/>
	        <input type="hidden" name="action" value="authenticate"/>
	        <input type="submit" name="Authenticate" value="Login" class="btnAuthorize"/>	        
	    </form>

<%}%>

		</td>
		
	</tr>

	<tr>

		<td>
	
		<div class='login'>
			<%String validlogin = (String) session.getAttribute("validlogin");	
			if (validlogin!=null && validlogin.equalsIgnoreCase("false")){
				out.println("Oops! You miszpeled something.");								
				session.removeAttribute("validlogin");//we neeed this no more.%>
				</div>
				<br>
				<div class="login">	Forgot your password? <a href="recover">Recover</a>.</div>					
			<%}else {%>
				<br>
				<div class="login">	Don't have an account? <a href="register">Register</a>.</div>
			<%}%>			
		</td>
	</tr>

</table>
