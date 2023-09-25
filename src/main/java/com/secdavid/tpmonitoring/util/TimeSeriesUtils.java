package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.Point;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                for (int a = 0; a < lastIndex; a++) {
                    list.remove(firstIndex);
                }
            }
        }
        return list;
    }


    public static List<TimeSeries> deleteDownloaded(List<TimeSeries> current, ZonedDateTime end) {

        for (TimeSeries ts : current) {
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
    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> timeIntervals) {
        return mergeTimeIntervals(timeIntervals, 0);
    }

    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> timeIntervals, int index) {
        if (timeIntervals.size() <= 1 || index+1 >= timeIntervals.size()) {
            return timeIntervals;
        }
        TimeInterval first = timeIntervals.get(index);
        TimeInterval second = timeIntervals.get(index+1);
        if (canBeMerged(first, second)) {
            first.end = second.end;
            timeIntervals.remove(second);
            mergeTimeIntervals(timeIntervals, 0);
        }
        else {
            mergeTimeIntervals(timeIntervals, ++index);
        }
        return timeIntervals;

    }

    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> mergedTimeIntervals, List<TimeInterval>... otherIntervals) {
        List<TimeInterval> result = (mergedTimeIntervals == null || mergedTimeIntervals.isEmpty())? new ArrayList<>(): mergedTimeIntervals;
        for (List<TimeInterval> timeIntervalList: otherIntervals) {
            result.addAll(timeIntervalList);
        }
        return mergeTimeIntervals(result.stream().sorted().collect(Collectors.toList()), 0);
    }

    public static List<TimeInterval> getMissingIntervals(List<TimeInterval> mergedTimeIntervals){
        List<TimeInterval> missingIntervals = new ArrayList<>();
        for (int i = 0; i < mergedTimeIntervals.size()-1; i++) {
            TimeInterval first = mergedTimeIntervals.get(i);
            TimeInterval second = mergedTimeIntervals.get(i+1);
            if (!canBeMerged(first, second)){
                TimeInterval missing = new TimeInterval();
                missing.setStart(first.end);
                missing.setEnd(second.start);
                missingIntervals.add(missing);
            }
        }
        return missingIntervals;
    }


        private static boolean canBeMerged(TimeInterval first, TimeInterval second) {
        return !first.end.isBefore(second.start);
    }
}
