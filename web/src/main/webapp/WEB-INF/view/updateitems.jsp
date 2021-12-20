<%-- 
    Document   : updateitems
    Created on : Dec 19, 2021, 4:46:36 PM
    Author     : caitlyn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Update Item Details</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <c:if test="${newItem == null}">
    <c:forEach var="item" items="${items}">
        <ul>
            <li><p>Item Name: ${item.name}, Current Stock: ${item.quantity}</p><form method="GET" action="./updateItem"><input type="hidden" name="action" value="findItem"/><input type="hidden" name="itemId" value="${item.id}"/><button type="submit">Select Item</button></form></li>
        </ul>
    </c:forEach>
    </c:if>
    <c:if test="${newItem != null}">
        <form method="POST" action="./updateItem">
            <input type="hidden" name="action" value="updateItem"/>
            <input type="hidden" name="itemId" value="${newItem.id}"/>
            <p>Item Name: <input type="text" name="itemName" value="${newItem.name}"/></p>
            <p>Item Quantity: <input type="number" name="itemQuantity" value="${newItem.quantity}"/></p>
            <p>Item Price: <input type="number" name="itemPrice" step="0.01" value="${newItem.price}"/></p>
            <p>Item Tag: <input type="text" name="itemType" value="${newItem.type}"/></p>
            <button type="submit">Update Item</button>
        </form>
    </c:if>
</main>


<jsp:include page="footer.jsp" />
