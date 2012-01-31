/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.interfaces;

import analytics.beans.MapperObject;
import net.sf.json.JSONObject;


public interface JSONUtilInterface {
    public JSONObject getJSONObject(String JSONString);
    public MapperObject parseJSONObject(JSONObject jsonObject);
}
