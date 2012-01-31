package analytics.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HttpConnection {

    public URLConnection getHttpConnection(String servletUrl) throws MalformedURLException {

        try {

            /*
             * Attempt to establish a conncetion to the servlet URL
             */
            URL url = new URL(servletUrl);
            URLConnection connection = url.openConnection(java.net.Proxy.NO_PROXY);
            /*
             * This connection does both input and output; Therefore set the
             * appropriate flags to true.
             */
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(true);
            connection.setDefaultUseCaches(true);
            /*
             * Set the content type of the Http request to octet-stream as
             * the e-mail message is going to be sent as an Object
             */
            connection.setRequestProperty("Content-Type","application/application");

            return (connection);
        } catch (Exception e) {
            System.out.println(e);
            return (null);
        }

    }

} 