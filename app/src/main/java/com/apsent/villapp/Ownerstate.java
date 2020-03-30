package com.apsent.villapp;

import java.util.Calendar;

public interface Ownerstate {

    void set_date(String date);
    int daysBetween(Calendar startDate, Calendar endDate);
    void checkAutenticateUser();
}
