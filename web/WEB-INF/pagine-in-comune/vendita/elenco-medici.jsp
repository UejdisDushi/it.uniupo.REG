<%@ page import="java.util.ArrayList" %>
<%@ page import="db.DBManager" %>
<%@ page import="model.Paziente" %>
<%@ page import="model.Medico" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <script type="application/javascript" src="../../../assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Elenco medici</title>
</head>

<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>


<input type="text" id="cercaPerNome" onkeyup="cercaPerNome()" placeholder="Cerca per codice regionale.." title="Type in a name">
<form action="termina-operazione.do" method="post">
    <table id="tabella">

        <tr class="header" id="intestazione">
            <th style="width:25%;">Codice Regionale</th>
            <th style="width:25%;">Nome</th>
            <th style="width:25%;">Cognome</th>
            <th style="width:20%;">Data di nascita</th>
            <th style="width:5%;"></th>
        </tr>
        <%
            DBManager dbManager = new DBManager();
            ArrayList<Medico> elencoMedici = dbManager.getMedici();
            for(int i = 0;i < elencoMedici.size();i++) {
        %>

        <tr>
            <td>
                <%=elencoMedici.get(i).getCodiceRegionale()%>
            </td>
            <td>
                <%=elencoMedici.get(i).getNome()%>
            </td>
            <td>
                <%=elencoMedici.get(i).getCognome()%>
            </td>
            <td>
                <%=elencoMedici.get(i).getDataNascita()%>
            </td>
            <td>
                <select name="medico">
                    <option selected></option>
                    <option>Si</option>
                </select>
            </td>

        </tr>
        <% } %>

    </table>
    <br>
    <br>
    <input value="Termina operazione" type="submit" style="margin-left: 589px">
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
    if(message != null) {
        switch (message) {
            case "medico-non-selezionato": { %>
<script>        window.alert('Attenzione, nessuna medico selezionato. Riprova.'); </script>
<%break;}   case "piu-medici": { %>
<script>        window.alert('Attenzione, è stato selezionato più di un medico. Riprova.');</script>
<%}}}%>
</body>

</html>