<%
String username = (String) session.getAttribute("username");
String name = (String) session.getAttribute("name");
%>

<table class="login-table">

	<tr>
		<td>
			<div class="login">You're almost there. Help us choose a username and a password.</div>		
		</td>
	</tr>

	<tr>
		<td>
	    <form name="updateForm" action="s" method="POST">
	    
	    <div class="login"> <%        
	        if ( session.getAttribute("missingname") != null)
	        	out.println("You forgot your name.");        
	        else if (session.getAttribute("invalidname") != null )
	        	out.println("Mizspelled name or surname");	        
	        else 
	        	out.println("Your real name and surname");
	        
	        session.removeAttribute("name");
	        session.removeAttribute("missingname");
	        session.removeAttribute("invalidname");        
        %></div>
        <input type="text" name="name" <%if (name!=null) out.println("value=\""+name+"\""); else out.println("value=\"\"");%> class="inputRegister"/>
        
	    
	    
		    <div class="login"> <%        
		        if ( session.getAttribute("missingusername") != null)
		        	out.println("Choose a username.");        
		        else if (session.getAttribute("invalidusername") != null )
		        	out.println("Only letters, numbers and '_'");
		        else if (session.getAttribute("unavailableusername") != null)
		        	out.println("Username is already taken.");
		        else 
		        	out.println("New Username:");
		        
		        session.removeAttribute("username");
		        session.removeAttribute("missingusername");
		        session.removeAttribute("invalidusername");
		        session.removeAttribute("unavailableusername");        
	        %></div>
            <input type="text" name="username" <%if (username!=null) out.println("value=\""+username+"\""); else out.println("value=\"\"");%> class="inputRegister"/>	    
	    
	    	<div class="login"><%
		        if ( session.getAttribute("missingpassword") != null)
		        	out.println("Choose a password.");
		        else if (session.getAttribute("invalidpassword") != null )
		        	out.println("Your password must be at least 6 symbols.");
		        else if (session.getAttribute("notmatchingpassword") != null)
		        	out.println("Your passwords don't match.");
		        else
		        	out.println("New password:");
			
				session.removeAttribute("missingpassword");
		        session.removeAttribute("invalidpassword");
		        session.removeAttribute("notmatchingpassword");
	        %>
	                
	        </div>
	        <input type="password" name="password" value="" class="inputRegister"/>
	        
	        
	        
	        
	    	<input type="hidden" name="userId" value="${userId}"/><br><br>
	    	
	    	
	    	<input type="submit" name="Submit" value="Submit" class="btnRegister" />
	    </form>			 		
		</td>				
	</tr>

	<tr><td></td></tr>
		
</table>
