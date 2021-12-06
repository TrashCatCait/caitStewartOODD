<%-- 
    Document   : home
    Created on : Dec 6, 2021, 5:42:16 AM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Home OODD CS</title>
        </head>
        <body>
            <jsp:include page="./header.jsp" /> 
            <h1><h:outputText value="Hello World!"/></h1>
        </body>
    </html>
</f:view>
