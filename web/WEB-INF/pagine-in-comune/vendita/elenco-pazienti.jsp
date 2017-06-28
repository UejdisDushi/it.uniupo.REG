<%@ page import="java.util.ArrayList" %>
<%@ page import="db.DBManager" %>
<%@ page import="model.Paziente" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Elenco pazienti</title>
</head>

<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>


<input type="text" id="cercaPerNome" onkeyup="cercaPerNome()" placeholder="Cerca per codice fiscale.." title="Type in a name">
<form action="/check-medico.do" method="post">
    <table id="tabella">

        <tr class="header" id="intestazione">
            <th style="width:25%;">Codice Fiscale</th>
            <th style="width:20%;">Nome</th>
            <th style="width:20%;">Cognome</th>
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
                <select name="cliente">
                    <option selected></option>
                    <option>Si</option>
                </select>
            </td>

        </tr>
        <% } %>

    </table>
    <br>
    <br>
    <input value="Check Medico" type="submit">
</form>

<button onclick="nuovaRiga()" id="nuovoPaziente">Inserisci nuovo paziente</button>

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
        if(message.equals("nessun-paziente")) { %>
<script>
    window.alert('Attenzione, nessuna paziente selezionato. Riprova.');
</script>
<%} else {
%>
<script>
    window.alert('Attenzione, non è possibile inserire un paziente già presente.');
</script>
<% }}%>
</body>

</html>