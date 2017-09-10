package com.github.defio.horizontalpicker.model;

import org.joda.time.DateTime;

import java.util.Locale;

/**
 * Created by jhonn on 28/02/2017.
 */
public class Day {
    private DateTime date;
    private boolean selected;

    public Day(DateTime date) {
        this.date = date;
    }

    public String getDay() {
        return String.valueOf(date.getDayOfMonth());
    }

    public String getWeekDay() {
        return date
                .toString("EEE", Locale.getDefault())
                .toUpperCase();
    }


    public DateTime getDate() {
        return date.withTime(0, 0, 0, 0);
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
