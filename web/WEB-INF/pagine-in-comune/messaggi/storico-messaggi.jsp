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



<div class="elencoMessaggi">
    <%
        DBManager dbManager = new DBManager();
        ArrayList<Messaggio> elencoMessaggi = dbManager.getMessaggiDaLeggere(((Login)request.getSession().getAttribute("login")).getUser());
        if(elencoMessaggi.size()==0) {%>
    <h3>Nessun messaggio presente</h3>
    <% }
        for(int i = 0;i<elencoMessaggi.size();i++) {
    %>
<button class="accordion" onclick="mostraCorpo()"><span style="font-weight: bold;font-size: 16px;"> Da: </span><%=elencoMessaggi.get(i).getMittente()%><br><span style="font-weight: bold;font-size: 16px;"> Data: </span><%=elencoMessaggi.get(i).getData()%></button>
<div class="corpo">
    <p><%=elencoMessaggi.get(i).getCorpo()%></p>
</div>
<%
    }
%>
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
