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
        </form><br/>
        <form method="get" action="<c:url value="/ticket/history" />">
            <button type="submit">history</button>
        </form>

        <h2>Tickets</h2>
        <security:authorize access="hasRole('ADMIN')">    
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
        </security:authorize>
        <a href="<c:url value="/ticket/create" />">Create a Ticket</a><br /><br />

        <c:choose>
            <c:when test="${fn:length(ticketDatabase) == 0}">
                <i>There are no tickets in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${ticketDatabase}" var="ticket">
                    Ticket ${ticket.id}:
                    <a href="<c:url value="/ticket/view/${ticket.id}" />">
                        <c:out value="${ticket.name}" /></a>
                    (owner: <c:out value="${ticket.owner}" />)
                    <security:authorize access="hasRole('ADMIN') or 
                                        principal.username=='${ticket.owner}'">
                        [<a href="<c:url value="/ticket/edit/${ticket.id}" />">Edit</a>]
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">            
                        [<a href="<c:url value="/ticket/delete/${ticket.id}" />">Delete</a>]
                    </security:authorize>
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>
