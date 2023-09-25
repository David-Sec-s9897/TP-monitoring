package com.secdavid.tpmonitoring.model;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.Period;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class TpProcess {
    private String name;

    private Category category;

    private MasterData masterData;

    private List<TimeSeries> timeSeriesList = new ArrayList<>();

    private List<TimeInterval> availableTimeIntervals = new ArrayList<>();

    public TpProcess(String name, Category category, MasterData masterData) {
        this.name = name;
        this.category = category;
        this.masterData = masterData;
    }

    public String getName() {
        return name;
    }

    public MasterData getMasterData() {
        return masterData;
    }

    public Category getCategory() {
        return category;
    }

    public List<TimeSeries> getTimeSeriesList() {
        return timeSeriesList;
    }

    public List<TimeInterval> getAvailableTimeIntervals() {
        return availableTimeIntervals;
    }

    public void setAvailableTimeIntervals(List<TimeInterval> availableTimeIntervals) {
        this.availableTimeIntervals = availableTimeIntervals;
    }

    @Override
    public String toString() {
        return "TpProcess{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", masterData=" + masterData +
                ", timeSeriesList=" + timeSeriesList +
                '}';
    }
}

