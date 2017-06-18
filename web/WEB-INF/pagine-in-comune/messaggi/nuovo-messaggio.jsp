<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<%
    String message = (String)request.getAttribute("redirect");
    if(message != null) {
        if(message.equals("inserimento-corretto")) { %>
<script>
    window.alert('Messaggio inviato correttamente');
</script>
<% }else { %>
<script>
    window.alert('Attenzione, destinatario non valido.');
</script>
<%}}
%>


<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToHome" styleId="uno">Home</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="due">Contattaci</a>
    <html:link action="/logout" styleId="tre">Log Out</html:link>
</div>

    <%
        DBManager dbManager = new DBManager();
        String cf = dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser());
        String ruolo = dbManager.getRuoloByCF(cf);
        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        ArrayList<String> elencoNominativi = dbManager.getElencoPazientiPerMessaggi(ruolo,idFarmacia,cf);
    %>

<div class="container">
    <div class="row">
        <div class="col-sm-4 col-md-10">
            <div class="panel panel-default" id="contenitore-messaggio">
                <div class="panel-body">
                    <form accept-charset="UTF-8" action="invia-messaggio.do" method="POST">
                        To:
                        <select name="destinatario" onmouseover="deleteSelectRole()" required>
                            <option id="def" selected on>Seleziona destinatario</option>
                            <%
                                for(String s : elencoNominativi) {
                            %>
                            <option name=<%=s%>><%=s%></option>
                            <%
                                }
                            %>
                        </select>
                        <br><br>
                        <textarea class="form-control counted" name="corpo" placeholder="Inserisci il messaggio" rows="10" style="margin-bottom:10px;" required></textarea>
                        <button class="btn btn-info" type="submit">Invia messaggio</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<footer id="footer">
    <section class="text">
        <p>
            Design & production Copyright 2017 by Uejdis Dushi.
        </p>
    </section>
    <br>
</footer>
</body>
</html>
