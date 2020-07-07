package com.a65apps.kalimullinilnazrafilovich.interactors.calendar;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class BirthdayCalendarModel implements BirthdayCalendar {
    @Override
    public GregorianCalendar getBirthdayCalendar() {
        return (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
    }
}
