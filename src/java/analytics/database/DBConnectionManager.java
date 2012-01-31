/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.database;

import analytics.util.PropertiesFileManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionManager {

    public static Connection getFusionTableConnection() throws ClassNotFoundException, SQLException{
        String driverName = PropertiesFileManager.DATABASE_PROPERTIES.getProperty("driverName");
        Class driverClass = Class.forName(driverName);
        String hostURL = PropertiesFileManager.DATABASE_PROPERTIES.getProperty("hostURL");
        String userName = PropertiesFileManager.DATABASE_PROPERTIES.getProperty("userName");
        String pass = PropertiesFileManager.DATABASE_PROPERTIES.getProperty("pass");
        Connection connnection = DriverManager.getConnection(hostURL, userName, pass);
        return connnection;
    }

}
