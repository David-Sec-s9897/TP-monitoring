package com.secdavid.tpmonitoring.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter ISO_ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    private static final DateTimeFormatter ENTSOE_REQUEST_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private static final DateTimeFormatter HUMAN_READABLE_FORMAT_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    public static DateTimeFormatter getISOFormatter() {
        return ISO_LOCAL_DATE_TIME_FORMATTER;
    }

    public static DateTimeFormatter getZonedDateTimeFormatter() {
        return ISO_ZONED_DATE_TIME_FORMATTER;
    }

    public static DateTimeFormatter getEntsoeRequestDateFormat() {
        return ENTSOE_REQUEST_DATE_FORMAT;
    }

    public static DateTimeFormatter getHumanReadableFormatFormat() {
        return HUMAN_READABLE_FORMAT_FORMAT;
    }
}
