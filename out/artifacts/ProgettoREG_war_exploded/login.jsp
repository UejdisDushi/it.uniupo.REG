<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html>
<head>
    <link rel="stylesheet" href="/assets/stylesheets/main3.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="/assets/stylesheets/main2.css">
    <script type="application/javascript" src="assets/javascripts/main.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>Login</title>
</head>

<body>
<header >
<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <a href="#">Attiva farmacia</a>
    <a href="#">Gestione messaggi</a>
</div>
</header>
<span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>



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