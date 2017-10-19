/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customtags;

 import java.io.PrintWriter; 
 import java.sql.Connection; 
 import java.sql.DriverManager; 
 import java.sql.SQLException; 
 import java.sql.SQLFeatureNotSupportedException; 
 import java.util.logging.Level; 
 import java.util.logging.Logger; 
 import javax.servlet.jsp.JspWriter; 
 import javax.servlet.jsp.JspException; 
 import javax.servlet.jsp.PageContext; 
 import javax.servlet.jsp.tagext.JspFragment; 
 import javax.servlet.jsp.tagext.SimpleTagSupport; 

public class Databaseconnection extends SimpleTagSupport implements javax.sql.DataSource {

     private String url; 
     private String driver; 
     private String username; 
     private String password; 
     private String connection; 
     private Connection connectionDB = null; 
     /** 
      * Called by the container to invoke this tag. The implementation of this 
      * method is provided by the tag library developer, and handles all tag 
      * processing, body iteration, etc. 
      * @throws javax.servlet.jsp.JspException 
      */ 
     @Override 
     public void doTag() throws JspException{ 
         JspWriter out = getJspContext().getOut(); 
         try { 
             // set connection paramters to the DB 
              
             try 
             { 
                 Class driverClass = Class.forName(driver); 
                 connectionDB = DriverManager.getConnection(url, username, password); 
                 /* set a session variable that contains the connection.  
                 This can be used to execute Statements, PreparedStatements or 
                 CallableStatements on DB. 
                 */ 
                 getJspContext().setAttribute(connection, this, PageContext.SESSION_SCOPE); 
             }  
             catch (ClassNotFoundException | SQLException ex) 
             { 
                 Logger.getLogger(Databaseconnection.class.getName()).log(Level.SEVERE, null, ex); 
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
             throw new JspException("Error in databseconnection tag", ex); 
         } 
     } 
 
 
     /** 
      * 
      * @param url 
      */ 
     public void setUrl(String url) { 
         this.url = url; 
     } 
 
 
     /** 
      * 
      * @param driver 
      */ 
     public void setDriver(String driver) { 
         this.driver = driver; 
     } 
 
 
     /** 
      * 
      * @param username 
      */ 
     public void setUsername(String username) { 
         this.username = username; 
     } 
 
 
     /** 
      * 
      * @param password 
      */ 
     public void setPassword(String password) { 
         this.password = password; 
     } 
 
 
     /** 
      * 
      * @param connection 
      */ 
     public void setConnection(String connection) { 
         this.connection = connection; 
     }    
  
     /** 
      * 
      * @param connectionDB 
      */ 
     public void setConnectionDB(Connection connectionDB) { 
         this.connectionDB = connectionDB; 
     } 
      
     @Override 
     public Connection getConnection() throws SQLException { 
         return connectionDB;   
     } 
 
 
     @Override 
     public Connection getConnection(String username, String password) throws SQLException { 
         return connectionDB; //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public PrintWriter getLogWriter() throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public void setLogWriter(PrintWriter out) throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public void setLoginTimeout(int seconds) throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public int getLoginTimeout() throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public Logger getParentLogger() throws SQLFeatureNotSupportedException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public <T> T unwrap(Class<T> iface) throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
 
 
     @Override 
     public boolean isWrapperFor(Class<?> iface) throws SQLException { 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
     } 
    
}
