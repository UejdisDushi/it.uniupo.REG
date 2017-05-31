<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
    <title>Login</title>
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
<div class="body">
    <form action="login.do" method="post" class="login">
        <h1 class="login">Log In</h1>
        <p>
            <label>Username</label>
            <input id="user" name="user" placeholder="Username" type="text" required>
        </p>
        <p>
            <label>Password</label>
            <input id="password" name="password" placeholder="Password" type="password" required>
        </p>
       <p>
           <input value="Log In" type="submit">
       </p>
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
</div>
</body>
</html>