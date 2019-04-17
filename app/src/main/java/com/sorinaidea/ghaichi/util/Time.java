package com.sorinaidea.ghaichi.util;

import java.util.Locale;

public class Time {
    public int hour, minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Time) {
            Time other = (Time) obj;
            return this.hour == other.hour && this.minute == other.minute;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d:%02d", hour, minute);
    }
}
