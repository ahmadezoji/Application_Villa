package com.absent.villaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absent.villaapp.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCustomer extends AppCompatActivity implements VillaListOwner {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Villa> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private VillaController  villaController;
    private GoogleMap mMap;

    public Users CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

        villaController=new VillaController();

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")).setVisible(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            }
        });


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_UserName_Customer)))
                .setText(CurrentUser.getName());

        this.filllist();
    }

    @Override
    public void filllist() {

        List<Villa> villas = villaController.getAllVilles();
        if(villas!=null)
        {
            ArrayList<LatLng> latLngs=new ArrayList<>();
                for (int i=0;i<villas.size();i++)
                {
                    LatLng VillaLoc = new LatLng(villas.get(i).getLat(),villas.get(i).getLon());

                    if (VillaLoc.latitude !=0.0 && VillaLoc.longitude!=0.0) {
                        MarkerOptions mo = new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())).visible(true);
                        Marker marker = mMap.addMarker(mo);
                        mo.anchor(0f, 0.5f);
                        marker.showInfoWindow();

                        //                    mMap.addMarker(new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(VillaLoc));
                    }
                }


            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);

            /*Recycle view set adapter*///show Villas
            layoutManager = new LinearLayoutManager(MainActivityCustomer.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter = new CardAdapterCustomer(villas,MainActivityCustomer.this,CurrentUser);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void deleteVilla(int id) {

    }

    @Override
    public void editVilla(Villa villa) {

    }


    public class GetVillasTaskCustomer extends AsyncTask<Object,Object,ArrayList<Villa>>{
        @Override
        protected ArrayList<Villa> doInBackground(Object... objects) {
            try {
                ArrayList<Villa>villas=new ArrayList<>();

                String api=
                new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://127.0.0.1:8080/villas/all")
                        .build()
                )
                        .execute()
                        .body()
                        .string();

                /*Call back Fill list IF SUCCESS*/
                JSONArray jsonArray=new JSONArray(api);
                for (int i=0;i<jsonArray.length();i++) {
                    Villa villa=new Villa();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    villa.setVillaId(jsonObject.getInt("id"));
                    villa.setTitle(jsonObject.getString("title"));
                    villa.setRoomCount(jsonObject.getInt("roomcnt"));
                    villa.setCapacity(jsonObject.getInt("capacity"));
                    villa.setLat(jsonObject.getInt("lat"));
                    villa.setLon(jsonObject.getInt("lon"));
                    villa.setAddress(jsonObject.getString("address"));
                    villa.setCover(jsonObject.getString("cover"));
                    villa.setCost(jsonObject.getInt("cost"));
                    villa.setAdminUserId(jsonObject.getInt("providerid"));
                    villas.add(villa);

                }


                return villas;
             }
            catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Villa> villas) {
            if (villas!=null)
            {
//                ((ListView)(findViewById(R.id.m_list2)))
//                        .setAdapter(new VillaAdapterCustomer(MainActivityCustomer.this,villas));


                ArrayList<LatLng> latLngs=new ArrayList<>();
//                for (int i=0;i<villas.size();i++)
//                {
//                    LatLng VillaLoc = new LatLng(villas.get(i).getLat(),villas.get(i).getLon());
//
//                    MarkerOptions mo = new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())).visible(true);
//                    Marker marker = mMap.addMarker(mo);
//                    mo.anchor(0f, 0.5f);
//                    marker.showInfoWindow();
//
//
////                    mMap.addMarker(new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(VillaLoc));
//                }


                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);

                /*Recycle view set adapter*///show Villas
                layoutManager = new LinearLayoutManager(MainActivityCustomer.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapter = new CardAdapterCustomer(villas,MainActivityCustomer.this,CurrentUser);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(MainActivityCustomer.this,"ناموجود ",Toast.LENGTH_LONG).show();
            }
        }
    }
    /*Menu Functions Override*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signOut: {
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
            }


        }
        return true;
    }
}
