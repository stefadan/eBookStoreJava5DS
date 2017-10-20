<%-- 
    Document   : eBookStoreAdminPublisherTag
    Created on : Oct 20, 2017, 1:19:13 AM
    Author     : iulica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/WEB-INF/tlds/publisher.tld" prefix="publisher"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Manage Publishers Page</title>
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
                        <form action="${pageContext.request.contextPath}/eBooksStoreAdminPublishersServlet" method="POST">
                        <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        
                        <sql:query dataSource="${snapshot}" var="result">
                            SELECT ID, NAME from EBOOKS.PUBLISHERS ORDER BY NAME ASC 
                        </sql:query>
                        
                        <%--
                        <publisher:Publisher
                            
                        />
                        --%>
                        <table border="1" width="100%">
                            <tr>
                            <td class="thc">select</td>    
                            <td class="thc">Publisher</td>
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><input type="checkbox" name="admin_publishers_checkbox" value="${row.id}"></td>
                                <td class="tdc"><c:out value="${row.name}"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <%-- Details --%>
                        <table class="tablecenterdwithborder">
                            <tr><td>
                            <table>
                                <tr>
                                    <td> PUBLISHER </td>
                                    <td> <input maxlength="50" size="50" type="text" name="admin_publishers_role"></input></td>
                                </tr>
                            </table>
                            <%-- buttons --%>
                            <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_publishers_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_publishers_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_publishers_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_publishers_cancel" value="Cancel"></td>
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
