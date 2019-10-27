package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {
    public Users CurrentUser;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<Bitmap> imageModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_Username_Reservation)))
                .setText(CurrentUser.getUsername());


        imageModelArrayList=new ArrayList<>();

        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));



        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(ReservationActivity.this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);


    }
}
