package com.secdavid.tpmonitoring.model;

import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class TpProcess {
    String name;
    MasterData masterData;
    List<TimeSeries> timeSeriesList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MasterData getMasterData() {
        return masterData;
    }

    public void setMasterData(MasterData masterData) {
        this.masterData = masterData;
    }

    public List<TimeSeries> getTimeSeriesList() {
        return timeSeriesList;
    }

    public void setTimeSeriesList(List<TimeSeries> timeSeriesList) {
        this.timeSeriesList = timeSeriesList;
    }

    public TpProcess (String name){
        this.name = name;
    }
}

