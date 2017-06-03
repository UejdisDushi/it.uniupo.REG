<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Reintegra magazzino</title>
</head>
<body>
<div class="row">
    <div class="col-md-4" style="background-color: #E1F2B6">Nome</div>
    <div class="col-md-2" style="background-color: #b2d1ff">Categoria</div>
    <div class="col-md-1" style="background-color: #dddddd">Costo</div>
    <div class="col-md-3" style="background-color: #006dba">Principio attivo</div>
    <div class="col-md-1" style="background-color: #ffaaaa">Reintegra</div>
    <div class="col-md-1" style="background-color: #EEEFFF">Ricetta: si / no</div>
    <div class="col-md-12" style="background-color: gray"></div>
    <%
        ArrayList<Prodotti> prodotti = (ArrayList<Prodotti>) request.getSession().getAttribute("elenco-prodotti");
        for(Prodotti prodotto : prodotti) {
    %>
    <form action="/reintegra-magazzino.do" method="post">
    <div class="col-md-4"><%=prodotto.getNome() %></div>
    <div class="col-md-2"><%=prodotto.getCategoria()%></div>
    <div class="col-md-1"><%=prodotto.getCosto()%></div>
    <div class="col-md-3"><%=prodotto.getPrincipioAttivo()%></div>
    <div class="col-md-1">
        <input id="reintegra" type="text" name="reintegra" required>
    </div>
    <div class="col-md-1"><%=prodotto.isRicetta()%></div>
    <%
        }
    %>

        <p>
            <input value="Reintegra" type="submit">
        </p>
    </form>
</div>

</body>
</html>
