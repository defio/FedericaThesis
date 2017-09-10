package com.github.defio.horizontalpicker.model;

import org.joda.time.DateTime;

import java.util.Locale;

/**
 * Created by jhonn on 28/02/2017.
 */
public class Month {
    private DateTime date;
    private boolean selected;

    public Month(DateTime date) {
        this.date = date;
    }

    public String getMonth() {
        return date.toString("MMMM", Locale.getDefault());
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
