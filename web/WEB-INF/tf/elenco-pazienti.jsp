<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="model.Rimanenze" %>
<%@ page import="db.DBManager" %>
<%@ page import="model.Paziente" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/prova-tabella.css">
    <title>Elenco pazienti</title>
</head>

<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToAttivaCollaboratore">Attiva collaboratore</html:link>
    <html:link action="/forwardToReintegraMagazzino">Reintegra magazzino</html:link>
    <html:link action="/forwardToVendita">Vendita</html:link>
    <html:link action="/logout">Log Out</html:link>
</div>
<span style="font-size:25px;cursor:pointer;" onclick="openNav()">&#9776; Menu</span>

<input type="text" id="cercaPerNome" onkeyup="cercaPerNome()" placeholder="Cerca per nome.." title="Type in a name">
<form action="/vendita.do" method="post">
    <table id="tabella">
        <tr class="header">
            <th style="width:25%;">Codice Fiscale</th>
            <th style="width:30%;">Nome</th>
            <th style="width:30%;">Cognome</th>
            <th style="width:10%;">Data di nascita</th>
            <th style="width:5%;"></th>
        </tr>
        <%
            DBManager dbManager = new DBManager();
            ArrayList<Paziente> elencoPazienti = dbManager.getPazienti();
            for(int i = 0;i < elencoPazienti.size();i++) {
        %>

        <tr>
            <td>
                <%=elencoPazienti.get(i).getCf()%>
            </td>
            <td>
                <%=elencoPazienti.get(i).getNome()%>
            </td>
            <td>
                <%=elencoPazienti.get(i).getCognome()%>
            </td>
            <td>
                <%=elencoPazienti.get(i).getDataDiNAscita()%>
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
<!--questo pulsante serve per reindirizzare se il CF, quindi paziente è già presente!-->
<form action="/già-presente.do">
    <input value="Già presente" type="submit">
</form>
</body>

</html>