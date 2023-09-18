package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.Point;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSeriesUtils {

    public static List<TimeSeries> mergeTimeSeries(List<TimeSeries> list) {
        if (list.size() >= 2) {
            for (int i = 0; i < list.size() - 1; i++) {
                int lastIndex = 0;
                int firstIndex = 1;
                for (int j = 1; j < list.size(); j++) {
                    TimeSeries current = list.get(i);
                    TimeSeries next = list.get(j);
                    if (current.compare(next)) {
                        list.get(i).getPeriod().getTimeInterval().setEnd(next.getPeriod().timeInterval.getEnd());
                        lastIndex = j;
                    } else {
                        break;
                    }
                }
                for (int a = 0; a<lastIndex; a++){
                    list.remove(firstIndex);
                }
            }
        }
        return list;
    }



    public static List<TimeSeries> deleteDownloaded(List<TimeSeries> current, ZonedDateTime end){

        for (TimeSeries ts:current) {
            List<Point> toDelete = new ArrayList<>();
            for (Point point : ts.getPeriod().getPoint()) {
                if(point.getDate().equals(end) || point.getDate().isAfter(end)){
                    toDelete.add(point);
                }
            }
            ts.getPeriod().getPoint().removeAll(toDelete);
        }
        return current;
    }
}
