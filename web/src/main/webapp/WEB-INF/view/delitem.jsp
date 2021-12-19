<%-- 
    Document   : users
    Created on : Dec 16, 2021, 3:07:46 PM
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

    <c:forEach var="item" items="${items}">
        <ul>
            <li><p>Item Name: ${item.name}, Current Stock: ${item.quantity}</p><form method="POST" action="./delItem"><input type="hidden" name="action" value="deleteItem"/><input type="hidden" name="itemId" value="${item.id}"/><button type="submit">Select Item</button></form></li>
        </ul>
        
        
    </c:forEach>
</main>


<jsp:include page="footer.jsp" />
