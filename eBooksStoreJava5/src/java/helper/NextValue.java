/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlets.Index;

public class NextValue {

    public String getNextValue(String tableName, String url, String user, String password){
        
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        
        String driver = "org.apache.derby.jdbc.ClientDriver";
        int rs = 0;
        try
        {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            String query = "SELECT max(id) FROM " + tableName;
            resultSet = statement.executeQuery(query);
            boolean resultSetHasRows = resultSet.next(); 
            if (resultSetHasRows)
            {
                rs = resultSet.getInt(1)+1;
            }
            else
            {
                 rs = -1;
            }
        } 
        catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (resultSet != null)
            {
                try
                {
                    resultSet.close();
                }
                catch (SQLException ex){Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
            }
            if (statement != null)
            {
                try
                {
                    statement.close();
                }
                catch (SQLException ex){Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
            }	
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException ex){Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
            }
        }
        
        return Integer.toString(rs);
            
}
}

