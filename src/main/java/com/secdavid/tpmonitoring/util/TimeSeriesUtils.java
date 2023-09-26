package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeSeriesUtils {

    /**
     * This method will merge several Timeintervals into one interval if start date of the next interval
     * is equal or even before the end of the previous one.
     *
     * @param timeIntervals List of timeintervals {@link TimeInterval} to be merged together
     * @return List of merged intervals. This list will contain one element if there is no gap in data.
     */
    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> timeIntervals) {
        return mergeTimeIntervals(timeIntervals, 0);
    }

    /**
     * This method will merge several Timeintervals into one interval if start date of the next interval
     * is equal or even before the end of the previous one.
     *
     * @param timeIntervals List of timeintervals {@link TimeInterval} to be merged together
     * @param startIndex    int index of start element.
     * @return List of merged intervals. This list will contain one element if there is no gap in data.
     */
    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> timeIntervals, int startIndex) {
        if (timeIntervals.size() <= 1 || startIndex + 1 >= timeIntervals.size()) {
            return timeIntervals;
        }
        TimeInterval first = timeIntervals.get(startIndex);
        TimeInterval second = timeIntervals.get(startIndex + 1);
        if (canBeMerged(first, second)) {
            first.end = second.end;
            timeIntervals.remove(second);
            mergeTimeIntervals(timeIntervals, 0);
        } else {
            mergeTimeIntervals(timeIntervals, ++startIndex);
        }
        return timeIntervals;

    }

    /**
     * This method will merge several Timeinterval lists  with existing timeinterval collection.
     * functionality is same with other mergeTimeIntervals methods but in this case you can
     * is equal or even before the end of the previous one.
     *
     * @param mergedTimeIntervals List of TimeIntervals {@link TimeInterval} which were already merged in a previous steps
     * @param otherIntervals      one or more another collections which needs to be merged to the existing  mergedTimeIntervals param.
     * @return List of merged intervals. This list will contain one element if there is no gap in data.
     */
    public static List<TimeInterval> mergeTimeIntervals(List<TimeInterval> mergedTimeIntervals, List<TimeInterval>... otherIntervals) {
        List<TimeInterval> result = (mergedTimeIntervals == null || mergedTimeIntervals.isEmpty()) ? new ArrayList<>() : mergedTimeIntervals;
        for (List<TimeInterval> timeIntervalList : otherIntervals) {
            result.addAll(timeIntervalList);
        }
        return mergeTimeIntervals(result.stream().sorted().collect(Collectors.toList()), 0);
    }

    /**
     * This merhod will return all gaps in data as a list of new TimeIntervals {@link TimeInterval}
     * each interval starts at the end of previous data interval and end at the beginning of the next interval which contains data.
     *
     * @param mergedTimeIntervals List of merged TimeIntervals {@link TimeInterval} to identify gaps.
     * @return List of TimeIntervals with gaps.
     * @example |---DATA INTERVAL-1---|   <Missing data>  |---DATA INTERVAL-2---|
     */
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


    /**
     * Intervals can be merged if first intervals end is equal or after next interval start.
     *
     * @param first  First TimeInterval
     * @param second Second TimeInterval
     * @return true if intervals are interconnecting each others or end time of first interval is equal to the start of the next one.
     * @examle 1 |---DATA INTERVAL-1---|---DATA INTERVAL-2---|
     * returns true;
     * @example 2
     * |---DATA INTERVAL-1---|
     * |---DATA INTERVAL-2---|
     * returns true;
     * @example 3
     * |---DATA INTERVAL-1---|   <some missing data>  |---DATA INTERVAL-2---|
     * returns false
     */
    private static boolean canBeMerged(TimeInterval first, TimeInterval second) {
        return !first.end.isBefore(second.start);
    }
}
