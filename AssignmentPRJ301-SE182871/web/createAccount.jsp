<%-- 
    Document   : createAccount.jsp
    Created on : Jul 6, 2024, 1:55:59 PM
    Author     : Ngoc Lan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}" />(6-12 chars)<br>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                    ${errors.usernameLengthErr}
                    </font> <br>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                 <font color="red">
                    ${errors.usernameIsExisted}
                    </font> <br>
            </c:if>
            Password*  <input type="password" name="txtPassword" value="${param.txtPassword}" /> (6-20 chars) <br>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}
                </font><br>
            </c:if>    
            Confirm*  <input type="password" name="txtConfirm" value="${param.txtConfirm}" />  <br>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color="red">
                ${errors.confirmNotMatched}
                </font> <br>
            </c:if>
            FullName*  <input type="text" name="txtFullname" value="${param.txtFullname}" /> (2-50 chars) <br>
            <c:if test="${not empty errors.fullNameLengthErr}">
                <font color="red">
                ${errors.fullNameLengthErr}
                </font> <br>
            </c:if>
            <input type="submit" value="Create Account" name="btAction" />
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
