<%-- 
    Document   : eBooksStoreAdminEBooks
    Created on : Nov 19, 2016, 7:36:42 PM
    Author     : gheor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Books Page</title>
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
                        <form action="${pageContext.request.contextPath}/eBookStoreAdminEBooksServlet" method="POST">
                        <sql:setDataSource 
                        var="snapshot" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshot}" var="result">
                            select a.isbn isbn, a.denumire denumire, a.pages pages, 
                                   a.pret pret, a.nr_exemplare nr_exemplare
                            from ebooks a
                            order by a.denumire                            
                        </sql:query>
                        <table border="1" width="100%">
                            <tr>
                            <td class="thc">Select</td>    
                            <td class="thc">ISBN</td>    
                            <td class="thc">Title</td>
                            <td class="thc">Pages</td>
                            <td class="thc">Price</td>
                            <td class="thc">NoCopies</td>
                            </tr>
                            <c:forEach var="row" varStatus="loop" items="${result.rows}">
                            <tr>
                                <td class="tdc"><input type="checkbox" name="admin_book_checkbox" value="${row.isbn}"></td>
                                <td class="tdc"><c:out value="${row.isbn}"/></td>
                                <td class="tdc"><c:out value="${row.denumire}"/></td>
                                <td class="tdc"><c:out value="${row.pages}"/></td>
                                <td class="tdc"><c:out value="${row.pret}"/></td>
                                <td class="tdc"><c:out value="${row.nr_exemplare}"/></td>
                            </tr>
                            </c:forEach>
                        </table>
                        <%-- Details author --%>                           
                        <sql:setDataSource 
                        var="snapshotauthor" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotauthor}" var="resultauthor">
                            SELECT SSN, FIRST_NAME, FAMILY_NAME from BOOK_AUTHOR 
                        </sql:query>                            
                        <%-- Details genres --%>                           
                        <sql:setDataSource 
                        var="snapshotgenres" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotgenres}" var="resultgenres">
                            SELECT ID, GENRE FROM EBOOKS.BOOK_GENRES 
                        </sql:query>
                        <%-- Details publisher --%>                           
                        <sql:setDataSource 
                        var="snapshotpublisher" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotpublisher}" var="resultpublisher">
                            SELECT ID, NAME FROM EBOOKS.PUBLISHERS 
                        </sql:query>
                        <%-- Details books type --%>                           
                        <sql:setDataSource 
                        var="snapshotbooktype" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotbooktype}" var="resultbooktype">
                            SELECT ID, TYPE FROM EBOOKS.BOOK_TYPES 
                        </sql:query>
                        <%-- Details bookquality --%>                           
                        <sql:setDataSource 
                        var="snapshotbookquality" 
                        driver="org.apache.derby.jdbc.ClientDriver40"
                        url="jdbc:derby://localhost:1527/ebooks;create=true;"
                        user="ebooks"  
                        password="ebooks"/>
                        <sql:query dataSource="${snapshotbookquality}" var="resultbookquality">
                            SELECT ID, QUALITY FROM EBOOKS.BOOK_PAPER_QUALITIES
                        </sql:query>                            
                        <table class="tablecenterdwithborder">
                            <tr><td>
                            <table>
                                <tr>
                                    <td> ISBN </td>
                                    <td> <input maxlength="50" size="50" type="text" name="admin_book_isbn"></input></td>
                                </tr>
                                <tr>
                                    <td> Title </td>
                                    <td> <input maxlength="50" size="50" type="text" name="admin_book_denumire"></input></td>
                                </tr>
                                <tr>
                                    <td> Pages </td>
                                    <td> <input maxlength="10" size="10" type="text" name="admin_book_pages"></input></td>
                                </tr>
                                <tr>
                                    <td> Price </td>
                                    <td> <input maxlength="10" size="10" type="text" name="admin_book_price"></input></td>
                                </tr>
                                <tr>
                                    <td> NoCopies </td>
                                    <td> <input maxlength="5" size="5" type="text" name="admin_book_nrex"></input></td>
                                </tr>
                                <tr>
                                    <td> AUTHOR: </td>
                                    <td>
                                    <select name="admin_ebooks_id_author" required="true">
                                        <c:forEach var="rowauthor" items="${resultauthor.rows}">    
                                            <option name="admin_ebooks_author" value="${rowauthor.SSN}|${rowauthor.first_name}|${rowauthor.family_name}">${rowauthor.SSN}|${rowauthor.first_name}|${rowauthor.family_name}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>                                
                                <tr>
                                    <td> ID_GENRE: </td>
                                    <td>
                                    <select name="admin_ebooks_id_genres" required="true">
                                        <c:forEach var="rowgenre" items="${resultgenres.rows}">    
                                            <option name="admin_ebooks_genres" value="${rowgenre.ID}|${rowgenre.genre}">${rowgenre.ID}|${rowgenre.genre}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td> ID_PUBLISHER: </td>
                                    <td>
                                    <select name="admin_ebooks_id_publisher" required="true">
                                        <c:forEach var="rowpublisher" items="${resultpublisher.rows}">    
                                            <option name="admin_ebooks_publisher" value="${rowpublisher.ID}|${rowpublisher.name}">${rowpublisher.ID}|${rowpublisher.name}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td> ID_BOOK_TYPES: </td>
                                    <td>
                                    <select name="admin_ebooks_id_booktype" required="true">
                                        <c:forEach var="rowbooktype" items="${resultbooktype.rows}">    
                                            <option name="admin_ebooks_booktype" value="${rowbooktype.id}|${rowbooktype.type}">${rowbooktype.id}|${rowbooktype.type}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td> ID_BOOK_QUALITY: </td>
                                    <td>
                                    <select name="admin_ebooks_id_bookquality" required="true">
                                        <c:forEach var="rowbookquality" items="${resultbookquality.rows}">    
                                            <option name="admin_ebooks_bookquality" value="${rowbookquality.id}|${rowbookquality.quality}">${rowbookquality.id}|${rowbookquality.quality}</option>
                                        </c:forEach>
                                    </select>
                                    </td>
                                </tr>
                            </table>
                            <%-- buttons --%>
                            <table>
                                    <tr><td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_ebook_insert" value="Insert"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_ebook_update" value="Update"></td>
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_ebook_delete" value="Delete"></td> 
                                        <td class="tdc"><input type="submit" class="ebooksstorebutton" name="admin_ebook_cancel" value="Cancel"></td>
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