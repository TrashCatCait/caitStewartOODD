<%-- 
    Document   : home
    Created on : Dec 6, 2021, 5:42:16 AM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<main role="main" class="container">

        <body>
            <h1>Items</h1>
            <div style="color:red;">${errorMessage}</div>
            <div style="color:green;">${message}</div>
            
            <h4>Search by name or item type</h4>
            <form action="./home" method="POST">
                <input type="hidden" name="action" value="search"/>
                <input type="text" name="searchTxt"/>
                <button type="submit">Search</button>
            </form>
            
            <c:forEach var="item" items="${availableItems}">
            <h4>Name: ${item.name}</h4>
            <h4>Price: ${item.price}</h4>
            <h4>Stock Remaining: ${item.quantity}</h4>
            <form action="./home" method="POST">
            <input type="hidden" name="itemId" value="${item.id}">
            <input type="hidden" name="action" value="addToCart">
            <button type="submit" >Add Item</button>
            </form>
            </c:forEach>
    
</main>
<jsp:include page="footer.jsp" />
