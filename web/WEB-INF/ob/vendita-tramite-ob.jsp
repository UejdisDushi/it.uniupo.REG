<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="model.Rimanenze" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script type="application/javascript" src="../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/prova-tabella.css">
    <title>Title</title>
</head>
<body>

<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Cerca per nome.." title="Type in a name">
<form action="/vendita-prodotti-per-ob.do" method="post">
<table id="myTable">
    <tr class="header">
        <th style="width:30%;">Nome</th>
        <th style="width:20%;">Categoria</th>
        <th style="width:5%;">Costo</th>
        <th style="width:20%;">Principio Attivo</th>
        <th style="width:10%;">In magazzino</th>
        <th style="width:10%;">Vendita</th>
        <th style="width:5%;"></th>
    </tr>
    <%
        ArrayList<Rimanenze> rimanenze = (ArrayList<Rimanenze>) request.getSession().getAttribute("vendita-prodotti-per-ob-qta");
        ArrayList<Prodotti> prodotti = (ArrayList<Prodotti>) request.getSession().getAttribute("vendita-prodotti-per-ob");
        for(int i = 0;i<rimanenze.size();i++)
        {
    %>


        <tr>
            <td><%=prodotti.get(i).getNome()%></td>
            <td><%=prodotti.get(i).getCategoria()%></td>
            <td><%=prodotti.get(i).getCosto()%></td>
            <td><%=prodotti.get(i).getPrincipioAttivo()%></td>
            <td><%=rimanenze.get(i).getQuantita()%></td>
            <td><input type="number" min="0" name="quantita"></td>
            <td><input img src="/assets/images/icona-carrello.png"  type="image" value="submit"></td>
        </tr>
        <%
            }
        %>

</table>

</form>
</body>

</html>
