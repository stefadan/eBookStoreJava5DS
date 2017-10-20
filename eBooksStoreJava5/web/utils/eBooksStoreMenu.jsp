<%-- 
    Document   : eBooksStoreMainPage.jsp
    Author     : gheorgheaurelpacurar
    Copyright  : gheorgheaurelpacurar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link href=".\\css\\menu.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <ul id="nav">
        <c:choose>
            <c:when test="${actualUserRole == 'admin'}">
                <li><a href="#">Manage</a>
                    <ul>
                        <c:choose>
                            <c:when test="${actualUserRole == 'admin'}">
                                <li><a href="./eBooksStoreAdminUsersPage.jsp">Users</a></li>
                                <li><a href="./eBooksStoreAdminUserRolesPage.jsp">User roles</a></li>
                                <li><a href="./eBooksStoreAdminPublishersPage.jsp">Publishers</a></li>
                                <li><a href="./eBooksStoreAdminGenresPage.jsp">Genres</a></li>
                                <li><a href="./eBooksStoreAdminBookTypePage.jsp">Book type</a></li>
                                <li><a href="./eBooksStoreAdminPaperQualityPage.jsp">Paper quality</a></li>
                                <li><a href="./eBooksStoreAdminBookAuthorPage.jsp">Authors</a></li>
                                <li><a href="./eBooksStoreAdminRatingPage.jsp">Rating</a></li>
                                <li><a href="./eBooksStoreAdminEBooks.jsp">eBooks</a></li>
                                <li><a href="./eBookStoreAdminPublisherTag.jsp">Publisher-Tag</a></li>
                            </c:when>
                        </c:choose>                              
                    </ul>
                </li>
                <li><a href="#">Reports</a>
                    <ul>
                        <li><a href="./eBooksStoreReport.jsp">Sold Books</a></li>
                    </ul>
                </li>    
            </c:when>
        </c:choose>        
        <c:choose>
            <c:when test="${actualUserRole == 'user'|| actualUserRole == 'admin'}">
                <li><a href="#">Orders</a>
                    <ul>
                        <li><a href="./eBookStoreUserOrder.jsp">Orders</a></li>
                    </ul>
                </li>    
            </c:when>
        </c:choose>                        
        <li><a href="./eBooksStoreExit.jsp">Log out</a></li>
        </ul>
        <script src="js/script.js"></script>
    </body>
</html>

