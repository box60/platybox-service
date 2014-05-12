<table class="login-table">

	<tr>
		<td>
			<div class="login"><%
		        if ( session.getAttribute("missingpassword") != null)
		        	out.println("Choose a password.");
		        else if (session.getAttribute("invalidpassword") != null )
		        	out.println("Your password must be at least 6 symbols.");
		        else if (session.getAttribute("notmatchingpassword") != null)
		        	out.println("Your passwords don't match.");
		        else
		        	out.println("Choose a new password, and retype.");
			
				session.removeAttribute("missingpassword");
		        session.removeAttribute("invalidpassword");
		        session.removeAttribute("notmatchingpassword");
	        %>
	                
	        </div>
		
		</td>
	</tr>

	<tr> 
		<td>
	    <form name="updateForm" action="r" method="POST">
	        <input type="password" name="password" value="" class="inputLogin"/>
	        <input type="password" name="passwordverify" value="" class="inputLogin"/>
	    	<input type="hidden" name="userId" value="${userId}"/><br><br>
	    	<input type="submit" name="Submit" value="Submit" class="btnRegister" />
	    </form>			 		
		</td>				
	</tr>

	<tr><td></td></tr>
		
</table>
