package com.secdavid.tpmonitoring.model;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TpProcess {
    private final String name;

    private final Category category;

    private final MasterData masterData;

    private final List<TimeSeries> timeSeriesList = new ArrayList<>();

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

    public Optional<TimeInterval> getLastTimeInterval() {
        if (availableTimeIntervals.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(availableTimeIntervals.get(availableTimeIntervals.size()-1));
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

