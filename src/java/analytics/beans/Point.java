/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.beans;

import java.io.Serializable;


public class Point implements Serializable{

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
