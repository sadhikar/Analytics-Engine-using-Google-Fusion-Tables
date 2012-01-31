/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.util;

import analytics.beans.MapperObject;
import analytics.interfaces.GeocodeInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;


public class Geocoder implements GeocodeInterface {

    public MapperObject getGeocode(String address) {
        String line = "";
        String tot = "";
        MapperObject mapperObject = null;
        HttpConnection createConnection = new HttpConnection();
        URLConnection connection = null;
        {
            BufferedReader input = null;
            try {
                address = URLEncoder.encode(address, "UTF-8");
                connection = createConnection.getHttpConnection("http://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&sensor=true");
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = input.readLine()) != null) {
                    tot = tot + line;
                }
                System.out.println(tot);
                JSONParser jSONParser = new JSONParser();
                JSONObject jSONObject = jSONParser.getJSONObject(tot);
                mapperObject = jSONParser.parseJSONObject(jSONObject);
                mapperObject.setAddress(address);
                
            } catch (MalformedURLException ex) {
                Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return mapperObject;
    }
    
    public static void main(String[] args) {
        Geocoder geocoder = new Geocoder();
        geocoder.getGeocode("Pittsburgh");
    }
}
