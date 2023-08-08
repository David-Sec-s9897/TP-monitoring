package com.secdavid.tpmonitoring.model.entsoe;

import java.time.ZonedDateTime;
import java.util.Date;

public class PeriodTimeInterval {
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

    @Override
    public String toString() {
        return "PeriodTimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}