/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.util;

import analytics.beans.MapperObject;
import analytics.beans.Point;
import analytics.interfaces.JSONUtilInterface;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JSONParser implements JSONUtilInterface {

    public JSONObject getJSONObject(String JSONString) {
        return (JSONObject) JSONSerializer.toJSON(JSONString);
    }

    public MapperObject parseJSONObject(JSONObject jSONObject) {
        Point point = new Point();
        MapperObject mapperObject = new MapperObject();
        JSONArray jSONArray = jSONObject.getJSONArray("results");
        if (jSONArray != null && jSONArray.getJSONObject(0) != null && jSONArray.getJSONObject(0).getJSONObject("geometry") != null) {
            if (jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location") != null) {
                point.setLatitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                point.setLongitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                mapperObject.setLocation(point);
            }
            if (jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport") != null && jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest") != null) {
                point.setLatitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest").getDouble("lat"));
                point.setLongitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest").getDouble("lng"));
                mapperObject.setSWBounds(point);
            }
            if (jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport") != null && jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast") != null) {
                point.setLatitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast").getDouble("lat"));
                point.setLongitude(jSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast").getDouble("lng"));
                mapperObject.setSWBounds(point);
            }
        }
        return mapperObject;
    }
}
