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
import java.sql.Statement;
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
public class eBooksStoreAdminBookAuthorServlet extends HttpServlet {

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
            if (request.getParameter("admin_author_insert") != null) { // insert values from fields
                // set connection paramters to the DB
                // read values from page fields
                String fname = request.getParameter("admin_author_fname");
                String lname = request.getParameter("admin_author_lname");
                String cnp = request.getParameter("admin_author_cnp");

                //NextValue nextVal = new NextValue(); 
                //String tableName = "EBOOKS.BOOK_AUTHOR";
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
                    // test if role field is empty & realize the insert
                    //if(!("".equals(fname))&&("".equals(lname))&&("".equals(cnp))){
                    if((fname.length()>0)&&(lname.length()>0)&&(cnp.length()>0)){
                        String DML = "INSERT INTO EBOOKS.BOOK_AUTHOR VALUES (?,?,?)";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, cnp);
                        pstmnt.setString(2, fname);
                        pstmnt.setString(3, lname);
                        pstmnt.execute();
                        // display a message for ok
                    }
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    // display a message for NOT OK
                    Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (statement != null)
                    {
                        try
                        {
                            statement.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminBookAuthorPage.jsp").forward(request, response);
                }
            }  // check push on Update button
            else if (request.getParameter("admin_author_update") != null){ // update
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;
                try
                { 
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the update operation
                    String[] selectedCheckboxes = request.getParameterValues("admin_author_checkbox");
                    String newcnp = request.getParameter("admin_author_cnp");
                    String newfname = request.getParameter("admin_author_fname");//firstname
                    String newlname = request.getParameter("admin_author_lname");//lastname
               if (!((newcnp.length()==0)&&(newfname.length()==0)&&(newlname.length()==0))) {
                for(String s : selectedCheckboxes){
                    if(!("".equals(newcnp))){
                            //only ss updated                            
                            String DML = "UPDATE EBOOKS.BOOK_AUTHOR SET ssn=? WHERE ssn=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, newcnp);
                            pstmnt.setString(2, s);
                            boolean execute = pstmnt.execute();
                    }
                    else if(!("".equals(newlname))){// only firstname should be updated
                            String DML = "UPDATE EBOOKS.BOOK_AUTHOR SET family_name=? WHERE SSN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, newlname);
                            pstmnt.setString(2, s);
                            boolean execute = pstmnt.execute();
                            }
                    else if (!("".equals(newfname))){// only username should be updated
                            String DML = "UPDATE EBOOKS.BOOK_AUTHOR SET first_name=? WHERE SSN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, newfname);
                            pstmnt.setString(2, s);
                            boolean execute = pstmnt.execute();
                    }
                else{ // update one or more roles for one or more users
                     // realize update of all selected rows
                            String ssn = s;
                            String DML = "UPDATE EBOOKS.BOOK_AUTHOR SET ssn=?, first_name=?, last_name=? WHERE SSN=?";
                            pstmnt = connection.prepareStatement(DML);
                            pstmnt.setString(1, ssn);
                            pstmnt.setString(2, newfname);
                            pstmnt.setString(3, newlname);
                            boolean execute = pstmnt.execute();
                            }
                        }
                    }
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                    if (connection != null){
                        try {
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex1);
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
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null){
                        try
                        {
                            connection.setAutoCommit(true);
                        }
                        catch (SQLException ex){                          
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminBookAuthorPage.jsp").forward(request, response);
                }
            } // check push on Delete button
            else if (request.getParameter("admin_author_delete") != null) { // delete 
                // declare specific variables
                ResultSet resultSet = null;
                PreparedStatement pstmnt = null;
                Connection connection = null;
                try
                { 
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);
                    // identify selected checkbox and for each execute the delete operation
                    String[] selectedCheckboxes = request.getParameterValues("admin_author_checkbox");
                    // multiple db critical operations should be executed into a transaction
                    connection.setAutoCommit(false);
                    for(String s : selectedCheckboxes){
                        // realize delete of all selected rows
                        String DML = "DELETE FROM EBOOKS.BOOK_AUTHOR WHERE SSN=?";
                        pstmnt = connection.prepareStatement(DML);
                        pstmnt.setString(1, s);
                        pstmnt.execute();
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                    if (connection != null){
                        try {
                            connection.rollback();
                        } catch (SQLException ex1) {
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex1);
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
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null){
                        try
                        {
                            connection.setAutoCommit(true);
                        }
                        catch (SQLException ex){                          
                            Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                connection.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(eBooksStoreAdminBookAuthorServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    // redirect page to its JSP as view
                    request.getRequestDispatcher("./eBooksStoreAdminBookAuthorPage.jsp").forward(request, response);
                }
            } // check push on Cancel button
            else if (request.getParameter("admin_author_cancel") != null){ // cancel
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
