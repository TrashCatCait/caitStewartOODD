<%-- 
    Document   : orders
    Created on : Dec 18, 2021, 7:52:33 PM
    Author     : caitlyn
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>All System Orders</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <h4>Search By Username Or order id</h4>
    <form action="./orders" method="POST">
        <input type="hidden" name="action" value="search"/>
        <input type="text" name="searchStr"/>
        <button type="submit">Search</button>
    </form>

    <ul>
        <c:forEach var="order" items="${orders}">
            <li><p>Order ID: ${order.invoiceNumber}</p></li> 
            <li><p>Bought by: ${order.purchaser.username}</p></li>
            <form method="GET" action="./viewOrder"><input type="hidden" name="order" value="${order.id}"/><button type="submit">Select Order</button></form>
        </c:forEach>
    </ul>
</main>


<jsp:include page="footer.jsp" />