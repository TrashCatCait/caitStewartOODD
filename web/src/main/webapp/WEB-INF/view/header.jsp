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
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">
        <!--<link rel="canonical" href="https://getbootstrap.com/docs/3.3/examples/navbar/">-->

        <title>Navbar Template for Bootstrap</title>
    </head><!-- comment -->
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
                    </ul>
                </div>
            </nav>


