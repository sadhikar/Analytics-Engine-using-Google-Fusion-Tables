/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.listeners;

import analytics.main.FusionProcessFlow;
import analytics.util.PropertiesFileManager;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * 
 */
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        try {
            PropertiesFileManager.readPropertiesFiles();
            FusionProcessFlow.startScheduling();
        } catch (IOException ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        FusionProcessFlow.stopScheduling();
        for(Enumeration<Driver> e = DriverManager.getDrivers();e.hasMoreElements();){
            try {
                Driver d = e.nextElement();
                DriverManager.deregisterDriver(d);
                System.out.println("The driver " + d.getClass() + " is deregistered successfully");
            } catch (SQLException ex) {
                Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
