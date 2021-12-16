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
            <h1>Hello World</h1>
            <div style="color:red;">${errorMessage}</div>
            <div style="color:green;">${message}</div>
            <c:forEach var="item" items="${availableItems}">
            <h4>${item.name}</h4>
            </c:forEach>
        </body>

    
</main>
<jsp:include page="footer.jsp" />
