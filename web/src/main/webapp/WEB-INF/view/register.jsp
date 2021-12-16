<%-- 
    Document   : register
    Created on : Dec 16, 2021, 12:48:19 PM
    Author     : caitlyn
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Create a New Account</H1>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>


    <p>Username must be unique and password must be at least 8 characters</p>
    <form action="./register" method="POST">
        <input type="hidden" name="action" value="createNewAccount">
        <p>Username <input type="text" name="uname" ></input></p><BR>
        <p>Password <input type="password" name="pword" ></input></p>
        <p>Re Enter Password <input type="password" name="pwordConfirm" ></input></p>
        <p><button type="submit" >Create New Account</button></p>
    </form> 

</main>


<jsp:include page="footer.jsp" />