<table class="login-table">

	<tr>
		<td>
		  
		</td>
	</tr>

	<tr> 
		<td>

  		<div class="login">Request a Platybox invite.<br><br></div>

		<div class="login"><%
        if ( session.getAttribute("missingemail") != null)
        	out.println("Choose an email.");        
        else if (session.getAttribute("invalidemail") != null )
        	out.println("Was that an email?");
        else if (session.getAttribute("unavailableemail") != null)
        	out.println("Email already registered.");
        else
        	out.println("Your email:");
        
        session.removeAttribute("email");
        session.removeAttribute("missingemail");
        session.removeAttribute("invalidemail");
        session.removeAttribute("unavailableemail");        
    	%></div>

	    <form name="signupForm" action="signup" method="POST">
	        <input type="text" name="email" value="" class="inputLogin"/><br>        
	        <input type="submit" name="Signup" value="Submit" class="btnLogin" />			
	    </form>
	    
	    <div class="login"><br>We shall not share your email, period.</div>
	    
	    <div class="login"><a href="register">Old-school registration here.</a></div>	
	    			 		
		</td>		
					
	</tr>

	<tr>
	<td>					
	</td></tr>
		
</table>
