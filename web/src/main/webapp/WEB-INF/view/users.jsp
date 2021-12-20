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
    <H1>All Users:</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    <ul>
    <c:forEach var="user" items="${userList}">
            <li><p>${user}</p><form method="GET" action="./modifyUser"><input type="hidden" name="user" value="${user.username}"/><button type="submit">Select User</button></form></li>
    </c:forEach>
    </ul>
</main>


<jsp:include page="footer.jsp" />