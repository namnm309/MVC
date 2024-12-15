<%-- 
    Document   : viewCart
    Created on : Jul 5, 2024, 1:13:37 AM
    Author     : Ngoc Lan
--%>

<%@page import="java.util.Map"%>
<%@page import="namnm.product.ProductDTO"%>
<%@page import="namnm.cart.CartBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
    </head>
    <body>
        <h1>Your Cart Include</h1>
        <c:set var="cart" value="${sessionScope.CART}" />
        <c:if test="${not empty cart && not empty cart.items}">
            <form action="DispatchServlet" method="POST">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>SKU</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${cart.items}" varStatus="counter">
                            <c:set var="dto" value="${entry.value}" />
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${dto.sku}
                                </td>
                                <td>
                                    ${dto.name}
                                </td>
                                <td>
                                    ${dto.description}
                                </td>
                                <td>
                                    ${dto.quantity}
                                </td>
                                <td>
                                    ${dto.price}
                                </td>
                                <td>
                                    ${dto.quantity * dto.price}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkItem" value="${dto.sku}" />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="7">
                                <div style="text-align: center">
                                    <a href="ShowCartListServlet">Add more to your cart</a>
                                </div>
                            </td>
                            <td>
                                <input type="submit" value="Remove Selected Items" name="btAction" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form><br>
            <form action="DispatchServlet" method="POST">
                <label for="nameCustomer">
                    Name: <input type="text" name="nameOfCustomer" id="nameCustomer" required />
                </label></br>
                <label for="email">
                    Email: <input type="email" name="emailOfCustomer" id="email" required />
                </label></br> 
                <label for="address">
                    Address: <textarea id="address" name="addressOfCustomer" rows="5" cols="20" required></textarea>
                </label></br>
                <input type="submit" value="Check Out" name="btAction" />
            </form>
        </c:if>
        <c:if test="${empty cart || empty cart.items}">
            <h2>
                <font color="red">No cart existed !!!</font>
            </h2>
        </c:if>
    </body>
</html>
