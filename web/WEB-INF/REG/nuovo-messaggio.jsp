<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        DBManager dbManager = new DBManager();
        String cf = dbManager.getCF(((Login)request.getSession().getAttribute("login")).getUser());
        String ruolo = dbManager.getRuoloByCF(cf);
        int idFarmacia = (int)request.getSession().getAttribute("id-farmacia");
        ArrayList<String> elencoNominativi = dbManager.getElencoPazientiPerMessaggi(ruolo,idFarmacia,cf);
    %>

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
</body>
</html>
