<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Messaggio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <title>Storico messaggi</title>
    <link rel="stylesheet" href="/assets/stylesheets/css.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<div class="head">
    <img src="/assets/images/logo.png">
</div>
<div id="mySidenav" class="sidenav">
    <html:link action="/forwardToHome" styleId="uno">Home</html:link>
    <html:link action="/forwardToNuovoMessaggio" styleId="due">Nuovo messaggio</html:link>
    <a href="mailto: uejdis.dushi@gmail.com" id="tre">Contattaci</a>
    <html:link action="/logout" styleId="quattro">Log Out</html:link>
</div>

<article>

    <%
        DBManager dbManager = new DBManager();
        ArrayList<Messaggio> elencoMessaggi = dbManager.getMessaggiDaLeggere(((Login)request.getSession().getAttribute("login")).getUser(),true);
        for(Messaggio mex : elencoMessaggi) {
    %>
    <br>
    <p>From: <span><%=mex.getMittente()%></span></p>
    <p>Date: <span><%=mex.getData()%></span></p>
    <p>Body: <br><span><%=mex.getCorpo()%></span></p>
    <p>-----------------------------------------------------------------------------------------------------</p>
    <%
        }
    %>

</article>

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
