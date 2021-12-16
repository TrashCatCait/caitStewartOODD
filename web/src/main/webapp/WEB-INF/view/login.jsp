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
        <h1>Log In:</h1>
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>
        <form action="./login" method="post">
            <input type="hidden" name="action" value="login">
            <p>Username: <input type="text" name="uname" ></input></p><BR>
            <p>Password: <input type="password" name="pword" ></input></p>
            <p><button type="submit" >Log In</button></p>
        </form> 
        <a href="./register">Or make a new account</a>
    </body>


</main>
<jsp:include page="footer.jsp" />
