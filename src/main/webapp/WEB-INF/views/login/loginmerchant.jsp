
<table class="login-table">

	<tr>
		<td>
		</td>
	</tr>

	<tr> 
		<td>

		<!-- Platybox login -->
	    <form name="loginForm" action="${pageContext.request.contextPath}/login" method="POST">
	    	<div class="login">Username or Email</div>
	        <input type="text" name="usernameOrEmail" value="" class="inputLogin"/><br>
	        <div class="login">Password</div>
	        <input type="password" name="password" value="" class="inputLogin"/><br>
	        <input type="hidden" name="action" value="login"/>	        
	        <input type="submit" name="Login" value="Login" class="btnLogin" />			
	    </form>		
	    
	    <%String validlogin = (String) session.getAttribute("validlogin");	
			if (validlogin!=null && validlogin.equalsIgnoreCase("false")){%>
			<div class="login">		
				<%out.println("Oops! You miszpeled something.");								
				session.removeAttribute("validlogin");//we neeed this no more.%>
			</div>		
			<div class="login">	Forgot your password? <a href="recover">Recover</a>.</div>					
			<%} else {%>
				<br>
				<div class="login">	Don't have an account? <a href="register">Register</a>.</div>
			<%} %>		
	    	 		
		</td>				
	</tr>

	<tr><td>			
							
	</td></tr>
		
</table>



