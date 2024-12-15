<%-- 
    Document   : market
    Created on : Jul 5, 2024, 1:24:32 AM
    Author     : Ngoc Lan
--%>

<%@page import="java.util.List"%>
<%@page import="namnm.product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Car List</h1>
        <c:if test="${not empty requestScope.CAR_LIST}">
            <c:set var="list" value="${requestScope.CAR_LIST}" />
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>SKU</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Available Quantity</th>
                        <th>Add to your Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${list}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${product.sku}</td>
                            <td>${product.name}</td>
                            <td>${product.description}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <input type="hidden" name="sku" value="${product.sku}" />
                                    <input type="hidden" name="name" value="${product.name}" />
                                    <input type="hidden" name="description" value="${product.description}" />
                                    <input type="hidden" name="price" value="${product.price}" />
                                    <input type="hidden" name="quantity" value="${product.quantity}" />
                                    <input type="submit" value="Add to your cart" name="btAction" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form action="DispatchServlet" method="POST">
                <input type="submit" value="View your cart" name="btAction" />
            </form>
        </c:if>
    </body>
</html>
