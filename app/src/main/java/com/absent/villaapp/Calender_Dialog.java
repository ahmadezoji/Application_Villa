package com.absent.villaapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class Calender_Dialog extends DialogFragment {
    private Context context;
    private Ownerstate ownerstate;

    public void setContext(Context context) {
        this.context = context;
    }
    public void setOwnerstate(Ownerstate ownerstate) {
        this.ownerstate = ownerstate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calender_layout,container);
        CalendarView calendarView=((CalendarView)(view.findViewById(R.id.m_calenderView)));



        final long millis = System.currentTimeMillis();
        calendarView.setDate(millis);

        calendarView
                .setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String m_date=String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth);
                        ((Ownerstate)context).set_date(m_date);

                        dismiss();


                    }
                });



        return view;
    }
}
