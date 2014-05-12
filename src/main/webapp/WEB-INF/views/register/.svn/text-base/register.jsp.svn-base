<%@page import="com.platybox.provider.servlets.CookieMap" %>

<%
String name = (String) session.getAttribute("name");		
String username = (String) session.getAttribute("username");		
String email = (String) session.getAttribute("email");
%>

<table class="login-table">

	<tr>
		<td>
		</td>
	</tr>

	<tr> 
		<td>
    <form name="loginForm" action="register" method="POST">
        
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
	        if ( session.getAttribute("missingemail") != null)
	        	out.println("Choose an email.");        
	        else if (session.getAttribute("invalidemail") != null )
	        	out.println("Doesn't look like an email.");
	        else if (session.getAttribute("unavailableemail") != null)
	        	out.println("Email already registered.");
	        else
	        	out.println("Your Email:");
	        
	        session.removeAttribute("email");
	        session.removeAttribute("missingemail");
	        session.removeAttribute("invalidemail");
	        session.removeAttribute("unavailableemail");        
	    %></div>
        <input type="text" name="email" <%if (email!=null) out.println("value=\""+email+"\""); else out.println("value=\"\"");%>  class="inputRegister"/>
        
        
        <div class="login"><%
	        if ( session.getAttribute("missingpassword") != null)
	        	out.println("Choose a password.");
	        else if (session.getAttribute("invalidpassword") != null )
	        	out.println("Your password must be at least 6 symbols.");
	        else if (session.getAttribute("notmatchingpassword") != null)
	        	out.println("Your passwords don't match.");
	        else
	        	out.println("New Password:");
        %></div>
        <input type="password" name="password" value="" class="inputRegister" />
        <%
        
        session.removeAttribute("missingpassword");
        session.removeAttribute("invalidpassword");
        session.removeAttribute("notmatchingpassword");        
        %><br>
        <input type="hidden" name="users_types_id" value="1"/>
        <input type="submit" name="Register" value="Register" class="btnRegister" />
    </form>
    
    	
    	</td>
    </tr>
	
	<tr>
		<td>
			<div class="login"><a href="login">Login to your account</a></div>		
		</td>
	</tr>
	
	
	</table>
 
    