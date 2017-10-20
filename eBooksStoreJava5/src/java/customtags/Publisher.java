/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customtags;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Publisher extends SimpleTagSupport {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id;
    private String name;
    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    
    String user = "ebooks" ;
    String password = "ebooks";
    String url = "jdbc:derby://localhost:1527/ebooks;create=true;";
    String driver = "org.apache.derby.jdbc.ClientDriver"; 
    ResultSet resultSet = null;
    Statement statement = null;
    Connection connection = null;
    PreparedStatement pstmnt = null;

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        
        try {
             
            try
                {
                    //check driver and create connection
                    Class driverClass = Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password); 
                    String DML = "SELECT id, name FROM EBOOKS.PUBLISHERS";
//                    String DML = "SELECT id, name FROM EBOOKS.PUBLISHERS WHERE id = ?";
//                    pstmnt.setInt(1, 1);
                    pstmnt = connection.prepareStatement(DML);
                    pstmnt.execute();
                }
                catch (ClassNotFoundException | SQLException ex)
                {
                    // display a message for NOT OK
                    Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);
                }

                            finally
                {
                    if (resultSet != null)
                    {
                        try
                        {
                            resultSet.close();
                        }
                        catch (SQLException ex){Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (statement != null)
                    {
                        try
                        {
                            statement.close();
                        }
                        catch (SQLException ex){Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (pstmnt != null)
                    {
                        try
                        {
                            pstmnt.close();
                        }
                        catch (SQLException ex){Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    if (connection != null)
                    {
                        try
                        {
                            connection.close();
                        }
                        catch (SQLException ex){Logger.getLogger(Publisher.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                }
            
    
            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Publisher tag", ex);
        }
    }
    
}
