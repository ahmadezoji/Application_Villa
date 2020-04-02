package com.apsent.villapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absent.villaapp.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCustomer extends AppCompatActivity implements VillaListOwner, LocationListener {
    private CardAdapterCustomer adapter;
    private static RecyclerView recyclerView;

    private VillaController villaController;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LatLng currentPos;
    private float  currentZoom = 5.0f;
    private CameraPosition cameraPosition;

    public Users CurrentUser;

    private List<Villa> villaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

        villaController = new VillaController();
        villaList = new ArrayList<>();
        getAllVillas();

        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                mMap.setMyLocationEnabled(true);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.37222794178551,54.63216595351696), 5.0f));
                showLocations(villaList);

                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        return false;
                    }

                });
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition pos) {
                        if (pos.zoom != currentZoom){
                            cameraPosition=pos;
                            currentZoom = pos.zoom;
                            currentPos =new LatLng(pos.target.latitude,pos.target.longitude);
                            filllist();
                            showLocations(villaList);
                        }
                    }
                });
                mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        CameraPosition Position = mMap.getCameraPosition();
                        currentPos =new LatLng(Position.target.latitude,Position.target.longitude);
                        filllist();
                        showLocations(villaList);
                    }
                });
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CurrentUser = (Users) bundle.get("user");
        ((TextView) (findViewById(R.id.m_UserName_Customer)))
                .setText(CurrentUser.getName());

        this.filllist();

    }
    private void showLocations(List<Villa> villas) {
        if (villas != null) {
            for (int i = 0; i < villas.size(); i++) {
                LatLng VillaLoc = new LatLng(villas.get(i).getLat(), villas.get(i).getLon());

                if (VillaLoc.latitude != 0.0 && VillaLoc.longitude != 0.0) {
                    mMap.addMarker(new MarkerOptions().position(VillaLoc).title(villas.get(i).getTitle())).setVisible(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(VillaLoc));
                }
            }
        }
    }

    private void getAllVillas() {
        villaList = villaController.getAllVilles();
        if (villaList != null) {
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            adapter = new CardAdapterCustomer(villaList, MainActivityCustomer.this, CurrentUser);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivityCustomer.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void filllist() {

        villaList = villaController.getVilla(currentPos,currentZoom);
        if (villaList != null) {
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            adapter = new CardAdapterCustomer(villaList, MainActivityCustomer.this, CurrentUser);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivityCustomer.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
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

    public void Customer_logout(View view) {
        Utils.writePreferences(this, Utils.PFREFRENCE_USER_LOGIN, Utils.PFREFRENCE_USER_LOGIN_KEY, "");
        Intent intent = new Intent(this, UserLoginActivity.class);
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

    @Override
    public void onLocationChanged(Location location) {
//        if (mMap!=null) {
//            villa_latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
//            mMap.animateCamera(cameraUpdate);
//            locationManager.removeUpdates(this);
//        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            return;
        }

    }
}
