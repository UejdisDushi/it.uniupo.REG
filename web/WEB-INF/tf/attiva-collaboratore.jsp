<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
    <title>Attiva Collaboratore</title>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
</head>
<body>

<h2>
    Inserisci collaboratore
</h2>

<form action="/attiva-collaboratore.do" method="post">
    <p>
        <label>Nome collaboratore</label>
        <input type="text" name="nomePersonale" />
    </p>
    <p>
        <label>Cognome collaboratore</label>
        <input type="text" name="cognome"/>
    </p>
    <p>
        <label>CF collaboratore</label>
        <input type="text" name="cf" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required="required"/>
    </p>
    <p>
        <label>Data di nascita collaboratore</label>
        <input type="date" name="dataNascita" min="1950-01-01" max="2017-06-01"/>
    </p>
    <p>
        <label>Ruolo collaboratore</label>
        <select id="ddt" name="ruolo" onmouseover="deleteSelectRole()">
            <option id="def" name="" selected on>Seleziona ruolo </option>
            <option name="df">df</option>
            <option name="ob">ob</option>
        </select>

    </p>
    <br>
    <br>
    <p>
        <label>Nome login per collaboratore</label>
        <input type="text" name="user"/>
    </p>
    <p>
        <label>Password per collaboratore</label>
        <input type="text" name="password"/>
    </p>

    <input type="submit" value="Registra collaboratore"/>
</form>



<!--
<form id="attiva-farmacia-form" class="attiva-farmacia-form" action="/attivaFarmacia.jsp" method="post">
    <!--<section class="user-account">
        <p>
            <label>Nome</label>
            <input id="nome" placeholder="Nome farmacia" type="text">
        </p>
        <p class="indirizzo">
            <label>Indirizzo</label>
            <input id="indirizzo" placeholder="Inserire indirizzo" type="text">
        </p>
        <p class="citta">
            <label>Citta'</label>
            <input id="citta" placeholder="Inserire città" type="text">
        </p>
        <p class="provincia">
            <label>Provincia</label>
            <input id="provincia" placeholder="Inserire provincia" type="text" size="20">
        </p>
        <p class="numeroDiTelefono">
            <label>Numero di telefono</label>
            <input id="numeroDiTelefono" placeholder="Inserire numero di telefono" type="text">
        </p>
        <p class="nomeTitolareFarmacista">
            <label>Nome titolare farmacista (TF)</label>
            <input id="nomeTitolareFarmacista" placeholder="Inserire nome titolare farmacista" type="text">
        </p>
        <p class="submitFarm">
            <input value="Registra farmacia" type="submit">
        </p>
    <!--</section>!-->
</form>
<footer id="footer">
    <hr>
    <section class="text">
        <p>
            Design & production Copyright 2017 by Uejdis Dushi.
        </p>
    </section>
    <br>
</footer>
</body>
</html>