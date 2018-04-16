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

        <h2>Item #${ticket.id}: <c:out value="${ticket.name}" /></h2>
        <security:authorize access="hasRole('ADMIN') or principal.username=='${ticket.customerName}'">            
            [<a href="<c:url value="/ticket/edit/${ticket.id}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="/ticket/delete/${ticket.id}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <i>Owner - <c:out value="${ticket.owner}" /></i><br /><br />
        <c:out value="${ticket.description}" /><br /><br />
        <c:if test="${fn:length(ticket.attachments) > 0}">
            Attachments:
            <c:forEach items="${ticket.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/ticket/${ticket.id}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        <a href="<c:url value="/ticket" />">Return to list tickets</a>
    </body>
</html>