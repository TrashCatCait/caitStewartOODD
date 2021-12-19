<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>${currentUser} Order's</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <ul>
    <c:forEach var="order" items="${orders}">
            <li><p>${order.invoiceNumber} Bought by: ${order.purchaser.username}</p><form method="GET" action="./viewOrder"><input type="hidden" name="order" value="${order.id}"/><button type="submit">Select Order</button></form></li>
    </c:forEach>
    </ul>
</main>


<jsp:include page="footer.jsp" />