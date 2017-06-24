<%@ page import="db.DBManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home REG</title>
    <meta charset="utf-8">
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

    <h1 class="bentornato">Bentornato <span>REG</span></h1>
<%
    DBManager dbManager = new DBManager();
    int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
%>
<br><br>
    <h3>totale vendite <%=dbManager.getNumeroComplessivoAcquisti(idFarmacia)%></h3>
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
