package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.logs.LoadedDataType;
import com.secdavid.tpmonitoring.model.logs.LogRecord;
import com.secdavid.tpmonitoring.model.logs.LogRecordType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LogRecordUtils {
    public static LogRecordType getLogRecordType(boolean reload) {
        return reload ? LogRecordType.dataRelod : LogRecordType.dataLoad;
    }

    public static LoadedDataType getLoadedDataType(DefaultMarketDocument document) {
        if (document == null){
            return LoadedDataType.NO_DATA_AVAILABLE;
        }
        ZonedDateTime requestedStart = document.getPeriodTimeInterval().getStart();
        ZonedDateTime requestedEnd = document.getPeriodTimeInterval().getEnd();
        List<TimeInterval> timeIntervals = document.getTimeSeries().stream()
                .map(timeSeries -> timeSeries.getPeriod().getTimeInterval())
                .collect(Collectors.toList());

        if (timeIntervals.stream().allMatch(
                timeInterval -> timeInterval.start.equals(requestedStart) && timeInterval.end.equals(requestedEnd))
        ) {
            return LoadedDataType.LOADED_FULL_INTERVAL_DATA;
        }
        return LoadedDataType.LOADED_SEVERAL_PARTIAL_INTERVLAS;
    }


    public static LogRecord createLogRecord(LogRecordType logRecordType, String name, Category category, String message, DefaultMarketDocument receivedDocument) {
        return new LogRecord(logRecordType, name, category, message, getLoadedDataType(receivedDocument));
    }
}
