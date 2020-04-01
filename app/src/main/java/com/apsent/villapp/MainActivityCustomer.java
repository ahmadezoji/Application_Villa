package com.apsent.villapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private VillaController  villaController;
    private GoogleMap mMap;

    public Users CurrentUser;

    List<Villa> villaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

        villaController=new VillaController();
        villaList = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
//                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                showLocations(villaList);

            }
        });


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        CurrentUser=(Users) bundle.get("user");
        ((TextView)(findViewById(R.id.m_UserName_Customer)))
                .setText(CurrentUser.getName());

        this.filllist();
    }

    private void showLocations(List<Villa>villas)
    {
          if(villas!=null)
          {
              for(int i=0;i<villas.size();i++)
              {
                  LatLng VillaLoc = new LatLng(villas.get(i).getLat(),villas.get(i).getLon());

                  if (VillaLoc.latitude !=0.0 && VillaLoc.longitude!=0.0) {
                      mMap.addMarker(new MarkerOptions().position(VillaLoc).title(villas.get(i).getTitle())).setVisible(true);
                      mMap.moveCamera(CameraUpdateFactory.newLatLng(VillaLoc));
                  }
              }
          }
    }
    private List<Villa> getVillas()
    {
        return villaController.getAllVilles();
    }
    @Override
    public void filllist() {

        villaList = getVillas();
        if(villaList!=null)
        {
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);

            /*Recycle view set adapter*///show Villas
            layoutManager = new LinearLayoutManager(MainActivityCustomer.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter = new CardAdapterCustomer(villaList,MainActivityCustomer.this,CurrentUser);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void deleteVilla(Villa villa) {

    }
    @Override
    public void editVilla(Villa villa) {

    }

    @Override
    public String UploadCoverToServer(Uri uri) {
        return null;
    }

    public void Customer_logout(View view)
    {
        Utils.writePreferences(this,Utils.PFREFRENCE_USER_LOGIN,Utils.PFREFRENCE_USER_LOGIN_KEY,"");
        Intent intent=new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
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
