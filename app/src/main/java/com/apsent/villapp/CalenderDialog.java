package com.apsent.villapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.absent.villapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderDialog extends DialogFragment {
    private Context context;
    private Ownerstate ownerstate;


    final static  List<Calendar> calendarList=new ArrayList<>();
    static int c=0;
    static String m_date;

    public void setContext(Context context) {
        this.context = context;
    }
    public void setOwnerstate(Ownerstate ownerstate) {
        this.ownerstate = ownerstate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.calender_layout,container);
        final CalendarView calendarView=((CalendarView)(view.findViewById(R.id.m_calenderView)));

//        calendarList.get(0);

        final long millis = System.currentTimeMillis();
        calendarView.setDate(millis);

        calendarView
                .setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                        String m_date=String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendarList.add(c,cal);

                        c=c+1;
                        if (c > 1) {
                            ((Ownerstate)context).daysBetween(calendarList.get(0),calendarList.get(1));

                            m_date= String.valueOf(calendarList.get(0).get(Calendar.YEAR))+"/"+
                                    String.valueOf(calendarList.get(0).get(Calendar.MONTH))+"/"+
                                    String.valueOf(calendarList.get(0).get(Calendar.DAY_OF_MONTH)) +" - "+
                                    String.valueOf(calendarList.get(1).get(Calendar.YEAR))+"/"+
                                    String.valueOf(calendarList.get(1).get(Calendar.MONTH))+"/"+
                                    String.valueOf(calendarList.get(1).get(Calendar.DAY_OF_MONTH)) ;



                            ((Ownerstate)context).set_date(m_date);
                            c = 0;
                            dismiss();
                        }





                    }
                });



        return view;
    }

    public static int daysBetween(Calendar startDate, Calendar endDate) {
        return Math.abs(startDate.get(Calendar.DAY_OF_MONTH)-endDate.get(Calendar.DAY_OF_MONTH));
    }
}
