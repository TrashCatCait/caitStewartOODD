<%-- 
    Document   : header.jsp
    Created on : Dec 6, 2021, 5:41:43 AM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Navbar Template for Bootstrap</title>
    </head>
    <body>

            <nav class="navbar">
                <div>
                    <ul class="nav-list"> 
                        <li class="list-item"><a href="./home">Home</a></li>
                        <li class="list-item"><a href="./about">About</a></li> 
                        <li class="list-item"><a href="./contact">Contact</a></li>
                        <li class="list-item"><a href="./login">Login</a></li> 
                        <li class="list-item"><a href="./logout">Logout</a></li>
                        <li class="list-item">User: ${currentUser}</li>
                        <c:if test="${sessionUser.getUserRole()=='ADMIN'}">
                        <li class="list-item"><a href="./addItem">Add Items</a></li>
                        <li class="list-item"><a href="./delItem">Remove Item</a></li>
                        <li class="list-item"><a href="./users">All Users</a></li>
                        </c:if>
                        <c:if test="${sessionUser.getUserRole()!='ANONYMOUS'}">
                        <li class="list-item"><a href="./modifyUser?user=${sessionUser.getUsername()}">My User</a></li>
                        </c:if>
                    </ul>
                </div>
            </nav>


