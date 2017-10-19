/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helper.NextValue;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class eBookStoreAdminEBooksServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            // declare specific DB info
            String user = "ebooks" ;
            String password = "ebooks";
            String url = "jdbc:derby://localhost:1527/ebooks;create=true;";
            String driver = "org.apache.derby.jdbc.ClientDriver";  
            // check push on Insert button
            if (request.getParameter("admin_ebook_insert") != null) { // insert values from fields
                // set connection paramters to the DB
                // read values from page fields                
                String isbn = request.getParameter("admin_book_isbn");
                String denumire = request.getParameter("admin_book_denumire");
                String price = request.getParameter("admin_book_price");
                String pages = request.getParameter("admin_book_pages");
                String copies = request.getParameter("admin_book_nrex");
                String id_type = request.getParameter("admin_ebooks_id_booktype");
                String vid_type = id_type.substring(0, id_type.indexOf('|'));
                String id_genre = request.getParameter("admin_ebooks_id_genres");
                String vid_genre = id_genre.substring(0, id_genre.indexOf('|'));
                String id_quality = request.getParameter("admin_ebooks_id_bookquality");
                String vid_quality = id_quality.substring(0, id_quality.indexOf('|'));
                String id_publisher = request.getParameter("admin_ebooks_id_publisher");
                String vid_publisher = id_publisher.substring(0, id_publisher.indexOf('|'));
                String cnpAuthor = request.getParameter("admin_ebooks_id_author");
                String vcnpAuthor = cnpAuthor.substring(0, cnpAuthor.indexOf('|'));
                
//                String isbn = "978-101-33-1228-8";//request.getParameter("admin_book_isbn");
//                String denumire = "Denumire";//request.getParameter("admin_book_denumire");
//                String price = "15"; //request.getParameter("admin_book_price");
//                String pages = "100";//request.getParameter("admin_book_pages");
//                String copies = "15";//request.getParameter("admin_book_nrex");
//                String id_genre = "2"; //request.getParameter("admin_ebooks_id_genres");
//                String id_quality = "1"; //request.getParameter("admin_ebooks_id_bookquality");
//                String id_publisher = "5"; //request.getParameter("admin_ebooks_id_publisher");
//                String id_type = "2"; //request.getParameter("admin_ebooks_id_booktype");
                
                NextValue nextVal = new NextValue(); 
                String tableName = "EBOOKS.EBOOKS_AUTHORS";

                // declare specific DBMS operationsvariables
                ResultSet resultSet = null;
                Connection connection = null;
                PreparedStatement pstmntb = null;
                PreparedStatement pstmntba = null;

                try
                { 
                    
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    connection.setAutoCommit(false);
                    String DMLBOOK = "INSERT INTO EBOOKS.EBOOKS"
                                    + "(isbn, denumire, pages, pret, nr_exemplare, id_type, id_genre, id_publisher, id_quality) "
                                    +" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    String DMLBOOKAUTH = "INSERT INTO EBOOKS.EBOOKS_AUTHORS VALUES (?, ?, ?)";
                    pstmntb = connection.prepareStatement(DMLBOOK);
                    pstmntba = connection.prepareStatement(DMLBOOKAUTH);
                    //next value for column EBOOKS.EBOOKS_AUTHORS.ID
                    String seqVal = nextVal.getNextValue(tableName, url, user, password);
                                        
                    pstmntb.setString(1, isbn);
                    pstmntb.setString(2, denumire);
                    pstmntb.setString(3, pages);
                    pstmntb.setString(4, price);
                    pstmntb.setString(5, copies);
                    pstmntb.setString(6, vid_type);
                    pstmntb.setString(7, vid_genre);
                    pstmntb.setString(8, vid_publisher);
                    pstmntb.setString(9, vid_quality);
                    
                    pstmntb.execute();

                    pstmntba.setString(1, seqVal);
                    pstmntba.setString(2, isbn);
                    pstmntba.setString(3, vcnpAuthor);
                    pstmntba.execute();
                    
                    connection.commit();
                    connection.setAutoCommit(true);
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmntb != null)
                    {
                        try
                        {
                            pstmntb.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmntba != null)
                    {
                        try
                        {
                            pstmntba.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }

                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
                }
            }  // check push on Update button
            else if (request.getParameter("admin_ebook_update") != null){ // update
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                PreparedStatement pstmnta = null;
                Connection connection = null;
                try
                { 
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    String[] selectedCheckboxes = request.getParameterValues("admin_book_checkbox");
                    String username = request.getParameter("admin_users_username");
                    String user_password = request.getParameter("admin_users_password");

                    String newisbn = request.getParameter("admin_book_isbn");
                    String newtitle = request.getParameter("admin_book_denumire");
                    String newpages = request.getParameter("admin_book_pages");
                    String newprice = request.getParameter("admin_book_price");
                    String newcopies = request.getParameter("admin_book_nrex");

                if (!((newisbn.length()==0)&&(newtitle.length()==0)&&(newpages.length()==0)&&(newprice.length()==0)&&(newcopies.length()==0))) 
                {
                        connection.setAutoCommit(false);
                        for(String s : selectedCheckboxes){
                            // realize update of all selected rows
                            String isbn = s;
                            if(!("".equals(newisbn))){ // only isbn should be updated
                                String DML = "UPDATE EBOOKS.EBOOKS SET isbn=? WHERE isbn=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, newisbn);
                                pstmnt.setString(2, isbn);
                               
                                String DMLAUT = "UPDATE EBOOKS.EBOOKS_AUTHORS SET id_isbn=? WHERE id_isbn=?";
                                pstmnta = connection.prepareStatement(DMLAUT);
                                pstmnta.setString(1, newisbn);
                                pstmnta.setString(2, isbn);
                            }
                            else if(!("".equals(newtitle))){// only username should be updated
                                String DML = "UPDATE EBOOKS.EBOOKS SET denumire=? WHERE isbn=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, newtitle);
                                pstmnt.setString(2, isbn);
                            }
                            else if(!("".equals(newpages))){// only username should be updated
                                String DML = "UPDATE EBOOKS.EBOOKS SET pages=? WHERE isbn=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, newpages);
                                pstmnt.setString(2, isbn);
                            }
                            else if(!("".equals(newprice))){// only username should be updated
                                String DML = "UPDATE EBOOKS.EBOOKS SET pret=? WHERE isbn=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, newprice);
                                pstmnt.setString(2, isbn);
                            }
                            else if(!("".equals(newcopies))){// only username should be updated
                                String DML = "UPDATE EBOOKS.EBOOKS SET nr_exemplare=? WHERE isbn=?";
                                pstmnt = connection.prepareStatement(DML);
                                pstmnt.setString(1, newcopies);
                                pstmnt.setString(2, isbn);
                            }
                            boolean execute = pstmnt.execute();
                        }
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    // display a message for NOT OK
                    Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);

                }
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }	
                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
                }
            } // check push on Delete button
            else if (request.getParameter("admin_ebook_delete") != null) { // delete 
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                PreparedStatement pstmnta = null;
                Connection connection = null;
                try
                {
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the delete operation
                    String[] selectedCheckboxes = request.getParameterValues("admin_book_checkbox");
                    // more critical DB operations should be made into a transaction
                    connection.setAutoCommit(false);
                    for(String s : selectedCheckboxes){
                        // realize delete of all selected rows
                        String isbn = s;
                        String DML = "DELETE FROM EBOOKS.EBOOKS WHERE ISBN=?";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, isbn);
                        pstmnt.execute();
                        
                        String DMLAUT = "DELETE FROM EBOOKS.EBOOKS_AUTHORS WHERE ID_ISBN=?";
                        pstmnta = connection.prepareStatement(DMLAUT);
                        pstmnta.setString(1, isbn);
                        pstmnta.execute();
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                    if (connection != null){
                        try {
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }              
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null){
                        try
                        {
                            connection.setAutoCommit(true);
                        }
                        catch (SQLException ex){                          
                            Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(eBookStoreAdminEBooksServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminEBooks.jsp").forward(request, response);
                }
            } // check push on Cancel button
            else if (request.getParameter("admin_ebook_cancel") != null){ // cancel
                request.getRequestDispatcher("./eBooksStoreMainPage.jsp").forward(request, response);
            }            
        }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
