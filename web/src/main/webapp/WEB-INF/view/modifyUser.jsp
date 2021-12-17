<%-- 
    Document   : modifyUser
    Created on : Dec 16, 2021, 5:05:18 PM
    Author     : caitlyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.solent.cait.oodd.dto.Roles"%>

<jsp:include page="header.jsp" />
<main role="main" class="container">

    <body>
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>
        <p>Notice changing of User names is currently not supported</p>
        <h4>${user.username} Details:</h4>
        <form action="./modifyUser" method="POST">
            <input type="hidden" name="action" value="modifyUser">
            <input type="hidden" name="username" value="${user.username}"/>

            <p>
                <label>First Name: </label>
                <input type="text" name="fname" value="${user.firstName}"/>
            </p>
            <p>
                <label>Last Name: </label>
                <input type="text" name="sname" value="${user.secondName}"/>
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
            <button type="submit">Update ${user.username}'s Details</button>
        </form>

        <h4>Change Password</h4>
        <form action="./modifyUser" method="POST">
            <input type="hidden" name="action" value="updatePassword">
            <input type="hidden" name="username" value="${user.username}"/>
            <p><label>Password: </label><input type="password" name="password"/></p>
            <p><label>Confirm Password: <input type="password" name="passwordConfirm"/></p>
            <button type="submit">Update ${user.username}'s password</button> 
        </form>

        <c:if test="${sessionUser.userRole =='ADMIN'}">
            <h4>User Roles & Account Status</h4>
            <form action="./modifyUser" method="POST">
                <input type="hidden" name="action" value="manageAccount"/>
                <input type="hidden" name="username" value="${user.username}"/>

                <p> 
                    <select name="userRole" >
                        <c:forEach var="role" items="${Roles.values()}">
                            <option value="${role}" <c:if test="${user.userRole == role}"> selected  </c:if>>${role}</option>
                        </c:forEach>
                    </select>
                </p>
                <p>
                    <select name="accountEnabled">
                        <option value="true" <c:if test="${user.enabled}"> selected  </c:if> >ENABLED</option>
                        <option value="false" <c:if test="${!user.enabled}"> selected  </c:if> >DISABLED</option>
                        </select>
                    </p>
                    <button type="submit">Update User</button>
                </form>
        </c:if>
        <c:if test="${sessionUser.userRole=='CUSTOMER'}">
            <form action="./modifyUser" method="POST">
                <input type="hidden" name="action" value="disableAccount"/>
                <input type="hidden" name="username" value="${user.username}"/>
                <button type="submit">Disable my account</button>
            </form>
        </c:if>
    </body>


</main>
<jsp:include page="footer.jsp" />
