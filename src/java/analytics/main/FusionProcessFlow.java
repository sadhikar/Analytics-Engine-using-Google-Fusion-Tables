/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.main;

import com.google.gdata.util.AuthenticationException;
import analytics.beans.InputData;
import analytics.beans.MapperObject;
import analytics.beans.QueryMaster;
import analytics.database.DBConnectionManager;
import analytics.util.FusionTable;
import analytics.util.Geocoder;
import analytics.util.InputAddressDetails;
import analytics.util.PropertiesFileManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FusionProcessFlow extends TimerTask {

    private static long cronInterval = 0;
    public static Timer time = null;

    public FusionProcessFlow() {
        cronInterval = Long.parseLong(PropertiesFileManager.CRON_PROPERTIES.getProperty("cronInterval"));
    }

    public static void startScheduling() {
        time = new Timer("FusionTableFillerCron", true);
        time.scheduleAtFixedRate(new FusionProcessFlow(), new Date(), cronInterval);
    }

    public static void stopScheduling() {
        if (time != null) {
            time.cancel();
            time.purge();
            time = null;
        }
    }

    @Override
    public void run() {
        //Read all the rows of Query Master table from database
        //For each query id, get the query name and pass it to InputAddressDetails to get inputDataList
        //For each Inputdata from the list, get the GeoCode Address and push into required table id and update the status of that row

        List<QueryMaster> queriesList = new FusionTable().getAllQueries();
        for (QueryMaster queryMasterBean : queriesList) {
            if (queryMasterBean != null) {
                try {
                    String queryName = queryMasterBean.getQueryName();
                    Geocoder geocoder = new Geocoder();
                    int queryId = queryMasterBean.getQueryId();
                    long fusionTableId = queryMasterBean.getFusionTableId();
                    List<InputData> inputDataListForQuery = new InputAddressDetails().fetchInputData(queryName);
//                    List<MapperObject> mapperList = new ArrayList<MapperObject>();
                    List<InputData> inputDataWithMapperObjectsUpdated = new ArrayList<InputData>();
                    for (InputData inputData : inputDataListForQuery) {
                        if (inputData != null && inputData.getAddress() != null) {
                            MapperObject mapperObject = geocoder.getGeocode(inputData.getAddress());
                            inputData.setMapperObject(mapperObject);
                            inputDataWithMapperObjectsUpdated.add(inputData);
//                            mapperList.add(mapperObject);
                        }
                    }
                    try {
                        FusionTable fusionTable = new FusionTable(PropertiesFileManager.GOOGLE_CONFIG_PROPERTIES.get("username").toString(), PropertiesFileManager.GOOGLE_CONFIG_PROPERTIES.get("pass").toString());
                        boolean insertStatus = fusionTable.fillFusionTable(fusionTableId, queryId, inputDataWithMapperObjectsUpdated);
                    } catch (AuthenticationException ex) {
                        Logger.getLogger(FusionProcessFlow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FusionProcessFlow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FusionProcessFlow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
