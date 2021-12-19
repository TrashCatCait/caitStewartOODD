<%-- 
    Document   : basket
    Created on : Dec 18, 2021, 7:52:42 PM
    Author     : caitlyn
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<main role="main" class="container">

    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <body>
        <h1>Basket</h1>
        <c:forEach var="item" items="${basketItems}">
            <h4>Item Name: ${item.name}</h4>
            <h4>Price: ${item.price}</h4>
            <h4>Number: ${item.quantity}</h4>
            <form action="./basket" method="POST">
                <input type="hidden" name="itemId" value="${item.id}">
                <input type="hidden" name="itemUUID" value="${item.uuid}">
                <input type="hidden" name="action" value="removeItem">
                <button type="submit" >Remove Item</button>
            </form>
        </c:forEach>
        <h3>Total Price: £${basketTotal}</h3>

        <form action="./basket" method="POST">
            <input type="hidden" name="action" value="checkout">
            <button type="submit">Checkout</button>
        </form>
    </body>


</main>
<jsp:include page="footer.jsp" />
