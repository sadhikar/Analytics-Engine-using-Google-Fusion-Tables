/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analytics.beans;


public class QueryMaster {

    private int queryId;
    private String queryName;
    private String description;
    private long fusionTableId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFusionTableId() {
        return fusionTableId;
    }

    public void setFusionTableId(long fusionTableId) {
        this.fusionTableId = fusionTableId;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}
