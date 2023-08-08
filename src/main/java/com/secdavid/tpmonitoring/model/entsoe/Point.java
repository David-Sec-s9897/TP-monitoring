package com.secdavid.tpmonitoring.model.entsoe;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Point {
    public ZonedDateTime date;
    public int position;
    public Double quantity;

    public int getPosition() {
        return position;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Point{" +
                "date=" + date.format(DateTimeFormatter.ISO_ZONED_DATE_TIME) +
                ", position=" + position +
                ", quantity=" + quantity +
                '}';
    }
}