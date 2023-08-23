package com.secdavid.tpmonitoring.model.entsoe;

import java.util.List;

public class ParsedMasterData {

    private String mRID;
    private String type;
    private String processType;
    private String created;
    private String controlAreaDomain;
    private List<TimeSeries> timeSeriesList;

    public String getmRID() {
        return mRID;
    }

    public void setmRID(String mRID) {
        this.mRID = mRID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getControlAreaDomain() {
        return controlAreaDomain;
    }

    public void setControlAreaDomain(String controlAreaDomain) {
        this.controlAreaDomain = controlAreaDomain;
    }

    public List<TimeSeries> getTimeSeriesList() {
        return timeSeriesList;
    }

    public void setTimeSeriesList(List<TimeSeries> timeSeriesList) {
        this.timeSeriesList = timeSeriesList;
    }

    @Override
    public String toString() {
        return "ParsedMasterData{" +
                "mRID='" + mRID + '\'' +
                ", type='" + type + '\'' +
                ", processType='" + processType + '\'' +
                ", created='" + created + '\'' +
                ", controlAreaDomain='" + controlAreaDomain + '\'' +
                ", timeSeriesList=" + timeSeriesList +
                '}';
    }
}
