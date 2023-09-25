package com.secdavid.tpmonitoring.model.entsoe;

import java.util.List;

public class Period{
    public TimeInterval timeInterval;
    public String resolution;
    public List<Point> Point;

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public List<Point> getPoint() {
        return Point;
    }

    public void setPoint(List<Point> point) {
        Point = point;
    }

    @Override
    public String toString() {
        return "Period{" +
                "timeInterval=" + timeInterval +
                ", resolution='" + resolution + '\'' +
                ", Point=" + Point +
                '}';
    }
}