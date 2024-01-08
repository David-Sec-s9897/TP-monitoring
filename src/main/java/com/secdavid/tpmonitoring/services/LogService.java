package com.secdavid.tpmonitoring.services;

import com.secdavid.tpmonitoring.model.logs.LogRecord;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Named
@ApplicationScoped
public class LogService implements Serializable {

    private List<LogRecord> logRecordList;

    @PostConstruct
    public void init(){
        logRecordList = new ArrayList<>();
    }

    public boolean persist(LogRecord logRecord) {
        return logRecordList.add(logRecord);
    }

    public List<LogRecord> loadAll() {
        return logRecordList;
    }

}
