<%-- 
    Document   : vieworder
    Created on : Dec 18, 2021, 8:29:05 PM
    Author     : caitlyn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.solent.cait.oodd.dto.Roles"%>
<%@page import="com.solent.cait.oodd.dto.InvoiceStatus"%>


<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Order Details</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div><!--  -->

    <h3>Order Number: ${orderDetails.invoiceNumber}</h3>
    <h4>Bought by: ${orderDetails.username}</h4>
    <ul>
        <c:forEach var="item" items="${orderDetails.getPurchasedItems()}">
            <li>Item Name: ${item.name}</li>
            <li>Price: ${item.price}</li> 
            <li>Amount Bought: ${item.quantity} (Order quantity currently seems to be bugged)</li>
        </c:forEach>
            <li>Order Total: ${orderDetails.amountDue}</li>
            <li>Date Of Purchase: ${orderDetails.dateOfPurchase}</li>
        <h5>Order Status: ${orderDetails.status}</h5>

    </ul>
    <c:if test="${sessionUser.userRole =='ADMIN'}">
        <h4>User Roles & Account Status</h4>
        <form action="./modifyOrder" method="POST">
            <input type="hidden" name="action" value="manageOrder"/>
            <input type="hidden" name="orderId" value="${orderDetails.id}"/>
            <h3> Order Status </h3>
            <select name="orderStatus" >
                <c:forEach var="status" items="${InvoiceStatus.values()}">
                    <option value="${status}" <c:if test="${orderDetails.status == status}"> selected  </c:if>>${status}</option>
                </c:forEach>
            </select>
            <button type="submit">Update Order</button>
        </form>
    </c:if>
</main>


<jsp:include page="footer.jsp" />