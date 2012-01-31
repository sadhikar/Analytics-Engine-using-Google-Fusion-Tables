/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.interfaces;

import analytics.beans.MapperObject;

public interface GeocodeInterface {
    public MapperObject getGeocode(String address);
}
