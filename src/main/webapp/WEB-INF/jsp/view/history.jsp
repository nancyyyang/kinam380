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
        </form><br/>
        <c:choose>
            <c:when test="${fn:length(historyDatabase) == 0}">
                <i>There are no history in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${historyDatabase}" var="history">
                    History ${history.id}:
                    <a href="<c:url value="/ticket/view/${history.item_id}" />">
                        <c:out value="${history.productName}" /></a>
                    (Bid Price: <c:out value="${history.bidPrice}" />)
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
</html>