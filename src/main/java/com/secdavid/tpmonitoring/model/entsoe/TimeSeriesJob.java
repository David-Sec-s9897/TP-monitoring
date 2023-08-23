package com.secdavid.tpmonitoring.model.entsoe;

import com.secdavid.tpmonitoring.enums.Category;

import java.util.List;

public class TimeSeriesJob {

    private String name;

    private Category category;

    private String cron;

    private List<String> timeseriesKeys;

    private String startCron;

    private String duration;

    private String nextStart;

    private Boolean active;

    private Boolean state;

    private String businessKey;

    private Boolean suspended;

    private Boolean fileUpload;

    private String mappingFile;

    private String masterDataFile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<String> getTimeseriesKeys() {
        return timeseriesKeys;
    }

    public void setTimeseriesKeys(List<String> timeseriesKeys) {
        this.timeseriesKeys = timeseriesKeys;
    }

    public String getStartCron() {
        return startCron;
    }

    public void setStartCron(String startCron) {
        this.startCron = startCron;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNextStart() {
        return nextStart;
    }

    public void setNextStart(String nextStart) {
        this.nextStart = nextStart;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Boolean fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getMappingFile() {
        return mappingFile;
    }

    public void setMappingFile(String mappingFile) {
        this.mappingFile = mappingFile;
    }

    public String getMasterDataFile() {
        return masterDataFile;
    }

    public void setMasterDataFile(String masterDataFile) {
        this.masterDataFile = masterDataFile;
    }

    @Override
    public String toString() {
        return "TimeSeriesJob{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", cron='" + cron + '\'' +
                ", timeseriesKeys=" + timeseriesKeys +
                ", startCron='" + startCron + '\'' +
                ", duration='" + duration + '\'' +
                ", nextStart='" + nextStart + '\'' +
                ", active=" + active +
                ", state=" + state +
                ", businessKey='" + businessKey + '\'' +
                ", suspended=" + suspended +
                ", fileUpload=" + fileUpload +
                ", mappingFile='" + mappingFile + '\'' +
                ", masterDataFile='" + masterDataFile + '\'' +
                '}';
    }
}
