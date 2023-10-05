package com.secdavid.tpmonitoring.util;

import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailUtils {

    public static String buildEmailText(Map<String, List<TimeInterval>> missingTimeIntervalsMap){
        StringBuilder text = new StringBuilder();
        text.append("<h1>Transparency portal list of missing intervals</h1>" +
                "<table width='100%' border='1' align='center'>"
                + "<tr align='center'>"
                + "<td><b>Process Name <b></td>"
                + "<td><b>Missing Time Intervals<b></td>"
                + "</tr>");

        for (Map.Entry<String, List<TimeInterval>> entry : missingTimeIntervalsMap.entrySet()) {
            System.out.println(entry.getKey() + " :" + entry.getValue());
            text.append("<tr align='center'>"+"<td>" + entry.getKey() + "</td>"
                    + "<td>" + entry.getValue().stream().map(timeInterval -> timeInterval.start  + " - " + timeInterval.end).collect(Collectors.joining(","))+ "</td>"+"</tr>");
        }
        return text.toString();
    }
}
