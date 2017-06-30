<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
    <title>Attiva Collaboratore</title>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>

<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToHome" styleId="uno">Home</html:link>
    <html:link action="/forwardToReintegraMagazzino" styleId="due">Reintegra</html:link>
    <html:link action="/forwardToVendita" styleId="tre">Vendita</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="quattro">Nuovo messaggio</html:link>
    <html:link action="/forwardToVisualizzaMessaggi" styleId="cinque">Messaggi ricevuti</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="sei">Contattaci</a>
    <html:link action="/logout" styleId="sette">Log Out</html:link>
</div>

<article class="attiva">
<form action="/attiva-collaboratore.do" method="post">
    <h3>
        Inserisci collaboratore
    </h3>

        <label style="font-weight: bold;font-size: 16px;">Nome</label>
        <input type="text" name="nomePersonale" class="form-control" required placeholder="Nome collaboratore"/>
        <br>
        <label style="font-weight: bold;font-size: 16px;">Cognome</label>
        <input type="text" name="cognome" class="form-control" required placeholder="Cognome collaboratore"/>
        <br>
        <label style="font-weight: bold;font-size: 16px;">CF</label>
        <input type="text" name="cf" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required class="form-control" placeholder="CF collaboratore"/>
        <br>
        <label style="font-weight: bold;font-size: 16px;">Data di nascita</label>
        <input type="text" name="dataNascita" min="1950-01-01" max="2017-06-01" class="form-control" required placeholder="aaaa-MM-dd" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"/>
        <br>
        <label style="font-weight: bold;font-size: 16px;">Ruolo collaboratore</label>
        <select id="ddt" name="ruolo" onmouseover="deleteSelectRole()" class="form-control" style="width: 250px;margin: auto;" required>
            <option id="def" name="" selected on></option>
            <option name="df">df</option>
            <option name="ob">ob</option>
        </select>
        <br>
        <label style="font-weight: bold;font-size: 16px;">User</label>
        <input type="text" name="user" class="form-control" required placeholder="Username del collaboratore"/>
        <br>
        <label style="font-weight: bold;font-size: 16px;">Password</label>
        <input type="text" name="password" class="form-control" required placeholder="Password del collaboratore"/>
    <br>

    <input type="submit" value="Registra collaboratore" style="margin-left: 347px"/>
</form>
</article>

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