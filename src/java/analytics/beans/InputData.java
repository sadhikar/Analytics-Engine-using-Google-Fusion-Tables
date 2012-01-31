/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.beans;


public class InputData {

    private String address;
    private String email;
    private String name;

    public MapperObject getMapperObject() {
        return mapperObject;
    }

    public void setMapperObject(MapperObject mapperObject) {
        this.mapperObject = mapperObject;
    }
    private String collegeName;
    private MapperObject mapperObject;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
