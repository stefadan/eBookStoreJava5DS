<%-- 
    Document   : eBookStoreUserOrder
    Created on : Oct 13, 2017, 4:19:28 PM
    Author     : iulica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="/WEB-INF/tlds/conectare.tld" prefix="conectare"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Store Order Page</title>
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
                        
                   <form action="${pageContext.request.contextPath}/eBookStoreUserOrderServlet" method="POST">
                       <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                    
                    <%--
                    <conectare:Databaseconnection 
                             connection="snapshot"  
                             driver="org.apache.derby.jdbc.ClientDriver40" 
                             url="jdbc:derby://localhost:1527/ebooks;create=true;" 
                             username="ebooks"   
                             password="ebooks"                        
                    /> 
                    --%>
                    <sql:query dataSource="${snapshot}" var="result">
                            select ID, ID_SSN, ID_ISBN, COPIES, ORDER_DATE from EBOOKS.EBOOK_ORDERS order by order_date desc 
                        </sql:query>
                        <table border="1" width="100%">
                            <tr>
                            <td class="thc">select</td>    
                            <td class="thc">ID_SSN</td>
                            <td class="thc">ID_ISBN</td>
                            <td class="thc">Copies</td>
                            <td class="thc">Order Date</td>
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><input type="checkbox" name="admin_orders_checkbox" value="${row.id}"></td>
                                <td class="tdc"><c:out value="${row.id_ssn}"/></td>
                                <td class="tdc"><c:out value="${row.id_isbn}"/></td>
                                <td class="tdc"><c:out value="${row.copies}"/></td>
                                <td class="tdc"><c:out value="${row.order_date}"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <%-- Details --%>
                        <sql:setDataSource 
                        var="snapshotorders" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotorders}" var="resultorders">
                            SELECT A.ISBN ISBN, A.DENUMIRE DENUMIRE, A.PAGES PAGES, 
                                   A.PRET PRICE, B.NAME PUBLISHER 
                            FROM EBOOKS.EBOOKS a, PUBLISHERS b
                            WHERE a.id_publisher = b.id
                        </sql:query>                            
                        <table class="tablecenterdwithborder">
                            <tr><td>
                            <table>
                                <tr>
                                    <td> EBOOK: </td>
                                    <td>
                                    <select name="user_ebooks_orders" required="true">
                                        <c:forEach var="roworders" items="${resultorders.rows}">    
                                            <option name="user_ebooks_roworders" value="${roworders.isbn}|${roworders.denumire}|${roworders.pages}|${roworders.price}|${roworders.publisher}">${roworders.isbn}|${roworders.denumire}|${roworders.pages}|${roworders.price}|${roworders.publisher}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>                                                                
                                <tr>
                                    <td> No. of copies </td>
                                    <td> <input maxlength="10" size="10" type="text" name="user_order_copies"></input></td>
                                </tr>
                            </table>
                            <%-- buttons --%>
                            <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_order_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_order_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_order_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_order_cancel" value="Cancel"></td>
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