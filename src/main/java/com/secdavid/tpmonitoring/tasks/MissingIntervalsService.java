package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import jakarta.ejb.Singleton;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class MissingIntervalsService {

    private Map<String, List<TimeInterval>> missingTimeIntervalsMap = new HashMap<>();

    private Map<String, List<TimeInterval>> lastIterationMissingIntervalsMap = new HashMap<>();
    public static final String ignoredIntervals = System.getProperty("tp.missingIntervals.ignoreList");
    List<String> ignoredIntervalsList = getIgnoredIntervals();


    public void addToMissingIntervals(String processName, List<TimeInterval> missingIntervals) {
        this.missingTimeIntervalsMap.put(processName, missingIntervals);
    }

    public Map<String, List<TimeInterval>> getMissingTimeIntervalsMap() {
        return missingTimeIntervalsMap;
    }

    public Map<String, List<TimeInterval>> getMissingTimeIntervalsMapFilterIgnored() {
        return missingTimeIntervalsMap.entrySet().stream().filter(e -> !ignoredIntervalsList.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setMissingTimeIntervalsMap(Map<String, List<TimeInterval>> missingTimeIntervalsMap) {
        this.missingTimeIntervalsMap = missingTimeIntervalsMap;
    }

    public Map<String, List<TimeInterval>> getLastIterationMissingIntervalsMap() {
        return lastIterationMissingIntervalsMap;
    }

    public void setLastIterationMissingIntervalsMap(Map<String, List<TimeInterval>> lastIterationMissingIntervalsMap) {
        this.lastIterationMissingIntervalsMap = lastIterationMissingIntervalsMap;
    }

    public boolean areSomeNewIntervals() {
        return (!missingTimeIntervalsMap.entrySet().stream().filter(e -> !ignoredIntervalsList.contains(e.getKey()))
                .allMatch(e -> e.getValue().equals(lastIterationMissingIntervalsMap.get(e.getKey()))));
    }

    public void updateLastMissingIntervals() {
        lastIterationMissingIntervalsMap.clear();
        lastIterationMissingIntervalsMap = missingTimeIntervalsMap;
    }

    /**
     * Find all missing intervals with start date before specified date.
     * @param startDate
     * @return
     */
    public List<TimeInterval> loadMissingIntervalsBeforeDate(String processName, ZonedDateTime startDate) {
        List<TimeInterval> processTimeIntervals = missingTimeIntervalsMap.get(processName);
        if (processTimeIntervals != null){
            return processTimeIntervals.stream().filter(timeInterval -> !timeInterval.getStart().isAfter(startDate)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<String> getIgnoredIntervals() {
        return (ignoredIntervals== null || ignoredIntervals.isBlank())?
                new ArrayList<>():Arrays.stream(ignoredIntervals.split(",")).toList();
    }
}
