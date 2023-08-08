package com.secdavid.tpmonitoring.model.entsoe;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeInterval {

    public static DateTimeFormatter FORMAT = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    public ZonedDateTime start;
    public ZonedDateTime end;

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
