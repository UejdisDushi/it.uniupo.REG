<!--%@ page import="reg_grails.Personale" %>!-->
<%@ page contentType="text/html;charset=UTF-8" %>

</head>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login</title>
</head>
<body>
<div class="body">
    <h1>Login</h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <form action="/views/login/login.do" method="post" >
        <div class="dialog">
            <table>
                <tbody>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="login">Login:</label>
                    </td>
                    <td valign="top">
                        <input type="text"
                               id="user" name="user"/>
                    </td> </tr>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="password">Password:</label>
                    </td>
                    <td valign="top">
                        <input type="password" id="password" name="password"/>
                    </td> </tr>
                </tbody>
            </table>
        </div>
        <div class="buttons">
            <span class="button">
                <input type="submit" value="perch" />
            </span>
        </div>
    </form>
</div>
</body>
</html>