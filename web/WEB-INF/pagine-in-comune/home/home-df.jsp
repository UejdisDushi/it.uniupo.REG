<%@ page import="model.Login" %>
<%@ page import="db.DBManager" %>
<%@ page import="model.Messaggio" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home <%= ((Login)request.getSession().getAttribute("login")).getUser()%></title> <!--bentornato ....!-->
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>


<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToVendita" styleId="uno">Vendita</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="due">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="tre">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="quattro">Contattaci</a>
    <html:link action="/logout" styleId="cinque">Log Out</html:link>
</div>

<%
    DBManager dbManager = new DBManager();
    if(!dbManager.getRuoloByCF(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser())).equals("df")) {
        response.sendRedirect("/login.jsp");
        return;
    }
    String anagrafica = dbManager.getNomeECognomeByCf(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser()));
%>
<h1 class="bentornato">Bentornato <%=anagrafica%></h1>


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
