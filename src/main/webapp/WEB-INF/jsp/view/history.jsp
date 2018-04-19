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
                <ol>
                    <c:forEach items="${historyDatabase}" var="history">

                        <li><a href="<c:url value="/ticket/view/${history.item_id}" />">
                                <c:out value="${history.productName}" /></a>
                            (Bid Price: <c:out value="${history.bidPrice}" />)</li>
                        <br/>
                    </c:forEach>
                </ol>
            </c:otherwise>
        </c:choose>
        <br><a href="<c:url value="/ticket" />">Return to list tickets</a>
    </body>
</html>
