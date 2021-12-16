<%-- 
    Document   : error
    Created on : Dec 15, 2021, 12:43:22 PM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<main role="main" class="container">

        <body>
            <h1>${error} ${status}</h1>
        </body>

    
</main>
<jsp:include page="footer.jsp" />