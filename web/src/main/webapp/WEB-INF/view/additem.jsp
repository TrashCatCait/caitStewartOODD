<%-- 
    Document   : login
    Created on : Dec 16, 2021, 12:43:20 PM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<main role="main" class="container">

    <body>
        <h1>Add Item:</h1>
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>
        <form action="./addItem" method="POST">
            <input type="hidden" name="action" value="addNewItem">
	    <p>Item Name: <input type="text" name="itemName" ></input></p>
            <p>Item Quantity: <input type="number" name="itemQuantity" ></input></p>
            <p>Item Price: <input type="number" name="itemPrice" step="0.01" ></input></p>
            <p><button type="submit" >Update Catalog</button></p>
        </form> 
    </body>


</main>
<jsp:include page="footer.jsp" />
