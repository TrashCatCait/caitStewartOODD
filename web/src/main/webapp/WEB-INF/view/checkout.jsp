<%-- 
    Document   : modifyUser
    Created on : Dec 16, 2021, 5:05:18 PM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.solent.cait.oodd.dto.Roles"%>

<jsp:include page="header.jsp" />
<main role="main" class="container">

    <body>
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>
        <h4>Checkout Now</h4>
        <p>Notice changing of User names is currently not supported</p>
        <form action="./checkout" method="POST">
            <input type="hidden" name="action" value="checkout">
 
            <p>
                <label>Card Number: </label>
                <input type="text" name="cardNum" value="${user.cardNumber}"/>
            </p>
            <p>
                <label>Card Expire: </label>
                <input type="text" name="cardExpire" value="${user.cardExpire}"/>
            </p>
            <p>
                <label>Name on Card </label>
                <input type="text" name="nameOnCard" value="${user.nameOnCard}"/>
            </p>
            <p>
                <label>Card CVV(three digits on back) </label>
                <input type="text" name="cvv" value=""/>
            </p>
            <button type="submit">Checkout</button>
        </form>

    </body>


</main>
<jsp:include page="footer.jsp" />
