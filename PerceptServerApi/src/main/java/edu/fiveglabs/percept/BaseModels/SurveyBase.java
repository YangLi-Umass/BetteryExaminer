package edu.fiveglabs.percept.BaseModels;

/**
 * Created by li on 6/8/2015.
 */
public class SurveyBase {
    private long id;
    private String startTime;
    private long buildingId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }
}

