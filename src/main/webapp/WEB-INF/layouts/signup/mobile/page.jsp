<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
  <title>Platybox</title>
  <meta name="description" content="Platybox is a minimalistic service that connects you to life in public spaces." />
  <meta name="keywords" content="social networks, qr codes, sensing, coffee shop, discounts, free, awesome, platybox" />
  <meta name="viewport" content="width=device-width,user-scalable=no" />
  <style type="text/css"><%@ include file="/resources/stylesheets/platybox-authentication.css" %></style>
</head>
<body>
  	<div id="header"><tiles:insertAttribute name="header" /></div>
	<div id="content-container">
		<div id="content"><tiles:insertAttribute name="content" /></div>
		<div id="footer"><tiles:insertAttribute name="footer" /></div>
	</div>	
</body>
</html>