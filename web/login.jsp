<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <title>Login</title>
</head>

<body>
    <div class="head">
        <img src="/assets/images/logo.png">
    </div>

    <div id="mySidenav" class="sidenav">
        <a href="/index.jsp" id="uno">Elenco farmacie</a>
        <a href="mailto: uejdis.dushi@gmail.com" id="due">Contattaci</a>
    </div>

    <form action="login.do" method="post" class="login">
        <h1 class="login">Log In</h1>
            <input id="user" type="text" name="user" placeholder="Username" class="form-control"required>
            <br>
            <input id="password" type="password"  name="password" placeholder="Password" class="form-control" required>
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

    <%
        String message = (String)request.getAttribute("redirect");
        if(message != null) {
            if(message.equals("dati-errati")) { %>
    <script>
        window.alert('Attenzione, alcuni dati non sono corretti. Riprova.');
    </script>
    <% }}
    %>
</body>
</html>