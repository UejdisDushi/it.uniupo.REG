<%@ page import="db.DBManager" %>
<%@ page import="model.Login" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Messaggio" %><%--
  Created by IntelliJ IDEA.
  User: Edi
  Date: 15/06/2017
  Time: 07:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Storico messaggi</title>
</head>
<body>
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


</body>
</html>
