<%@ page import="model.Farmacia" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
    <title>Farmacie-presenti</title>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
</head>
<body>
<header id="main">
    <nav>
        <ul>
            <li>
                <html:link action="/lista-farmacie">Farmacie</html:link>
            </li>
            <li>
                <a href="/login.jsp">Area Riservata</a>
            </li>
        </ul>
    </nav>
</header>
<div id="wrapper"/>
<h1>
<%
    List<Farmacia> farmacie = (List<Farmacia>) request.getAttribute("farmacie");
    if (farmacie == null)
    {
        %>
    <script>
        alert("Attenzione: nessuna farmacia presente nel sistema");
        window.location='/login.jsp'
    </script>
    <%
        }else {
    for (Farmacia farmacia : farmacie)
    {
%>

<%=farmacia.getNome()%>
</h1>
    <p><%=farmacia.getVia()%></p>
    <p><%=farmacia.getCitta()%></p>
    <p><%=farmacia.getProvincia()%></p>
    <p><%=farmacia.getCap()%></p>
    <p><%=farmacia.getNumeroTelefono()%></p>
<%
    } }
%>
<div class="pushfooter"></div>
</div>
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
