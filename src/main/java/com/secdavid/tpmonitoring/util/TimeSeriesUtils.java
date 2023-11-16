package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.Point;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
import com.secdavid.tpmonitoring.tasks.BackgroundTaskManager;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
                if (point.getDate().equals(end) || point.getDate().isAfter(end)) {
                    toDelete.add(point);
                }
            }
            ts.getPeriod().getPoint().removeAll(toDelete);
        }
        return current;
    }


    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> timeIntervals) {
        if (timeIntervals.size() <= 1) {
            return timeIntervals;
        }
        for (int index = 0; index < timeIntervals.size() - 1; ) {
            TimeInterval first = timeIntervals.get(index);
            TimeInterval second = timeIntervals.get(index + 1);

            if (canBeMerged(first, second)) {
                first.end = second.end;
                timeIntervals.remove(index + 1);
            } else {
                index++;
            }
        }
        return timeIntervals;
    }

    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> mergedTimeIntervals, List<TimeInterval>... otherIntervals) {
        List<TimeInterval> result = (mergedTimeIntervals == null || mergedTimeIntervals.isEmpty()) ? new ArrayList<>() : mergedTimeIntervals;
        for (List<TimeInterval> timeIntervalList : otherIntervals) {
            result.addAll(timeIntervalList);
        }
        return mergeTimeIntervals(result.stream().sorted().collect(Collectors.toList()));
    }

    public static List<TimeInterval> getMissingIntervals(List<TimeInterval> mergedTimeIntervals, ZonedDateTime expectedEnd) {
        List<TimeInterval> missingIntervals = new ArrayList<>();
        if(mergedTimeIntervals.size() > 1) {
            for (int i = 0; i < mergedTimeIntervals.size() - 1; i++) {
                TimeInterval first = mergedTimeIntervals.get(i);
                TimeInterval second = mergedTimeIntervals.get(i + 1);
                if (!canBeMerged(first, second)) {
                    TimeInterval missing = new TimeInterval();
                    missing.setStart(first.end);
                    missing.setEnd(second.start);
                    missingIntervals.add(missing);
                }
            }
        }
        missingIntervals.addAll(checkForMissingData(mergedTimeIntervals, expectedEnd));
        return missingIntervals;
    }

    private static List<TimeInterval> checkForMissingData(List<TimeInterval> mergedTimeIntervals, ZonedDateTime expectedEnd){
        List<TimeInterval> missingIntervals = new ArrayList<>();
        if(!mergedTimeIntervals.isEmpty()) {
            TimeInterval lastTimeInterval = mergedTimeIntervals.get(mergedTimeIntervals.size() - 1);
            if (lastTimeInterval.getEnd().plus(BackgroundTaskManager.REPEAT_EVERY_MINUTES, ChronoUnit.MINUTES).isBefore(expectedEnd)) {
                TimeInterval missing = new TimeInterval();
                missing.setStart(lastTimeInterval.getEnd());
                missing.setEnd(expectedEnd);
                missingIntervals.add(missing);
            }
        }
        return missingIntervals;
    }

    private static boolean canBeMerged(TimeInterval first, TimeInterval second) {
        return !first.end.isBefore(second.start);
    }
}
