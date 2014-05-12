<table class="login-table">

	<tr>
		<td>
			<div class="login">
				Reset your password.
			</div>			
		</td>
	</tr>

	<tr> 
		<td>

	    <form name="recoverForm" action="recover" method="POST">
	    	<div class="login">Your registered email:</div>
	        <input type="text" name="email" value="" class="inputLogin"/><br>        
	        <input type="submit" name="Recover" value="Submit" class="btnLogin" />			
	    </form>		
			 		
		</td>				
	</tr>

	<tr><td>
			
			<%String validlogin = (String) session.getAttribute("validemail");	
			if (validlogin!=null && validlogin.equalsIgnoreCase("false")){%>
				<div class="login">
				<%out.println("You miszpeled your email.");								
				session.removeAttribute("validemail");//we neeed this no more.%>
			</div>				
			<%}else {%>
				<div class="login"><a href="login">Login to your account</a></div>		
			<%} %>
			
	</td></tr>
		
</table>
