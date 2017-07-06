<%@ page import="db.DBManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Farmacia" %>
<%@ page import="model.Grafici" %>
<%@ page import="model.Login" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <title>Home REG</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <script type="text/javascript" src="/assets/javascripts/canvasjs.min.js"></script>
    <script type="text/javascript" src="/assets/javascripts/jquery.canvasjs.min.js"></script>
</head>
<body>

<%
    DBManager dbManager = new DBManager();
    if(!dbManager.getRuoloByCF(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser())).equals("")) {
        response.sendRedirect("/login.jsp");
        return;
    }
    ArrayList<Farmacia> elencoNomiFarmacie = dbManager.getNomiFarmacie();
    Grafici data = (Grafici) request.getAttribute("grafici");
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
                            <%
                            for(Farmacia fa:elencoNomiFarmacie) { if(data == null) {
                            %>
                            {  y: <%=dbManager.getNumeroComplessivoAcquisti(fa.getIdFarmacia())%>, name: "<%=dbManager.getNomeFarmacia(fa.getIdFarmacia())%>"},
                            <%} else {%>
                            {  y: <%=dbManager.getNumeroComplessivoAcquistiByRange(fa.getIdFarmacia(),data.getDa(), data.getA())%>, name: "<%=dbManager.getNomeFarmacia(fa.getIdFarmacia())%>"},
                            <%}}%>
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
                            <%
                                for(Farmacia fa:elencoNomiFarmacie) { if(data == null) {
                                %>
                            {  y: <%=dbManager.getNumeroPezziVenduti(fa.getIdFarmacia())%>, name: "<%=dbManager.getNomeFarmacia(fa.getIdFarmacia())%>"},
                            <%} else {%>
                            {  y: <%=dbManager.getNumeroPezziVendutiByRange(fa.getIdFarmacia(), data.getDa(),data.getA())%>, name: "<%=dbManager.getNomeFarmacia(fa.getIdFarmacia())%>"},
                            <%}}%>
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
    <html:link action="/forwardToAttivaFarmacia" styleId="uno">Attiva Farmacia</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="due">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="tre">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="quattro">Contattaci</a>
    <html:link action="/logout" styleId="cinque">Log Out</html:link>
</div>

    <h1 class="bentornato-custom">Bentornato <span>REG</span></h1>

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
