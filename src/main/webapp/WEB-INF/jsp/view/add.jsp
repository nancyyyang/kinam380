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

        <h2>Start A Bid</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="ticketForm">
            <form:label path="name">Name</form:label><br/>
            <form:input type="text" path="name" /><br/><br/>
            <form:label path="price">Price</form:label><br/>
            <form:input type="number" path="price" /><br/><br/>
            <form:label path="description">Description</form:label><br/>
            <form:textarea path="description" rows="5" cols="30" /><br/><br/>
            <b>Attachments</b><br/>
            <input type="file" name="attachments" multiple="multiple" accept="image/*"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>
