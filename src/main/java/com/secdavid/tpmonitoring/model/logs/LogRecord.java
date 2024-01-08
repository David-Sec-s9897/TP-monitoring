package com.secdavid.tpmonitoring.model.logs;

import com.secdavid.tpmonitoring.enums.Category;

import java.io.Serializable;

public class LogRecord implements Serializable {

    LogRecordType logRecordType;
    String processName;
    Category category;
    String message;

    LoadedDataType loadedDataType;

    public LogRecord(LogRecordType logRecordType, String processName,Category category, String message, LoadedDataType loadedDataType) {
        this.logRecordType = logRecordType;
        this.processName = processName;
        this.category = category;
        this.message = message;
        this.loadedDataType = loadedDataType;
    }

}
