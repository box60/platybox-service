<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="leftbox">
<h1>About yourself</h1>
<p>Your profile is public and can be seen by anybody in Platybox.</p>

<h1>Tips</h1>
<p>Pick an avatar that clearly represents you. A photograph of yourself makes your account personal and interesting.</p>
<p>Platybox is about people, we require that you use your full name, i.e. your first and second name, two words without punctuation. 
For the sake of simplicity we don't accept other characters, like accents or hyphenated words.</p>
<p>You must write a short description about yourself. Why are you on Platybox? What should people follow you here?</p>
</div>

<div id="rightbox">

<div id="sub-menu">
<ul>
<li><a href="<c:url value="/settings/account"/>">Account</a></li>
<li><a href="<c:url value="/settings/profile"/>">Profile</a></li>
</ul>
</div>

<form method="post" action="<c:url value="/settings/profile"/>" enctype="multipart/form-data">   
   <br><br>
    <img src="${photo}"><br>
    Maximum size of 1Mb. JPG, GIF, PNG.<br>   
    <input type="hidden" name="name"/><br>
	<input type="file" name="file"/><br>
	${flashimage}
	
	<br><br>
	Real Name:<br>
	<input type="text" name="realname" value="${name}" id="input"/><br>
	${flashname}
	
	<br><br>
	Bio:<br>	
	<textarea name="bio" cols=50 rows=6 id="input">${description}</textarea><br>
	${flashdescription}
	
	<br><br>
	<input type="submit" value="Save" id="btn"/><br>
	${flashsuccess}
</form>        
</div>       