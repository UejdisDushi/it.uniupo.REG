<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Login</title>
</head>

<body>

<%
    String message = (String)request.getAttribute("redirect");
    if(message != null) {
        if(message.equals("dati-errati")) { %>
<script>
    window.alert('Attenzione, alcuni dati non sono corretti. Riprova.');
</script>
<% }}
%>

<div class="head">
    <img src="/assets/images/logo.png">
</div>


<div id="mySidenav" class="sidenav">
    <a href="/index.jsp" id="uno">Elenco farmacie</a>
    <a href="mailto: uejdis.dushi@gmail.com" id="due">Contattaci</a>
</div>


    <form action="login.do" method="post" class="login">
        <h1 class="login">Log In</h1>
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
            <input id="user" type="text" class="form-control" name="user" placeholder="Username" required>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
            <input id="password" type="password" class="form-control" name="password" placeholder="Password" required>
        </div>
        <br><br>
       <p>
           <input value="Log In" type="submit">
       </p>
    </form>
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