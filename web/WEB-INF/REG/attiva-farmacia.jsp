<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Attiva farmacia</title>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<header id="main">
    <nav>
        <ul>
            <html:link action="/prova.do">Prova</html:link>
            <li><a href="/creaAccountIndividuale">Crea account individuale</a></li>
            <li><a href="/attivaFarmacia">Attività farmacia</a></li>
            <li><a href="/vendita">Vendita</a></li>
            <li><a href="/reintegra">Reintegra magazzino</a></li>
            <li><a href="/verificaCliente">Verifica cliente</a></li>

            <li>Log Out<a href="#">
                <span class="log-out"></span>
            </a></li>
        </ul>
    </nav>
</header>
<h2>
    Attiva farmacia
</h2>

<form action="/attiva-farmacia.do" method="post">
    <p>
        <label>Nome</label>
        <input type="text" name="nomeFarmacia" />
    </p>
    <p>
        <label>Indirizzo</label>
        <input type="text" name="via" />
    </p>
    <p>
        <label>Citta</label>
        <input type="text" name="citta" />
    </p>
    <p>
        <label>CAP</label>
        <input type="text" name="cap" />
    </p>
    <p>
        <label>Provincia</label>
        <input type="text" name="provincia" />
    </p>
    <p>
        <label>Numero di telefono</label>
        <input type="text" name="numeroTelefono" />
    </p>
    <br>
    <br>
    <p>
        <label>Nome del personale da attivare</label>
        <input type="text" name="nomePersonale" />
    </p>
    <p>
        <label>Cognome del personale da attivare</label>
        <input type="text" name="cognome"/>
    </p>
    <p>
        <label>CF del personale da attivare</label>
        <input type="text" name="cf" pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$" required="required"/>
    </p>
    <p>
        <label>Data di nascita del personale da attivare</label>
        <input type="date" name="dataNascita" min="1950-01-01" max="2017-06-01"/>
    </p>
    <br>
    <br>
    <p>
        <label>Nome login da assegnare al personale creato</label>
        <input type="text" name="user"/>
    </p>
    <p>
    <label>Password da assegnare al personale creato</label>
    <input type="text" name="password"/>
    </p>

    <input type="submit" value="Registra farmacia"/>
</form>



<!--
<form class="form-horizontal">
<fieldset>


<!-- change col-sm-N to reflect how you would like your column spacing (http://getbootstrap.com/css/#forms-control-sizes) -->


<!-- Form Name -->
<legend>Form Name</legend>

<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="nomeFarmacia" class="control-label col-sm-2">Nome farmacia</label>
    <div class="col-sm-10">
        <input class="form-control" id="nomeFarmacia" placeholder="Nome della farmacia" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="via" class="control-label col-sm-2">Indirizzo</label>
    <div class="col-sm-10">
        <input class="form-control" id="via" placeholder="Indirizzo della farmaccia" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="citta" class="control-label col-sm-2">Città</label>
    <div class="col-sm-10">
        <input class="form-control" id="citta" placeholder="Città della farmacia" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group form-group-sm">
    <label for="cap" class="control-label col-sm-2">CAP</label>
    <div class="col-sm-10">
        <input class="form-control" id="cap" placeholder="Cap" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="pro" class="control-label col-sm-2">Provincia</label>
    <div class="col-sm-10">
        <input class="form-control" id="pro" placeholder="Provincia della farmacia" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="numeroTelefono" class="control-label col-sm-2">Numero di telefono</label>
    <div class="col-sm-10">
        <input class="form-control" id="numeroTelefono" placeholder="Telefono della farmacia" required="" type="number">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="nomePersonale" class="control-label col-sm-2">Nome del titolare</label>
    <div class="col-sm-10">
        <input class="form-control" id="nomePersonale" placeholder="Nome del titolare" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="cognome" class="control-label col-sm-2">Cognome del titolare</label>
    <div class="col-sm-10">
        <input class="form-control" id="cognome" placeholder="Cognome del titolare" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="cf" class="control-label col-sm-2">Codice fiscale</label>
    <div class="col-sm-10">
        <input class="form-control" id="cf" placeholder="CF" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="dataNascita" class="control-label col-sm-2">Data di nascita</label>
    <div class="col-sm-10">
        <input class="form-control" id="dataNascita" placeholder="Data di nascita" required="" type="date">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="user" class="control-label col-sm-2">Username</label>
    <div class="col-sm-10">
        <input class="form-control" id="user" placeholder="Username" required="" type="text">

    </div>
</div>
<!-- Text input http://getbootstrap.com/css/#forms -->
<div class="form-group">
    <label for="password" class="control-label col-sm-2">Password</label>
    <div class="col-sm-10">
        <input class="form-control" id="password" placeholder="Password" required="" type="password">

    </div>
</div>
<!-- Button http://getbootstrap.com/css/#buttons -->
<div class="form-group">
    <label class="control-label col-sm-2" for="attivaFarmacia"></label>
    <div class="text-right col-sm-10">
        <button type="submit" id="attivaFarmacia" name="attivaFarmacia" class="btn btn-primary" aria-label="">Attiva farmacia &amp; TF</button>

    </div>
</div>


</fieldset>
</form>

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