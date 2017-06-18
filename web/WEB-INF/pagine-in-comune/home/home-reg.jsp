<%@ page import="db.DBManager" %>
<%@ page import="model.Messaggio" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Login" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home <%= ((Login)request.getSession().getAttribute("login")).getUser()%></title>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToAttivaFarmacia" styleId="uno">Attiva Farmacia</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="due">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="tre">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="quattro">Contattaci</a>
    <html:link action="/logout" styleId="cinque">Log Out</html:link>
</div>

<article>
    <h1>Bentornato <span>REG</span></h1>
    <br>
    Sei l'amministratore del sito, hai le seguenti funzionalità:
    <ul>
        <li>Attivare le varie farmacie. Il processo prevede anche la creazione del titolare farmacista;</li>
    </ul>
</article>


<article>
    <%
        DBManager dbManager = new DBManager();
        ArrayList<Messaggio> elencoMessaggi = dbManager.getMessaggiDaLeggere(((Login)request.getSession().getAttribute("login")).getUser(),false);
        if(elencoMessaggi.size() != 0) {
    %>
    <a2>Hai <%=elencoMessaggi.size()%> da leggere!</a2>
    <%
        }
    %>
</article>

<div id="wrapper"></div>
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
