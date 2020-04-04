package com.apsent.villapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.absent.villapp.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationActivity extends AppCompatActivity implements Ownerstate {
    public Users CurrentUser;
    public Villa Currentvilla;

    private APIs apIs;

    private static ViewPager mPager;
    private ArrayList<Bitmap> imageModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        Currentvilla=(Villa) bundle.get("villa");
        ((TextView)(findViewById(R.id.m_Username_Reservation)))
                .setText(CurrentUser.getName());



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);


        /*Fill Villa Details*/

        imageModelArrayList=new ArrayList<>();

//        imageModelArrayList.add(Currentvilla.getPic());
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));
        imageModelArrayList.add(BitmapFactory.decodeResource(getResources(),R.drawable.villa5));


//
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(new SlidingImage_Adapter(ReservationActivity.this,imageModelArrayList));
//
//        CirclePageIndicator indicator = (CirclePageIndicator)
//                findViewById(R.id.indicator);
//
//        indicator.setViewPager(mPager);
//
//
//        /*------------------------------------------------------------------*/
//
//        final float density = getResources().getDisplayMetrics().density;
//
//        indicator.setRadius(5 * density);

    }


    @Override
    public void set_date(String date) {
        ((EditText)(findViewById(R.id.txt_startdate)))
                .setText(date);
    }
    public void Onclick_CalenderView(View view)
    {
        CalenderDialog dialog=new CalenderDialog();
        dialog.setContext(ReservationActivity.this);
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
        //        dialog.setVillaListOwner((VillaListOwner) context);
        FragmentManager fm =(ReservationActivity.this).getFragmentManager();
        dialog.show(fm,"");
    }
    public void Onclick_CancleReserve(View view)
    {
        Intent intent=new Intent(this, MainActivityCustomer.class);
        startActivity(intent);
    }
    public void Onclick_Reserve(View view)
    {
        try {


                String startdate=
                        ((EditText)(findViewById(R.id.txt_startdate)))
                        .getText().toString();

//                Integer reserve_duration=2;
                Integer reserve_duration=Integer.valueOf(
                        ((EditText)(findViewById(R.id.txt_duration)))
                                .getText().toString());

                CustomerReservation reservation=new CustomerReservation(
                        CurrentUser.getId(),Currentvilla.getVillaId(),startdate,reserve_duration );

                addReservation(reservation);


        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error In Reservation !!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int daysBetween(Calendar startDate, Calendar endDate) {

        int duration=Math.abs(startDate.get(Calendar.DAY_OF_MONTH)-endDate.get(Calendar.DAY_OF_MONTH));
        ((EditText)(findViewById(R.id.txt_duration)))
                .setText(String.valueOf(
                        duration
                ));

        return duration;
    }

    public void addReservation(CustomerReservation reservation)
    {
        Call<Boolean> call=apIs.addReservarion(reservation);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    if ((Boolean) response.body())
                    {
                        Toast.makeText(ReservationActivity.this,"Reservation Successed !!",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ReservationActivity.this,"Reservation Failed !!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void checkAutenticateUser() {

    }
}
