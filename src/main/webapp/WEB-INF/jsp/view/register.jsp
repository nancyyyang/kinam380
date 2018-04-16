<%-- 
    Document   : register
    Created on : Apr 16, 2018, 3:33:56 PM
    Author     : s1140698
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bidding Website Register</title>
    </head>
    <body>
        <h2>Bidding Website Register</h2>
        <form action="register" method="POST">
            <label for="username">Username:</label><br/>
            <input type="text" id="username" name="username" /><br/><br/>
            <label for="password">Password:</label><br/>
            <input type="password" id="password" name="password" /><br/><br/>
            <label for="pwAgain">Confirm password:</label><br/>
            <input type="pwAgain" id="pwAgain" name="pwAgain" /><br/><br/>
            <input type="hidden" name="role" value="ROLE_USER"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Register"/>
        </form>
    </body>
</html>
