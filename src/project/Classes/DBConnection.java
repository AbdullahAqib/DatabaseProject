/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.Classes;

/**
 *
 * @author abdul
 */
import java.sql.*;

public class DBConnection {
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
    
        public void createLink() throws SQLException
       {
           connection = DriverManager.getConnection("jdbc:mysql://localhost/db_shop","root","abdullah");
           statement = connection.createStatement();
       }
     
        public void closeLink()
        {	try{
            if(connection!=null)connection.close();
            if(statement!=null)statement.close();
            if(resultSet!=null)resultSet.close();
        }catch(SQLException exp){
            exp.printStackTrace();
        }
    
    }
}
