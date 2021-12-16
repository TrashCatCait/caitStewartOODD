<%-- 
    Document   : modifyUser
    Created on : Dec 16, 2021, 5:05:18 PM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<main role="main" class="container">

    <body>
        <h1>Hello World</h1>
        <p>Notice changing of User names is currently not supported</p>
        <h4>${user.username} Details:</h4>
        <form action="./modifyUser" method="POST">
            <p>
                <label>Username: </label>
                <input type="text" name="username" value="${user.username}" disabled/>
            </p>
            <p>
            <label>First Name: </label>
            <input type="text" name="firstName" value="${user.firstName}"/>
            </p>
            <p>
            <label>Last Name: </label>
            <input type="text" name="secondName" value="${user.secondName}"/>
            </p>
            <p>
            <label>Address Line 1: </label>
            <input type="text" name="addressLine1" value="${user.address.addressLine1}"/>
            </p>
            <p>
            <label>Address Line 2: </label>
            <input type="text" name="addressLine2" value="${user.address.addressLine2}"/>
            </p>
            <p>
            <label>House Number: </label>
            <input type="text" name="houseNumber" value="${user.address.houseNumber}"/>
            </p>
            <p>
            <label>Postal Code: </label>
            <input type="text" name="postcode" value="${user.address.postcode}"/>
            </p>
            <p>
            <label>Country: </label>
            <input type="text" name="country" value="${user.address.country}"/>
            </p>
            <p>
            <label>City: </label>
            <input type="text" name="city" value="${user.address.city}"/>
            </p>
            <p>
            <label>County: </label>
            <input type="text" name="county" value="${user.address.county}"/>
            </p>
            <p>
            <label>Mobile </label>
            <input type="text" name="mobile" value="${user.address.mobile}"/>
            </p>
            <p>
            <label>Telephone: </label>
            <input type="text" name="telephone" value="${user.address.telephone}"/>
            </p>
            <button type="submit">Update User Details</button>
        </form>
    </body>


</main>
<jsp:include page="footer.jsp" />
