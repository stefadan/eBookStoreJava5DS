<%-- 
    Document   : eBooksStoreAdminBookAuthorPage
    Created on : Oct 8, 2017, 2:30:25 PM
    Author     : iulica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manage Authors Page</title>
        <link rel="stylesheet" type="text/css" href=".\\css\\ebookstore.css">
    </head>
    <body>
            <%-- test if actual user is authenticated and authorized --%>
        <c:choose>
                <c:when test="${validUser == true}">   
                    <%-- Build the table containing actual user roles and their roles in a master-detail view--%>
                    <%-- include menu --%>
                    <%@ include file="./utils/eBooksStoreMenu.jsp" %>
                    <%-- Master view --%>
                        <form action="${pageContext.request.contextPath}/eBooksStoreAdminBookAuthorServlet" method="POST">
                        <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshot}" var="result">
                            select SSN, FIRST_NAME, FAMILY_NAME from EBOOKS.BOOK_AUTHOR ORDER BY FIRST_NAME ASC 
                        </sql:query>
                        <table border="1" width="100%">
                            <tr>
                            <td class="thc">select</td>    
                            <td class="thc">CNP</td>
                            <td class="thc">First name</td>
                            <td class="thc">Last name</td>
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><input type="checkbox" name="admin_author_checkbox" value="${row.ssn}"></td>
                                <td class="tdc"><c:out value="${row.ssn}"/></td>
                                <td class="tdc"><c:out value="${row.first_name}"/></td>
                                <td class="tdc"><c:out value="${row.family_name}"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <%-- Details --%>
                        <table class="tablecenterdwithborder">
                            <tr><td>
                            <table>
                                <tr>
                                    <td> CNP </td>
                                    <td> <input class = "inputlarge" maxlength="13" size="13" type="text" name="admin_author_cnp"></input></td>
                                </tr>
                                <tr>
                                    <td> First name </td>
                                    <td> <input class = "inputlarge" maxlength="50" size="50" type="text" name="admin_author_fname"></input></td>
                                </tr>
                                <tr>
                                    <td> Last name </td>
                                    <td> <input class = "inputlarge" maxlength="50" size="50" type="text" name="admin_author_lname"></input></td>
                                </tr>
                            </table>
                            <%-- buttons --%>
                            <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_author_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_author_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_author_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_author_cancel" value="Cancel"></td>
                                    </tr>     
                            </table>
                            </td></tr>
                        </table>    
                        </form>
            </c:when>
            <c:otherwise>
                <c:redirect url="./index.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>                
    </body>
</html>