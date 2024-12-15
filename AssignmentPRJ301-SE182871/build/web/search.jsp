<%-- 
    Document   : search
    Created on : Jul 2, 2024, 11:00:52 AM
    Author     : Ngoc Lan
--%>
<%@page import="javax.servlet.http.Cookie"%>
<%@page import="namnm.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!--Add cookie bên LoginServlet xong thì welcom , -->
        <!--Khi cookie tồn tại thì welcome -->
        <font color="red">
        Welcome, ${sessionScope.USER.fullName}
        </font>

        <h1>Search Page</h1>
        <form action="DispatchServlet" method="POST">
            Search Value: <input type="text" name="txtSearchValue" value="${param.txtSearchValue != null ? param.txtSearchValue : ''}" /><br/>
            <input type="submit" value="Search" name="btAction" />
            <input type="submit" value="Logout" name="btAction" />
        </form><br/>

        <c:set var="searchValue" value="${param.txtSearchValue}" />
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCHRESULT}" />
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            <tr>
                                <form action="DispatchServlet">
                                    <td>${counter.count}</td>
                                    <td>${dto.username}
                                        <input type="hidden" name="txtUsername" value="${dto.username}" />
                                    </td>
                                    <td>
                                        <input type="text" name="txtPassword" value="${dto.password}" />
                                    </td>
                                    <td>${dto.fullName}</td>
                                    <td>
                                        <input type="checkbox" name="chkAdmin" value="ON" 
                                               <c:if test="${dto.role}">checked="checked"</c:if> />
                                    </td>
                                    <td>
                                        <c:url var="urlRewriting" value="DispatchServlet">
                                            <c:param name="btAction" value="delete" />
                                            <c:param name="username" value="${dto.username}" />
                                            <c:param name="lastSearchValue" value="${searchValue}" />
                                        </c:url>
                                        <a href="${urlRewriting}">Delete</a>
                                    </td>
                                    <td>
                                        <input type="submit" value="Update" name="btAction" />
                                        <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                    </td>
                                </form>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty result}">
                <h2>
                    <font color="red">No record is matched!!!</font>
                </h2>
            </c:if>
        </c:if>

        <%--<%
        <font color="red">Welcome , ${sessionScope.USER.fullName}</font>
        <h1>Search Page</h1>
        <form action="DispatchServlet"> 
            Search Value <input type="text" name="txtSearchValue" 
                                value="<%= (request.getParameter("txtSearchValue") != null) ? request.getParameter("txtSearchValue") : "" %>" /><br/>
            <input type="submit" value="Search" name="btAction" />
        </form><br/>
        --%>

        <%-- 
            String searchValue = request.getParameter("txtSearchValue");

            if (searchValue != null) { //Khác với servlet bên servlet chắc chăc khác null vì có form điền vào r 

                List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCHRESULT");

                if (result != null) { 
        --%>
        <%-- 
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Full Name</th>
                    <th>Role</th>
                    <th>Delete</th> <!--Thêm chức năng delete cho giao diện -->
                    <th>Update</th> 
                </tr>
            </thead> 
            <tbody>
                <!-- Mỗi phần tử của result kiểu dữ liệu là RegistrationDTO -->
                <% int count = 0; //đếm stt cho No.
                for (RegistrationDTO dto : result) { 
                    //Delete
                    String urlRewriting = "DispatchServlet"
                            + "?btAction=Delete"
                            + "&username=" + dto.getUsername()
                            + "&lastSearchValue=" + searchValue;
                %>
                <form action="DispatchServlet">
                    <tr>
                        <td><%= ++count %></td>
                        <td><%= dto.getUsername() %>
                            <input type="hidden" name="txtUsername" value="<%= dto.getUsername() %>" />
                        </td>
                        <td><input type="text" name="txtPassword" value="<%= dto.getPassword() %>" /></td>
                        <td><%= dto.getFullName() %></td>
                        <td>
                            <input type="checkbox" name="chkAdmin" value="ON" <% if (dto.isRole()) { %> checked="checked" <% } %> />
                        </td>
                        <td><a href="<%= urlRewriting %>">Delete</a></td>
                        <td>
                            <input type="submit" value="Update" name="btAction" />
                            <input type="hidden" name="lastSearchValue" value="<%= searchValue %>" />
                        </td>
                    </tr>
                </form>
                <% } //end for traversing dto %>
            </tbody>
        </table>
        --%>
        <%-- 
                } else { 
        --%> 
        <%-- 
        <h2>No record is matched !!!</h2>           
        <%-- 
                } //check first time for search value 
        --%> 
    </body>
</html>
