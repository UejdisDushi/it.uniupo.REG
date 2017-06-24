<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home <%= ((Login)request.getSession().getAttribute("login")).getUser()%></title> <!--bentornato ....!-->
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToAttivaCollaboratore" styleId="uno">Attiva</html:link>
    <html:link action="/forwardToReintegraMagazzino" styleId="due">Reintegra</html:link>
    <html:link action="/forwardToVendita" styleId="tre">Vendita</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="quattro">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="cinque">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="sei">Contattaci</a>
    <html:link action="/logout" styleId="sette">Log Out</html:link>
</div>
<%
    DBManager dbManager = new DBManager();
    int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
    String anagrafica = dbManager.getNomeECognomeByCf(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser()));
%>
<h1 class="bentornato">Bentornato <%=anagrafica%></h1>

<br><br>
<h3>Totale vendite <%=dbManager.getNumeroComplessivoAcquisti(idFarmacia)%></h3>
<h3>Numero pezzi venduti <%=dbManager.getNumeroPezziVenduti(idFarmacia)%></h3>
<h3>Numero di farmaci con ricetta <%=dbManager.getNumeroDiFarmaciConRicetta(idFarmacia)%></h3>
<h3>Numero di ricette <%=dbManager.getNumeroDiRicette(idFarmacia)%></h3>
<h3>Numero medio di farmaci prescritti per ricetta <%=dbManager.getMediaFarmaciPerRicetta(idFarmacia)%></h3>


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
