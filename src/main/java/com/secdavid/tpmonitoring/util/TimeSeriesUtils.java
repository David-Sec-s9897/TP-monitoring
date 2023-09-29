package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.Point;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static List<TimeInterval> getMissingIntervals(List<TimeInterval> mergedTimeIntervals) {
        List<TimeInterval> missingIntervals = new ArrayList<>();
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
        return missingIntervals;
    }


    private static boolean canBeMerged(TimeInterval first, TimeInterval second) {
        return !first.end.isBefore(second.start);
    }

    public static String buildEmailText(Map<String, List<TimeInterval>> missingTimeIntervalsMap){
        StringBuilder text = new StringBuilder();
        text.append("<table width='100%' border='1' align='center'>"
                + "<tr align='center'>"
                + "<td><b>Process Name <b></td>"
                + "<td><b>Missing Time Intervals<b></td>"
                + "</tr>");

        for (Map.Entry entry : missingTimeIntervalsMap.entrySet()) {
            System.out.println(entry.getKey() + " :" + entry.getValue());
            text.append("<tr align='center'>"+"<td>" + entry.getKey() + "</td>"
                    + "<td>" + entry.getValue() + "</td>"+"</tr>");
        }
        return text.toString();
    }
}
