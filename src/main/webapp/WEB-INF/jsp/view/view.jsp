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
        <security:authorize access="hasRole('ADMIN') or principal.username=='${ticket.owner}'">            
            [<a href="<c:url value="/ticket/edit/${ticket.id}" />">Edit</a>]
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">            
            [<a href="<c:url value="/ticket/delete/${ticket.id}" />">Delete</a>]
        </security:authorize>
        <br /><br />
        <i>Owner : <c:out value="${ticket.owner}" /></i><br /><br />
        Expected Price : $<c:out value="${ticket.price}" /><br /><br />
        Current Price : $<c:out value="${ticket.current_price}" /><br /><br />
        Number of bids : <c:out value="${ticket.num_of_bid}" /><br /><br />
        Status : <c:out value="${ticket.status}" /><br /><br />
        Description : <c:out value="${ticket.description}" /><br /><br />
        <c:if test="${ticket.winner!=""}">
            Winner : <c:out value="${ticket.winner}" /><br /><br /></c:if>
        <c:if test="${fn:length(ticket.attachments) > 0}">
            Attachments:
            <c:forEach items="${ticket.attachments}" var="attachment"
                       varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/ticket/${ticket.id}/attachment/${attachment.name}" />">
                    <c:out value="${attachment.name}" /></a>
            </c:forEach><br /><br />
        </c:if>
        Comments : 
        <c:if test="${fn:length(comments) > 0}">

            <ul>
            <c:forEach items="${comments}" var="comment">
                <li>${comment.content} by ${comment.name} [<a href="<c:url value="${ticket.id}/deleteComment/${comment.id}" />">Delete Comment</a>]</li>
            </c:forEach>
                </ul>
        </c:if><br>
        [<a href="<c:url value="comment/${ticket.id}" />">Add a Comment</a>]
         <form:form method="POST" enctype="multipart/form-data" modelAttribute="ticketForm">
            <form:label path="bidPrice">Bid Price:</form:label><br/>
            <form:input type="number" path="bidPrice" min="${ticket.current_price+1}" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" value="Submit"/>
        </form></form:form>
        <a href="<c:url value="/ticket" />">Return to list tickets</a>
    </body>
</html>