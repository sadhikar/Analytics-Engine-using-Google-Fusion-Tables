/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.util;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.Service.GDataRequest.RequestType;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;
import analytics.beans.GoogleCredentials;
import analytics.beans.InputData;
import analytics.beans.MapperObject;
import analytics.beans.QueryMaster;
import analytics.database.DBConnectionManager;
import analytics.interfaces.FusionTableInterface;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class FusionTable implements FusionTableInterface {

    /**
     * Google Fusion Tables API URL stem.
     * All requests to the Google Fusion Tables server
     * begin with this URL.
     *
     * The next line is Google Fusion Tables API-specific code:
     */
    private static final String SERVICE_URL =
            "https://www.google.com/fusiontables/api/query";
    /**
     * CSV values are terminated by comma or end-of-line and consist either of
     * plain text without commas or quotes, or a quoted expression, where inner
     * quotes are escaped by doubling.
     */
    private static final Pattern CSV_VALUE_PATTERN =
            Pattern.compile("([^,\\r\\n\"]*|\"(([^\"]*\"\")*[^\"]*)\")(,|\\r?\\n)");
    /**
     * Handle to the authenticated Google Fusion Tables service.
     *
     * This code uses the GoogleService class from the
     * Google GData APIs Client Library.
     */
    private GoogleService service;

    public FusionTable() {
    }

    /**
     * Two versions of ApiExample() are provided:
     * one that accepts a Google user account ID and password for authentication,
     * and one that accepts an existing auth token.
     */
    /**
     * Authenticates the given account for {@code fusiontables} service using a
     * given email ID and password.
     *
     * @param email    Google account email. (For more information, see
     *                 http://www.google.com/support/accounts.)
     * @param password Password for the given Google account.
     *
     * This code instantiates the GoogleService class from the
     * Google GData APIs Client Library,
     * passing in Google Fusion Tables API-specific parameters.
     * It then goes back to the Google GData APIs Client Library for the
     * setUserCredentials() method.
     */
    public FusionTable(String email, String password)
            throws AuthenticationException {
        service = new GoogleService("fusiontables", "fusiontables.ApiExample");
        service.setUserCredentials(email, password, ClientLoginAccountType.GOOGLE);
    }

    public List<QueryMaster> getAllQueries() {
        List<QueryMaster> queriesList = new ArrayList<QueryMaster>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getFusionTableConnection();
            String query = PropertiesFileManager.QUERY_PROPERTIES.getProperty("get_all_queries");
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                QueryMaster queryMasterBean = new QueryMaster();
                queryMasterBean.setQueryId(rs.getInt("query_id"));
                queryMasterBean.setDescription(rs.getString("description"));
                queryMasterBean.setQueryName(rs.getString("query_name"));
                queryMasterBean.setFusionTableId(rs.getInt("fusion_table_id"));
                queriesList.add(queryMasterBean);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Exception occured " + cnfe.getClass() + "\nMessage : " + cnfe.getMessage());
        } catch (SQLException sqle) {
            System.out.println("Exception occured " + sqle.getClass() + "\nMessage : " + sqle.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sql) {
                System.out.println("Exception occured in finally block of getAllQueries method of " + FusionTable.class);
            }
        }
        return queriesList;
    }

    /**
     * Fetches the results for a select query. Prints them to standard
     * output, surrounding every field with (@code |}.
     *
     * This code uses the GDataRequest class and getRequestFactory() method
     * from the Google Data APIs Client Library.
     * The Google Fusion Tables API-specific part is in the construction
     * of the service URL. A Google Fusion Tables API SELECT statement
     * will be passed in to this method in the selectQuery parameter.
     */
    public void runSelect(String selectQuery) throws IOException,
            ServiceException {
        URL url = new URL(
                SERVICE_URL + "?sql=" + URLEncoder.encode(selectQuery, "UTF-8"));
        GDataRequest request = service.getRequestFactory().getRequest(
                RequestType.QUERY, url, ContentType.TEXT_PLAIN);

        request.execute();

        /* Prints the results of the query.                */
        /* No Google Fusion Tables API-specific code here. */

        Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
        while (scanner.hasNextLine()) {
            scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
            MatchResult match = scanner.match();
            String quotedString = match.group(2);
            String decoded = quotedString == null ? match.group(1)
                    : quotedString.replaceAll("\"\"", "\"");
            System.out.print("|" + decoded);
            if (!match.group(4).equals(",")) {
                System.out.println("|");
            }
        }
    }

    /**
     * Executes insert, update, and delete statements.
     * Prints out results, if any.
     *
     * This code uses the GDataRequest class and getRequestFactory() method
     * from the Google Data APIs Client Library to construct a POST request.
     * The Google Fusion Tables API-specific part is in the use
     * of the service URL. A Google Fusion Tables API INSERT, UPDATE,
     * or DELETE statement will be passed into this method in the
     * updateQuery parameter.
     */
    public void runUpdate(String updateQuery) throws IOException,
            ServiceException {
        URL url = new URL(SERVICE_URL);
        GDataRequest request = service.getRequestFactory().getRequest(
                RequestType.INSERT, url,
                new ContentType("application/x-www-form-urlencoded"));
        OutputStreamWriter writer =
                new OutputStreamWriter(request.getRequestStream());
        writer.append("sql=" + URLEncoder.encode(updateQuery, "UTF-8"));
        writer.flush();

        request.execute();

        /* Prints the results of the statement.            */
        /* No Google Fusion Tables API-specific code here. */

        Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
        while (scanner.hasNextLine()) {
            scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
            MatchResult match = scanner.match();
            String quotedString = match.group(2);
            String decoded = quotedString == null ? match.group(1)
                    : quotedString.replaceAll("\"\"", "\"");
            System.out.print("|" + decoded);
            if (!match.group(4).equals(",")) {
                System.out.println("|");
            }
        }
    }

    public long createFusionTable(GoogleCredentials googleCredentials, Map tableColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean fillFusionTable(long fusionTableId, int queryId, List<InputData> inputDataWithMapperObjectsUpdatedList) {
        try {
            String fusionQueryName = PropertiesFileManager.QUERY_PROPERTIES.getProperty(fusionTableId + "");
            for (InputData inputDataWithMapperObjectsUpdated : inputDataWithMapperObjectsUpdatedList) {
                String.format(fusionQueryName, inputDataWithMapperObjectsUpdated.getName(), inputDataWithMapperObjectsUpdated.getCollegeName(), inputDataWithMapperObjectsUpdated.getEmail(), inputDataWithMapperObjectsUpdated.getMapperObject().getLocation().getLatitude(), inputDataWithMapperObjectsUpdated.getMapperObject().getLocation().getLongitude());
                runUpdate(fusionQueryName);
                updateRowInsertionStatus(inputDataWithMapperObjectsUpdated.getEmail(), queryId);
            }
        } catch (IOException ex) {
            Logger.getLogger(FusionTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(FusionTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean deleteFusionTable(long fusionTableId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private int updateRowInsertionStatus(String email, int queryId) {
        String query = PropertiesFileManager.QUERY_PROPERTIES.getProperty("update_row_status");
        Connection con = null;
        PreparedStatement ps = null;
        int numberOfrowsUpdated = 0;
        try {
            con = DBConnectionManager.getFusionTableConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setInt(2, queryId);
            numberOfrowsUpdated = ps.executeUpdate();            
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Exception is " + cnfe.getClass() + "and message is " + cnfe.getMessage());
        } catch (SQLException sqle) {
            System.out.println("Exception is " + sqle.getClass() + "and message is " + sqle.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                System.out.println("SQL Exception when trying to close Prepared Statement or Connection");
            }
        }
        return numberOfrowsUpdated;
    }
}
