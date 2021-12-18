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
            <c:forEach var="item" items="${availableItems}">
            <h4>${item.name}</h4>
            <h4>${item.price}</h4>
            <form action="./home" method="POST">
            <input type="hidden" name="itemId" value="${item.id}">
            <input type="hidden" name="action" value="addToCart">
            <button type="submit" >Add Item</button>
            </form>
            </c:forEach>

            <h1>Basket</h1>
            <c:forEach var="item" items="${basketItems}">
            <h4>${item.name}</h4>
            <h4>${item.price}</h4>
            <form action="./home" method="POST">
            <input type="hidden" name="itemId" value="${item.id}">
            <input type="hidden" name="action" value="removeItem">
            <button type="submit" >Remove Item</button>
            </form>
            </c:forEach>

            <form action="./home" method="POST">
                <input type="hidden" name="action" value="checkout">
                <button type="submit">Checkout</button>
            </form>
        </body>

    
</main>
<jsp:include page="footer.jsp" />
