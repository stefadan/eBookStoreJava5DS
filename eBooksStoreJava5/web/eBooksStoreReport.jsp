<%-- 
    Document   : eBooksStoreReport
    Created on : Oct 15, 2017, 2:58:18 PM
    Author     : iulica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Electronic Books Page</title>
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
                        <form action="${pageContext.request.contextPath}/eBookStoreReportServlet" method="POST">
                        <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshot}" var="result">
                                select b.isbn ISBN, b.denumire DENUMIRE, b.pages PAGES, 
                                       b.pret PRET, b.nr_exemplare NR_EXEMPLARE, a.copies COPIES, 
                                       a.order_date ORDER_DATE
                                from ebook_orders a, ebooks b
                                where b.isbn = a.id_isbn                            
                        </sql:query>
                        <table border="1" width="100%">
                            <tr>
                            <td class="thc">ISBN</td>    
                            <td class="thc">DENUMIRE</td>
                            <td class="thc">PRET</td>
                            <td class="thc">NR_EXEMPLARE</td>
                            <td class="thc">NR_EX_VANDUTE</td>
                            <td class="thc">ORDER_DATE</td>
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><c:out value="${row.isbn}"/></td>
                                <td class="tdc"><c:out value="${row.denumire}"/></td>
                                <td class="tdc"><c:out value="${row.pret}"/></td>
                                <td class="tdc"><c:out value="${row.nr_exemplare}"/></td>
                                <td class="tdc"><c:out value="${row.copies}"/></td>
                                <td class="tdc"><c:out value="${row.order_date}"/></td>
                            </tr>
                            </c:forEach>
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