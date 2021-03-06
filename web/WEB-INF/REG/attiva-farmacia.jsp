<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Attiva farmacia</title>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
</head>
<body>

<%
    DBManager db = new DBManager();
    if(!db.getRuoloByCF(db.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser())).equals("")) {
        response.sendRedirect("/login.jsp");
        return;
    }
%>

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

<article class="attiva">
    <form action="/attiva-farmacia.do" method="post">
        <div class="left">
            <h3 style="font-size: 23px;">
                Anagrafica farmacia
            </h3>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Nome</label>
                <input type="text" name="nomeFarmacia" placeholder="Nome farmacia" class="form-control" required/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Indirizzo</label>
                <input type="text" name="via" placeholder="Indirizzo" class="form-control" required/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Citta</label>
                <input type="text" name="citta" placeholder="Città" class="form-control" required/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">CAP</label>
                <input type="text" name="cap" placeholder="CAP" class="form-control" required pattern="[0-9]{5}"/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Provincia</label>
                <input type="text" name="provincia" placeholder="Provincia" class="form-control" required pattern="[a-zA-Z]{2}"/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Numero di telefono</label>
                <input type="text" name="numeroTelefono" placeholder="Numero di telefono" class="form-control" required/>
            </p>
        </div>
        <div class="right">
            <h3 style="font-size: 23px;">
                Anagrafica titolare
            </h3>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Nome</label>
                <input type="text" name="nomePersonale" class="form-control" placeholder="Nome titolare farmacia" required/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Cognome</label>
                <input type="text" name="cognome" class="form-control" placeholder="Cognome titolare farmacia" required/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;text-transform: uppercase; ">CF</label>
                <input type="text" name="cf" class="form-control" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required="required" placeholder="CF del titolare"/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Data di nascita</label>
                <input type="text" name="dataNascita" min="1950-01-01" max="2017-06-01" class="form-control" required placeholder="aaaa-MM-dd" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">User</label>
                <input type="text" name="user" class="form-control" required placeholder="Username del titolare"/>
            </p>
            <p>
                <label style="font-weight: bold;font-size: 17px;">Password</label>
                <input type="text" name="password" class="form-control" required placeholder="Password del titolare"/>
            </p>
        </div>
        <input type="submit" value="Registra farmacia"/>
    </form>
</article>
<div id="wrapper"></div>
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
        if(message.equals("inserimento-corretto")) { %>
<script>
    window.alert('Inserimento avvenuto con successo');
</script>
<% }else { %>
<script>
    window.alert('Attenzione, alcuni dati non sono corretti. Riprova.');
</script>
<%}}
%>

</body>
</html>
