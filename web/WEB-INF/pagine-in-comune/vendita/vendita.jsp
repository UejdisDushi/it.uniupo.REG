<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotti" %>
<%@ page import="model.Rimanenze" %>
<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <title>Vendita</title>
</head>

<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToHome" styleId="uno">Home</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="due">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="tre">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="quattro">Contattaci</a>
    <html:link action="/logout" styleId="cinque">Log Out</html:link>
</div>


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
            if(dbManager.getRuoloByCF(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser())).equals("ob")) {
                response.sendRedirect("/login.jsp");
                return;
            }
            if(dbManager.getRuoloByCF(dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser())).equals("")) {
                response.sendRedirect("/login.jsp");
                return;
            }
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
                <input type="number" min="1" name="quantita" class="form-control">
            </td>
            <td>
                <input img src="/assets/images/icona-carrello.png" type="image" value="submit">
            </td>
        </tr>
        <% } %>

    </table>

</form>

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
    String totale = (String) request.getAttribute("totale");
    if(message != null) {
        if(message.equals("valore-non-consentito")) { %>
<script>
    window.alert('Attenzione, è stata selezionata una quantità non ammessa.');
</script>
<%} else if(message.equals("nessuna-quantità")) { %>
<script>
    window.alert('Attenzione, nessuna quantità selezionata. Riprova.');
</script>
<%}}
    if(totale != null) {%>
<script>
    window.alert('Totale vendita: ' + <%=totale%> + '€');
</script>
<%
    }
%>

</body>

</html>