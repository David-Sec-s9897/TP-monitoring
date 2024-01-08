package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.model.logs.LogRecord;
import com.secdavid.tpmonitoring.services.LogService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class LogView implements Serializable {

    List<LogRecord> logRecordList;

    @Inject
    LogService logService;

    @PostConstruct
    public void init(){
        logService.loadAll();
    }

    public List<LogRecord> getLogRecordList() {
        return logRecordList;
    }

    public void setLogRecordList(List<LogRecord> logRecordList) {
        this.logRecordList = logRecordList;
    }
}
