/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helper.NextValue;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author iulica
 */
public class eBookStoreUserOrderServlet extends HttpServlet {

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
            if (request.getParameter("admin_order_insert") != null) { // insert values from fields
                // set connection paramters to the DB
                // read values from page fields
                LocalDate ld = LocalDate.now();
                Date orderdate = Date.valueOf(ld);
                
                String bookorder = request.getParameter("user_ebooks_orders");
                String isbn = bookorder.substring(0, bookorder.indexOf('|'));
                String copies = request.getParameter("user_order_copies");
                String ssn = request.getParameter("actualUser");
             
                NextValue nextVal = new NextValue(); 
                String tableName = "EBOOKS.EBOOK_ORDERS";
                // declare specific variables
                ResultSet resultSet = null;
                Statement statement = null;
                Connection connection = null;
                PreparedStatement pstmnt = null;
                
                try
                {
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password); 
                    // test if number of copies field is empty & realize the insert
                    if(!("".equals(copies)) ){
                        // display a message for ok
                         String DML = "INSERT INTO EBOOKS.EBOOK_ORDERS "
                                    + "(id, id_isbn, id_ssn, copies, order_date)"
                                    + "VALUES " 
                                    +"(?, ?, ?, ?, ?)"; 
                        
                        pstmnt = connection.prepareStatement(DML);
                        String seqVal = nextVal.getNextValue(tableName, url, user, password);

                        pstmnt.setString(1, seqVal);
                        pstmnt.setString(2, isbn);
                        pstmnt.setString(3, ssn);
                        pstmnt.setString(4, copies);
                        pstmnt.setDate(5, orderdate);
                        pstmnt.execute();

                    }

                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    // display a message for NOT OK
                    Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (statement != null)
                    {
                        try
                        {
                            statement.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBookStoreUserOrder.jsp").forward(request, response);
                }
            }  // check push on Update button
            else if (request.getParameter("admin_order_update") != null){ // update
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;

                LocalDate ld = LocalDate.now();
                Date orderdate = Date.valueOf(ld);

                try
                { 
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the update operation
                    String[] selectedCheckboxes = request.getParameterValues("admin_orders_checkbox");
                    String isbn = request.getParameter("user_ebooks_orders");
                    String newisbn = isbn.substring(0, isbn.indexOf('|'));
                    String newcopies = request.getParameter("user_order_copies");

                    if(!(("".equals(newcopies)))){ 
                         for(String s : selectedCheckboxes){ 
                                 String DML = "UPDATE EBOOKS.EBOOK_ORDERS SET copies=?, order_date=? WHERE id=?"; 
                                 pstmnt = connection.prepareStatement(DML); 
                                 pstmnt.setString(1, newcopies); 
                                 pstmnt.setDate(2, orderdate); 
                                 pstmnt.setString(3, s); 
                         }
                             boolean execute = pstmnt.execute(); 
                         } 
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                    if (connection != null){
                        try {
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex1);
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
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null){
                        try
                        {
                            connection.setAutoCommit(true);
                        }
                        catch (SQLException ex){                          
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBookStoreUserOrder.jsp").forward(request, response);
                }
            } // check push on Delete button
            else if (request.getParameter("admin_order_delete") != null) { // delete 
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;
                try
                { 
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    String[] selectedCheckboxes = request.getParameterValues("admin_orders_checkbox");
                    connection.setAutoCommit(false);
                    for(String s : selectedCheckboxes){
                        String DML = "DELETE FROM EBOOKS.EBOOK_ORDERS WHERE ID=?";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, s);
                        pstmnt.execute();
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                    if (connection != null){
                        try {
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex1);
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
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null){
                        try
                        {
                            connection.setAutoCommit(true);
                        }
                        catch (SQLException ex){                          
                            Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(eBookStoreUserOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBookStoreUserOrder.jsp").forward(request, response);
                }
            } // check push on Cancel button
            else if (request.getParameter("admin_order_cancel") != null){ // cancel
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
