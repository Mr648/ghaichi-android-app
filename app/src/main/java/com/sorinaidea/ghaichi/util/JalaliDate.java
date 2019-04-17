package com.sorinaidea.ghaichi.util;

import java.util.Locale;

public class JalaliDate {
    public int year, month, day;

    public JalaliDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JalaliDate) {
            JalaliDate other = (JalaliDate) obj;
            return this.year == other.year && this.month == other.month && this.day == other.day;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%04d/%02d/%02d", year, month, day);
    }
}
