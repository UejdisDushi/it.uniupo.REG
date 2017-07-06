<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="model.Grafici" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Home <%= ((Login)request.getSession().getAttribute("login")).getUser()%></title> <!--bentornato ....!-->
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <script type="text/javascript" src="/assets/javascripts/canvasjs.min.js"></script>
    <script type="text/javascript" src="/assets/javascripts/jquery.canvasjs.min.js"></script>
</head>
<body>

<%
    DBManager dbManager = new DBManager();
    int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
    String anagrafica = dbManager.getNomeECognomeByCf(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser()));
    Grafici data = (Grafici) request.getAttribute("grafici");
    int numCompl, totPezzi, farmVenduti;
    if(data == null) {
        numCompl = dbManager.getNumeroComplessivoAcquisti(idFarmacia);
        totPezzi = dbManager.getNumeroPezziVenduti(idFarmacia);
        farmVenduti = dbManager.getNumeroDiFarmaciConRicetta(idFarmacia);
    }
    else {
        numCompl = dbManager.getNumeroComplessivoAcquistiByRange(idFarmacia, data.getDa(), data.getA());
        totPezzi = dbManager.getNumeroPezziVendutiByRange(idFarmacia,data.getDa(), data.getA());
        farmVenduti = dbManager.getNumeroDiFarmaciConRicettaByRange(idFarmacia,data.getDa(), data.getA());
    }
%>

<script type="text/javascript">
    window.onload = function () {
        var chart = new CanvasJS.Chart("TotVendite",
            {
                title:{
                    text: "Numero di ordini"
                },
                animationEnabled: true,
                legend:{
                    verticalAlign: "bottom",
                    horizontalAlign: "center"
                },
                data: [
                    {
                        type: "pie",
                        showInLegend: true,
                        toolTipContent: "{name}: <strong>{y}</strong>",
                        indexLabel: "{name}: {y}",
                        dataPoints: [
                            {  y: <%=numCompl%>, name: "<%=dbManager.getNomeFarmacia(idFarmacia)%>", exploded: true},
                            {  y: <%=dbManager.getNumeroComplessivoAcquisti(0)%>, name: "Totale"}
                        ]
                    }
                ]
            });
        chart.render();
        var chart = new CanvasJS.Chart("TotPezzi",
            {
                title:{
                    text: "Pezzi venduti"
                },
                animationEnabled: true,
                legend:{
                    verticalAlign: "bottom",
                    horizontalAlign: "center"
                },
                data: [
                    {
                        type: "pie",
                        showInLegend: true,
                        toolTipContent: "{name}: <strong>{y}</strong>",
                        indexLabel: "{name}: {y}",
                        dataPoints: [
                            {  y: <%=totPezzi%>, name: "<%=dbManager.getNomeFarmacia(idFarmacia)%>", exploded: true, color:"#4CAF50"},
                            {  y: <%=dbManager.getNumeroPezziVenduti(0)%>, name: "Totale", color:"#2196F3"}
                        ]
                    }
                ]
            });
        chart.render();
        var chart = new CanvasJS.Chart("farmaci-ricetta",
            {
                title:{
                    text: "Categoria farmaci venduti"
                },
                animationEnabled: true,
                legend:{
                    verticalAlign: "bottom",
                    horizontalAlign: "center"
                },
                data: [
                    {
                        type: "pie",
                        showInLegend: true,
                        toolTipContent: "{name}: <strong>{y}</strong>",
                        indexLabel: "{name}: {y}",
                        dataPoints: [
                            {  y: <%=farmVenduti%>, name: "Con ricetta", exploded: true, color:"#f44336"},
                            {  y: <%=dbManager.getNumeroDiFarmaciSenzaRicetta(idFarmacia)%>, name: "Senza ricetta", color:"#555"}
                        ]
                    }
                ]
            });
        chart.render();
    }
</script>


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

<h1 class="bentornato-custom">Bentornato <%=anagrafica%></h1>


<form action="grafici-per-range.do" method="post">
    <label style="margin-left: 430px;display: inline">Da: </label><input type="date" name="da" class="form-control" required placeholder="aaaa-MM-dd" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])" style="width: 200px;margin: auto;display:inline;">
    <label style="margin-left: 102px">A: </label><input type="date" name="a" class="form-control" required placeholder="aaaa-MM-dd" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])" style="width: 200px;margin: auto;display:inline;">
    <br><br>
    <input value="Elabora" type="submit" style="margin-left: 655px">
</form>
<br>

<div id="grafici">
    <div id="TotVendite" style="height: 300px; width: 70%;margin:auto"></div>
    <br><br><br>
    <div id="TotPezzi" style="height: 300px; width: 70%;margin:auto"></div>
    <br><br><br>
    <div id="farmaci-ricetta" style="height: 300px; width: 70%;margin:auto"></div>
</div>


<br><br><br><br><br>


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