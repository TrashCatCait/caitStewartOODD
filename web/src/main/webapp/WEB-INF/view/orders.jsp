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
    <H1>Create a New Account</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <ul>
    <c:forEach var="order" items="${orders}">
            <li><p>${order}</p><form method="GET" action="./viewOrder"><input type="hidden" name="order" value="${order.id}"/><button type="submit">Select User</button></form></li>
    </c:forEach>
    </ul>
</main>


<jsp:include page="footer.jsp" />