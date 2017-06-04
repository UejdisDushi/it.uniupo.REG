
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Dashboard REG</title>
    <link rel="stylesheet" href="/assets/stylesheets/main3.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
</head>
<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToAttivaFarmacia">Attiva Farmacia</html:link>
    <a href="#">Gestione messaggi</a>
    <html:link action="/logout">Log Out</html:link>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>

</body>
</html>