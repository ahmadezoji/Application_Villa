package com.absent.villaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardviewtest.CustomeAdapter;
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

public class MainActivityCustomer extends AppCompatActivity implements VillaListOwner {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Villa> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private GoogleMap mMap;



    public Users CurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

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
                .setText(CurrentUser.getUsername());

        this.filllist();
    }

    @Override
    public void filllist() {

        new GetVillasTaskCustomer().execute();
    }

    public class GetVillasTaskCustomer extends AsyncTask<Object,Object,ArrayList<Villa>>{
        @Override
        protected ArrayList<Villa> doInBackground(Object... objects) {
            try {
                ArrayList<Villa>villas=new ArrayList<>();

                String api=
                new OkHttpClient().newCall(
                        new Request.Builder()
                                .url("http://84.241.1.59:9191/villas/all")
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
                    villa.setPic((jsonObject.getString("cover")).getBytes());
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
                for (int i=0;i<villas.size();i++)
                {
                    LatLng VillaLoc = new LatLng(villas.get(i).getLat(),villas.get(i).getLon());

                    MarkerOptions mo = new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())).visible(true);
                    Marker marker = mMap.addMarker(mo);
                    mo.anchor(0f, 0.5f);
                    marker.showInfoWindow();


//                    mMap.addMarker(new MarkerOptions().position(VillaLoc).title(String.valueOf(villas.get(i).getCost())));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(VillaLoc));
                }


                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);

                /*Recycle view set adapter*///show Villas
                layoutManager = new LinearLayoutManager(MainActivityCustomer.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                adapter = new CustomeAdapter(villas);
                recyclerView.setAdapter(adapter);

            }
            else
            {
                Toast.makeText(MainActivityCustomer.this,"ناموجود ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
