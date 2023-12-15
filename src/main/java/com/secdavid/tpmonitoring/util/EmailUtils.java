package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailUtils {
    public static final String ICON_OK = new String(Character.toChars(0x2705));
    public static final String ICON_NOT_OK = new String(Character.toChars(0x274C));
    public static final String ICON_UNKNOWN = new String(Character.toChars(0x2754));

    public static String buildUnavailabilityEmailText(Map<String, List<TimeInterval>> missingTimeIntervalsMap) {
        StringBuilder text = new StringBuilder();
        text.append("<h1>Transparency portal list of missing intervals</h1>" +
                "<table width='100%' border='1' align='center'>"
                + "<tr align='center'>"
                + "<td><b>Process Name <b></td>"
                + "<td><b>Missing Time Intervals<b></td>"
                + "</tr>");

        for (Map.Entry<String, List<TimeInterval>> entry : missingTimeIntervalsMap.entrySet()) {
            text.append("<tr align='center'>" + "<td>" + entry.getKey() + "</td>"
                    + "<td>" + entry.getValue().stream().map(timeInterval ->
                            DateTimeUtils.getHumanReadableFormatFormat().format(timeInterval.start)
                                    + " - " +
                                    DateTimeUtils.getHumanReadableFormatFormat().format(timeInterval.end))
                    .collect(Collectors.joining(",<br/> ")) + "</td>" + "</tr>");
        }
        return text.toString();
    }

    public static String buildSummaryEmailText(List<TpProcess> tpProcessList) {
        StringBuilder text = new StringBuilder();
        text.append("<h1>Transparency portal availability report</h1>" +
                "<table width='100%' border='1' align='center'>"
                + "<tr align='center'>"
                + "<td><b>Process Name <b></td>"
                + "<td><b>Status<b></td>"
                + "</tr>");

        for (TpProcess tpProcess : tpProcessList) {
            String status = (tpProcess.getLastTimeInterval().isPresent()) ?
                    DateTimeUtils.getHumanReadableFormatFormat().format(tpProcess.getLastTimeInterval().get().getEnd()) :
                    "no data";
            String icon = status.equals("no data") ? ICON_UNKNOWN : ICON_OK;
            text.append("<tr align='center'>" + "<td>" + tpProcess.getName() + "</td>"
                    + "<td>" + String.format("%s (%s)", icon, status) + "</td>" + "</tr>");
        }
        return text.toString();
    }


}
