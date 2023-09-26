package com.secdavid.tpmonitoring.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter formatter =  DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static DateTimeFormatter getISOFormatter() {
        return formatter;
    }
}
