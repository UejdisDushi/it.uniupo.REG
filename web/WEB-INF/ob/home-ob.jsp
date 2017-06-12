<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home Operatore Banco</title> <!--bentornato ....!-->
    <link rel="stylesheet" href="/assets/stylesheets/main3.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
</head>
<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToVenditaTramiteOB">Vendita</html:link>
    <html:link action="/forwardToNuovoMessaggio">Nuovo messaggio</html:link>
    <html:link action="/logout">Log Out</html:link>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>

</body>
</html>