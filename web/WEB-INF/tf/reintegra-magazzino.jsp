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

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToAttivaCollaboratore">Attiva collaboratore</html:link>
    <html:link action="/forwardToReintegraMagazzino">Reintegra magazzino</html:link>
    <html:link action="/logout">Log Out</html:link>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>

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

</body>
</html>
