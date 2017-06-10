<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="model.Rimanenze" %>
<%@ page import="db.DBManager" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/prova-tabella.css">
    <title>Vendita</title>
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
            <th style="width:25%;">Nome</th>
            <th style="width:20%;">Categoria</th>
            <th style="width:5%;">Costo</th>
            <th style="width:20%;">Principio Attivo</th>
            <th style="width:5%;">Ricetta</th>
            <th style="width:10%;">In magazzino</th>
            <th style="width:10%;">Vendita</th>
            <th style="width:5%;"></th>
        </tr>
        <%
            DBManager dbManager = new DBManager();
            int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
            ArrayList<Rimanenze> magazzinoDellaFarmacia = dbManager.getRimanenzeByIdFarmacia(idFarmacia);
            ArrayList<Prodotti> prodotti = dbManager.getProdottiInMagazzino(idFarmacia);

            for(int i = 0;i < prodotti.size();i++) {
        %>


        <tr>
            <td>
                <%=prodotti.get(i).getNome()%>
            </td>
            <td>
                <%=prodotti.get(i).getCategoria()%>
            </td>
            <td>
                <%=prodotti.get(i).getCosto()%>
            </td>
            <td>
                <%=prodotti.get(i).getPrincipioAttivo()%>
            </td>
            <td>
                <%if(prodotti.get(i).isRicetta()){%>
                Si
                <%} else {%>
                No<%}%>
            </td>
            <td>
                <%=magazzinoDellaFarmacia.get(i).getQuantita()%>
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
</body>

</html>