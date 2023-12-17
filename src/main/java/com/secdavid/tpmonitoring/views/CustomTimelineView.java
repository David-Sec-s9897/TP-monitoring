package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.services.TPProcessService;
import com.secdavid.tpmonitoring.util.DateTimeUtils;
import com.secdavid.tpmonitoring.util.TimeSeriesUtils;
import com.secdavid.tpmonitoring.views.enums.TimelineEventType;
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
            for (TimeInterval ts : process.getAvailableTimeIntervals()) {
                TimelineEvent event = buildTimeLineEvent(TimelineEventType.AVAILABLE, ts.start, ts.end, process.getName());
                model.add(event);
            }
            List<TimeInterval> availableTimeIntervals = process.getAvailableTimeIntervals();
            if (availableTimeIntervals != null) {
                List<TimeInterval> missingIntervals = TimeSeriesUtils.getMissingIntervals(process.getAvailableTimeIntervals(), process.getMissingDataTolerance());
                for (TimeInterval missing : missingIntervals) {
                    TimelineEvent event = buildTimeLineEvent(TimelineEventType.UNAVAILABLE, missing.start, missing.end, process.getName());
                    model.add(event);
                }
            }
        }
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

    private TimelineEvent<Object> buildTimeLineEvent(TimelineEventType timelineEventType, ZonedDateTime start, ZonedDateTime end, String name) {
        return TimelineEvent.builder()
                .data(String.format("%s %s - %s", timelineEventType.getName(),
                        DateTimeUtils.getHumanReadableFormatFormat().format(start),
                        DateTimeUtils.getHumanReadableFormatFormat().format(end)))
                .startDate(start.toLocalDateTime())
                .endDate(end.toLocalDateTime())
                .editable(false)
                .group(name)
                .styleClass(timelineEventType.getStyleClass())
                .build();
    }
}