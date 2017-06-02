
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



    <a <jsp:forward page="attiva-farmacia.jsp"> Attiva Farmacia</jsp:forward>> Attiva farmacia</a>
    <a href="#">Attiva farmacia</a>


    <a href="#">Gestione messaggi</a>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>
<span>&#9776; Log Out </span>


</body>
</html>
