<%@ page import="db.DBManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Farmacia" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <a href="/login.jsp" id="uno">Log In</a>
    <a href="mailto: uejdis.dushi@gmail.com" id="due">Contattaci</a>
</div>

<div class="elencoFarmacie">
    <%
        DBManager dbManager = new DBManager();
        ArrayList<Farmacia> elencoFarmacie = dbManager.elencoFarmacie();
        for(Farmacia farmacia : elencoFarmacie) {
    %>
    <ul>
        <li>
            <span class="nome"><%=farmacia.getNomeFarmacia()%></span>
            <br>
            <%=farmacia.getVia()%> - <%=farmacia.getCap()%> <%=farmacia.getCitta()%> (<%=farmacia.getProvincia()%>)
            <br>
            Tel. <%=farmacia.getNumeroTelefono()%>
            <br>
            <br>
        </li>
    </ul>
    <%}%>
</div>

<footer id="footer">
    <section class="text">
        <p>
            Design & production Copyright 2017 by Uejdis Dushi.
        </p>
    </section>
    <br>
</footer>
</body>
</html>
