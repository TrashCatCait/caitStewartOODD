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
    <H1>Create a New Account</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div><!--  -->

    <h3>${orderDetails.invoiceNumber}</h3>
    <ul>
        <c:forEach var="item" items="${orderDetails.getPurchasedItems()}">
            <li><p>Item Name: ${item.name} Price: ${item.price} Amount Bought: ${item.quantity} (Order quantity currently seems to be bugged) Order Status: ${orderDetails.status}</p></li>
            </c:forEach>
    </ul>
    <c:if test="${sessionUser.userRole =='ADMIN'}">
        <h4>User Roles & Account Status</h4>
        <form action="./modifyOrder" method="POST">
            <input type="hidden" name="action" value="manageOrder"/>

            <p> 
                <select name="orderStatus" >
                    <h3> Order Status </h3>
                    <c:forEach var="status" items="${InvoiceStatus.values()}">
                        <option value="${status}" <c:if test="${orderDetails.status == status}"> selected  </c:if>>${status}</option>
                    </c:forEach>
                </select>
            </p>
                <button type="submit">Update Order</button>
            </form>
    </c:if>
</main>


<jsp:include page="footer.jsp" />