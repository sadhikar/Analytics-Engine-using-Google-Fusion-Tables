/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.AuthProvider;
import java.util.Properties;


public class PropertiesFileManager {

    public static Properties QUERY_PROPERTIES;
    public static Properties DATABASE_PROPERTIES;
    public static Properties CRON_PROPERTIES;
    public static Properties GOOGLE_CONFIG_PROPERTIES;

    public static void readPropertiesFiles() throws IOException{
        readQueriesProperties();
        readDatabaseProperties();
        readCronProperties();
        readGoogleConfigProperties();
    }

    private static void readQueriesProperties() throws IOException {
        ClassLoader classLoader = PropertiesFileManager.class.getClassLoader();
        if (classLoader != null) {
            InputStream is = classLoader.getResourceAsStream("queries.properties");
            if (is != null) {
                QUERY_PROPERTIES = new Properties();
                QUERY_PROPERTIES.load(is);
                is.close();
            }
        }
    }

    private static void readDatabaseProperties() throws IOException {
        ClassLoader classLoader = PropertiesFileManager.class.getClassLoader();
        if(classLoader!=null){
            InputStream is = classLoader.getResourceAsStream("database.properties");
            if(is!=null){
                DATABASE_PROPERTIES = new Properties();
                DATABASE_PROPERTIES.load(is);
                is.close();
            }
        }
    }

    private static void readCronProperties() throws IOException {
        ClassLoader classLoader = PropertiesFileManager.class.getClassLoader();
        if(classLoader!=null){
            InputStream is = classLoader.getResourceAsStream("cron.properties");
            if(is!=null){
                CRON_PROPERTIES = new Properties();
                CRON_PROPERTIES.load(is);
                is.close();
            }
        }
    }

    private static void readGoogleConfigProperties() throws IOException {
        ClassLoader classLoader = PropertiesFileManager.class.getClassLoader();
        if (classLoader != null) {
            InputStream is = classLoader.getResourceAsStream("googleconfig.properties");
            if (is != null) {
                GOOGLE_CONFIG_PROPERTIES = new Properties();
                GOOGLE_CONFIG_PROPERTIES.load(is);
                is.close();
            }
        }
    }
}
