<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="model.Rimanenze" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/prova-tabella.css">
    <title>Title</title>
</head>

<script>
    window.alert("Manca il controllo con js sulla quantità di vendità ammessa, manca il messaggio con il totale della spesa e manca anche il menu")
</script>

<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToVenditaTramiteOB">Vendita</html:link>
    <html:link action="/logout">Log Out</html:link>
</div>

<span style="font-size:25px;cursor:pointer;" onclick="openNav()">&#9776; Menu</span>

<input type="text" id="cercaPerNome" onkeyup="cercaPerNome()" placeholder="Cerca per nome.." title="Type in a name">
<form action="/vendita-prodotti-per-ob.do" method="post">
    <table id="tabella">
        <tr class="header">
            <th style="width:30%;">Nome</th>
            <th style="width:20%;">Categoria</th>
            <th style="width:5%;">Costo</th>
            <th style="width:20%;">Principio Attivo</th>
            <th style="width:10%;">In magazzino</th>
            <th style="width:10%;">Vendita</th>
            <th style="width:5%;"></th>
        </tr>
        <% ArrayList<Rimanenze> rimanenze = (ArrayList<Rimanenze>) request.getSession().getAttribute("vendita-prodotti-per-ob-qta");
           ArrayList<Prodotti> prodotti = (ArrayList<Prodotti>) request.getSession().getAttribute("vendita-prodotti-per-ob");
           for(int i = 0;i < rimanenze.size();i++) {
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
                <%=rimanenze.get(i).getQuantita()%>
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