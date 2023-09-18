package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
import com.secdavid.tpmonitoring.services.TPProcessService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

@Named("customTimelineView")
@ViewScoped
public class CustomTimelineView implements Serializable {

    @Inject
    TPProcessService service;

    private TimelineModel<String, ?> model;
    private LocalDateTime start;
    private LocalDateTime end;

    @PostConstruct
    public void init() {
        // set initial start / end dates for the axis of the timeline
        start = LocalDate.now().minusDays(1).atStartOfDay();
        end = LocalDate.now().plusDays(1).atStartOfDay();

        // create timeline model
        model = new TimelineModel<>();

        buildAvailableTimeLineEvents();

        Map<String, List<TimelineEvent<String>>> groups = model.getEvents().stream()
                .collect(groupingBy(TimelineEvent::getGroup));

        for (String timelineGroup : groups.keySet()) {
            List<TimelineEvent<String>> events = groups.get(timelineGroup);
            for (int i = 0; i < events.size() - 1; i++) {
                TimelineEvent event = events.get(i);
                TimelineEvent nextEvent = events.get(i + 1);
                if (!event.getEndDate().equals(nextEvent.getStartDate())) {
                    model.add(createUnavailableEvent(event.getEndDate(), nextEvent.getStartDate(), timelineGroup));
                }
            }
        }
    }

    private void buildAvailableTimeLineEvents() {
        for (TpProcess process : service.getProcesses()) {
            Set<StartEndTime> set = new HashSet<>();
            for (TimeSeries ts : process.getTimeSeriesList()) {
                StartEndTime startEndTime = new StartEndTime(ts.getPeriod().timeInterval.start.toLocalDateTime(), ts.getPeriod().timeInterval.end.toLocalDateTime());
                if (!set.contains(startEndTime)) {
                    set.add(startEndTime);
                    TimelineEvent event = TimelineEvent.builder()
                            .data("Available")
                            .startDate(ts.getPeriod().timeInterval.start.toLocalDateTime())
                            .endDate(ts.getPeriod().timeInterval.end.toLocalDateTime())
                            .editable(false)
                            .group(process.getName())
                            .styleClass("available")
                            .build();
                    //System.out.println(ts.getPeriod().timeInterval.start.toLocalDateTime()+", "+ts.getPeriod().timeInterval.end.toLocalDateTime());
                    //System.out.println(ts);
                    model.add(event);
                }
            }
        }
    }


    private TimelineEvent createUnavailableEvent(LocalDateTime start, LocalDateTime end, String group) {
        System.out.println("Unavailable created");
        return TimelineEvent.builder()
                .data(String.format("Unavailable %s - %s", start.toString(), end.toString()))
                .startDate(start)
                .endDate(end)
                .editable(false)
                .group(group)
                .styleClass("unavailable")
                .build();
    }

    public TimelineModel<String, ?> getModel() {
        return model;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}