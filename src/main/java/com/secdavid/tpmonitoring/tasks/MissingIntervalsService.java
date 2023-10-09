package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import jakarta.ejb.Stateless;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class MissingIntervalsService {

    private Map<String, List<TimeInterval>> missingTimeIntervalsMap = new HashMap<>();

    private Map<String, List<TimeInterval>> lastIterationMissingIntervalsMap = new HashMap<>();


    public void addToMissingIntervals(String processName, List<TimeInterval> missingIntervals) {
        this.missingTimeIntervalsMap.put(processName, missingIntervals);
    }

    public Map<String, List<TimeInterval>> getMissingTimeIntervalsMap() {
        return missingTimeIntervalsMap;
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
        return (!missingTimeIntervalsMap.entrySet().stream().allMatch(e -> e.getValue().equals(lastIterationMissingIntervalsMap.get(e.getKey()))));
    }

    public void updateLastMissingIntervals() {
        lastIterationMissingIntervalsMap.clear();
        lastIterationMissingIntervalsMap = missingTimeIntervalsMap;
    }
}
