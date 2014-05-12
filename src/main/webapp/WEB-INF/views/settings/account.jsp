<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="leftbox">

<h1>Changing your username</h1>
<p>Your username will not be changed if it's already taken or if it's an invalid username.</p>
<p>Valid usernames contain letters, numbers and '_'</p>


<h1>Register a phone</h1>
<p>To register a phone to your account, or update your currently register phone, text the word SIGNUP to (604) 800-9199.</p>

</div>


<div id="rightbox">

<div id="sub-menu">
<ul>
<li><a href="<c:url value="/settings/account"/>">Account</a></li>
<li><a href="<c:url value="/settings/profile"/>">Profile</a></li>
</ul>
</div>


<form method="post" action="<c:url value="/settings/account"/>">
       
   	<p>
	Username:<br>
	<input type="text" name="username" value="${username}" id="input"/><br>
    ${flashusername}
	</p>
	
	<p>
	Email:<br>
	<input type="text" name="email" value="${email}"/ id="input"><br>
	${flashemail}
	</p>
	
	<p>
	Phone:<br>
	${phone}<br>
	</p>
	
	<p>
	<input type="submit" value="Save" id="btn" /><br>
	${flashsuccess}
	</p>
</form>        
</div>       