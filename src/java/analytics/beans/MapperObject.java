/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.beans;

import java.io.Serializable;


public class MapperObject implements Serializable{

    private Point location;
    private Point NEBounds;
    private Point SWBounds;
    private String address;
    private String name;
    private String description;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getNEBounds() {
        return NEBounds;
    }

    public void setNEBounds(Point NEBounds) {
        this.NEBounds = NEBounds;
    }

    public Point getSWBounds() {
        return SWBounds;
    }

    public void setSWBounds(Point SWBounds) {
        this.SWBounds = SWBounds;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
