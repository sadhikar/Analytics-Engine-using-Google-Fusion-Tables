/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analytics.interfaces;

import analytics.beans.GoogleCredentials;
import analytics.beans.InputData;
import java.util.List;
import java.util.Map;


 
public interface FusionTableInterface {

    public long createFusionTable(GoogleCredentials googleCredentials,Map tableColumns);

    public boolean fillFusionTable(long fusionTableId, int queryId, List<InputData> inputDataWithMapperObjectsUpdated);

    public boolean deleteFusionTable(long fusionTableId);

}
