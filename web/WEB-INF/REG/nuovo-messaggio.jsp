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
        <textarea name="corpo"></textarea>
        <br>
        <br>
        <input type="submit" value="Invia messaggio"/>
    </form>
</body>
</html>
