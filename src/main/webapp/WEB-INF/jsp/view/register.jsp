<!DOCTYPE html>
<html>
    <head>  
        <title>Online Bidding System Register</title>
    </head>
    <body>
        <h2>Online Bidding System Register</h2>
        <form:form method="POST" enctype="multipart/form-data"
           modelAttribute="userRegister">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username" /><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:password path="password" /><br/><br/>
    <form:label path="pwAgain">Confirm Password</form:label><br/>
    <form:password path="pwAgain" /><br/><br/>
    <form:hidden path="role" value="ROLE_USER"/>
    
            <input type="submit" value="Register"/>
    </form:form>
    </body>
</html>
