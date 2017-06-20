<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="db.DBManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
<head>
    <script type="application/javascript" src="../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <title>Reintegra magazzino</title>
</head>
<body>



<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToHome" styleId="uno">Home</html:link>
    <html:link action="/forwardToAttivaCollaboratore" styleId="due">Attiva</html:link>
    <html:link action="/forwardToVendita" styleId="tre">Vendita</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="quattro">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="cinque">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="sei">Contattaci</a>
    <html:link action="/logout" styleId="sette">Log Out</html:link>
</div>

<input type="text" id="cercaPerNome" onkeyup="cercaPerNome()" placeholder="Cerca per nome.." title="Type in a name">
<form action="/reintegra-magazzino.do" method="post">
    <table id="tabella">
        <tr class="header">
            <th style="width:30%;">Nome</th>
            <th style="width:25%;">Categoria</th>
            <th style="width:10%;">Costo</th>
            <th style="width:10%;">Ricetta: si / no</th>
            <th style="width:20%;">Quantità reintegro</th>
            <th style="width:5%;"></th>
        </tr>
            <%
                DBManager dbManager = new DBManager();
                ArrayList<Prodotti> catalogoProdotti = dbManager.getTuttiProdotti();
                for(Prodotti prodotto : catalogoProdotti) {
            %>
        <tr>
            <td>
                <%=prodotto.getNome()%>
            </td>
            <td>
                <%=prodotto.getCategoria()%>
            </td>
            <td>
                <%=prodotto.getCosto()%>
            </td>
            <td>
                <%if(prodotto.isRicetta()){%>
                    Si
                <%} else {%>
                    No<%}%>
            </td>
            <td>
                <input type="number" min="0" name="quantita">
            </td>
            <td>
                <input img src="/assets/images/icona-carrello.png" type="image" value="submit">
            </td>
        </tr>
        <% } %>
    </table>
</form>
</div>

<footer id="footer">
    <section class="text">
        <p>
            Design & production Copyright 2017 by Uejdis Dushi.
        </p>
    </section>
    <br>
</footer>

<%
    String message = (String)request.getAttribute("redirect");
    if(message != null) {
        if(message.equals("reintegra-ok")) { %>
<script>
    window.alert('Reintegro effettuato correttamente.');
</script>
<% }else { %>
<script>
    window.alert('Attenzione, nessuna quantità selezionata. Riprova.');
</script>
<%}}
%>


</body>
</html>
