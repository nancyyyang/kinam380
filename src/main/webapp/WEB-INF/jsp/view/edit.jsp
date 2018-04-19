<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Ticket #${ticket.id}</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="ticketForm">   
            <form:label path="name">Name</form:label><br/>
            <form:input type="text" path="name" /><br/><br/>
            <form:label path="price">Price</form:label><br/>
            <form:input type="number" path="price" /><br/><br/>
            <form:label path="description">Description</form:label><br/>
            <form:textarea path="description" rows="5" cols="30" /><br/><br/>
            <c:if test="${fn:length(ticket.attachments) > 0}">
                <b>Attachments:</b><br/>
                <ul>
                    <c:forEach items="${ticket.attachments}" var="attachment">
                        <li>
                            <c:out value="${attachment.name}" />
                            [<a href="<c:url 
                                    value="/ticket/${ticket.id}/delete/${attachment.name}"
                                    />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add attachments</b><br />
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Save"/><br/><br/>
        </form:form>
        <a href="<c:url value="/ticket" />">Return to list tickets</a>
    </body>
</html>