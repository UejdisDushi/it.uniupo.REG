<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Attiva farmacia</title>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
</head>
<body>
<header id="main">
    <nav>
        <ul>
            <li><a href="/attivaFarmacia">Attiva farmacia</a></li>
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
        <input type="text" name="nome" />
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
    <input type="submit" value="Registra farmacia"/>
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