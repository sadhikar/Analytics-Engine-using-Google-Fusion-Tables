/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.util;

import analytics.beans.InputData;
import analytics.database.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InputAddressDetails {

    public List<InputData> fetchInputData(String queryName) throws ClassNotFoundException, SQLException{
        List<InputData> inputDataList = null;
        Connection connection = DBConnectionManager.getFusionTableConnection();
        String query = PropertiesFileManager.QUERY_PROPERTIES.getProperty(queryName);
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        inputDataList = new ArrayList<InputData>();
        while(rs.next()){
            InputData id = new InputData();
            id.setName(rs.getString("name"));
            id.setEmail(rs.getString("email"));
            id.setCollegeName(rs.getString("college_name"));
            id.setAddress(rs.getString("perm_locality")+","+rs.getString("perm_city")+","+rs.getString("perm_state")+","+rs.getString("perm_country"));
            inputDataList.add(id);
        }
        return inputDataList;
    }
}
