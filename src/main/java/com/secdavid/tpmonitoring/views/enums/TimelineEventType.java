package com.secdavid.tpmonitoring.views.enums;

public enum TimelineEventType {

    AVAILABLE("Available","available"),
    UNAVAILABLE("Unavailable", "unavailable");

    String styleClass;
    String name;

    TimelineEventType(String name, String styleClass) {
        this.name = name;
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getName() {
        return name;
    }
}
