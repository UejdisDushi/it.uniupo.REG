<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/assets/stylesheets/main3.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
</head>
<body>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <html:link action="/forwardToHome">Home</html:link>
    <html:link action="/logout">Log Out</html:link>
</div>

<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>


    <%
        DBManager dbManager = new DBManager();
        String cf = dbManager.getCFByUsername(((Login)request.getSession().getAttribute("login")).getUser());
        String ruolo = dbManager.getRuoloByCF(cf);
        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        ArrayList<String> elencoNominativi = dbManager.getElencoPazientiPerMessaggi(ruolo,idFarmacia,cf);
    %>
    <form action="invia-messaggio.do" method="post">
        <select name="destinatario">
            <br>
            <%
                for(String s : elencoNominativi) {
            %>
            <option name=<%=s%>><%=s%></option>
            <%
                }
            %>
        </select>
        <textarea name="corpo" required></textarea>
        <br>
        <br>
        <input type="submit" value="Invia messaggio"/>
    </form>
</body>
</html>
