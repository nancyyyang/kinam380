<!DOCTYPE html>
<html>
<head>
    <title>Online Bidding Website</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Edit a User</h2>
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="ticketUser">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username" disabled="true"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="password" path="password" /><br/><br/>
    <form:label path="roles">Roles</form:label><br/>
    <form:checkbox path="roles" value="ROLE_USER" />ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
    <br /><br />
    <input type="submit" value="Edit User"/>
</form:form>
</body>
</html>
