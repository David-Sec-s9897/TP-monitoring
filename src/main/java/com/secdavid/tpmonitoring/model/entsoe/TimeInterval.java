package com.secdavid.tpmonitoring.model.entsoe;

import java.time.ZonedDateTime;

public class TimeInterval implements Comparable<TimeInterval> {

    public ZonedDateTime start;
    public ZonedDateTime end;


    public TimeInterval(){
    }

    public TimeInterval(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

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
        return "TimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public int compareTo(TimeInterval that) {
        if (this.getStart().compareTo(that.start) != 0) {
            return this.getStart().compareTo(that.start);
        } else {
            return this.getEnd().compareTo(that.end);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeInterval)) return false;

        TimeInterval that = (TimeInterval) o;

        if (!start.equals(that.start)) return false;
        return end.equals(that.end);
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}
