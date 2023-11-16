package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.services.TPProcessService;
import com.secdavid.tpmonitoring.util.DateTimeUtils;
import com.secdavid.tpmonitoring.util.TimeSeriesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        for (TpProcess process : service.getProcesses()) {

        }

        for (TpProcess process : service.getProcesses()) {
            Set<StartEndTime> set = new HashSet<>();
            for (TimeInterval ts : process.getAvailableTimeIntervals()) {
                TimelineEvent event = buildAvailableTimeLineEvent(ts.start, ts.end, process.getName());
                model.add(event);
            }
            if (process.getAvailableTimeIntervals() != null) {
                List<TimeInterval> missingIntervals = TimeSeriesUtils.getMissingIntervals(process.getAvailableTimeIntervals(), process.getLastSync());
                for (TimeInterval missing : missingIntervals) {
                    TimelineEvent event = buildUnAvailableTimeLineEvents(missing.start, missing.end, process.getName());
                    model.add(event);
                }
            }
        }
    }

    private TimelineEvent buildAvailableTimeLineEvent(ZonedDateTime start, ZonedDateTime end, String name) {
        return TimelineEvent.builder()
                .data(String.format("Available %s - %s", start.toString(), end.toString()))
                .startDate(start.toLocalDateTime())
                .endDate(end.toLocalDateTime())
                .editable(false)
                .group(name)
                .styleClass("available")
                .build();
    }


    private TimelineEvent buildUnAvailableTimeLineEvents(ZonedDateTime start, ZonedDateTime end, String group) {
        System.out.println("Unavailable created");
        return TimelineEvent.builder()
                .data(String.format("Unavailable %s - %s", start.toString(), end.toString()))
                .startDate(start.toLocalDateTime())
                .endDate(end.toLocalDateTime())
                .editable(false)
                .group(group)
                .styleClass("unavailable")
                .build();
    }

    public void onSelect(TimelineSelectEvent<String> e) {
        TimelineEvent<String> timelineEvent = e.getTimelineEvent();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "select", timelineEvent.getData() + ": " + timelineEvent.getStartDate().format(DateTimeUtils.getISOFormatter()) + " - " + timelineEvent.getEndDate().format(DateTimeUtils.getISOFormatter())));
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