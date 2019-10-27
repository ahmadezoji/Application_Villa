package com.absent.villaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminInsertctivity extends AppCompatActivity implements LocationListener {
    public Users CurrentUser;
    private Button btnCapture_Cam;
    private Button btnCapture_Gallery;
    private ImageView imgCapture;
    private Bitmap villaimgBMP = null;

    private String mCurrentPhotoPath;


    private static final int GALLERY_PICTURE = 1;
    private static final int CAMERA_REQUEST = 0;
//    public static final int RESULT_GALLERY = 0;

    private GoogleMap mMap;
    LatLng villa_latLng;
    public static final String BASE_URL = "http://84.241.1.59:9191/";
    private APIs apIs;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CurrentUser = (Users) bundle.get("user");
        ((TextView) (findViewById(R.id.m_Username_ShowVilla)))
                .setText(CurrentUser.getUsername());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIs = retrofit.create(APIs.class);


        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);



//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_InsertVilla);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                mMap = googleMap;
//                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                mMap.setMyLocationEnabled(true);
//                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//                    @Override
//                    public boolean onMyLocationButtonClick() {
//                        return false;
//                    }
//                });
//                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        // Add a marker in Sydney and move the camera
//                        mMap.clear();
//                        villa_latLng = new LatLng(latLng.latitude, latLng.longitude);
//                        mMap.addMarker(new MarkerOptions().position(villa_latLng));
//                        mMap.setMyLocationEnabled(true);
//
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(villa_latLng));
//                    }
//                });
//
//
//            }
//        });

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},123);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        imgCapture = (ImageView) findViewById(R.id.capturedImage);
//        /*click Capture By Gallery*/
        btnCapture_Gallery =(Button)findViewById(R.id.m_uploadBtn);
        btnCapture_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent galleryIntent = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent , RESULT_GALLERY );
                 Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_PICTURE);
            }
        });

        /*Click Capture By Camera*/
        btnCapture_Cam =(Button)findViewById(R.id.btnTakePicture);

        btnCapture_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent,CAMERA_REQUEST);
//                    MEDIA_TYPE_IMAGE_BY_CAMERA
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(final int requestCode,final String[] permissions,final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "You didn't give permission to access device location", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void AddVilla(final Villa villa)
    {
        Call<List<Villa>> call= apIs.createvillas(villa);
        call.enqueue(new Callback<List<Villa>>() {
            @Override
            public void onResponse(Call<List<Villa>> call, Response<List<Villa>> response) {
                if (response.isSuccessful())
                {
                    List<Villa> villas= new ArrayList<>();
                    villas=response.body();
                    if (villas!=null)
                    {
                        Toast.makeText(AdminInsertctivity.this,"ثبت نام با موفقیت بود  !!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(AdminInsertctivity.this, "ثبت نام ناموفق بود  !!", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Villa>> call, Throwable t) {

            }
        });

    }

    public void Onclick_BtnAdd(View view)
    {
        try{
                Villa villa=new Villa();


                EditText VTitle=(EditText)(findViewById(R.id.VTitle));
                EditText VCost=(EditText)(findViewById(R.id.VCost));
                EditText VRoomCnt=(EditText)(findViewById(R.id.VRoomCnt));
                EditText VCapacity=(EditText)(findViewById(R.id.VCapacity));
                EditText VArea=(EditText)(findViewById(R.id.VArea));
                EditText VAdderss=(EditText)(findViewById(R.id.VAdderss));

                villa.setTitle(VTitle.getText().toString());
                villa.setCost(Integer.valueOf(VCost.getText().toString()));
                villa.setRoomCount(Integer.valueOf(VRoomCnt.getText().toString()));
                villa.setCapacity(Integer.valueOf(VCapacity.getText().toString()));
                villa.setAddress(VAdderss.getText().toString());



                villa.setLat((float)villa_latLng.latitude);
                villa.setLon((float)villa_latLng.longitude);


                villa.setAdminUserId(CurrentUser.getUserId());


//            villaimgBMP = BitmapFactory.decodeResource(getResources(), R.drawable.index1);
            if (villaimgBMP!=null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                villaimgBMP.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArrayimg = stream.toByteArray();
                int size=byteArrayimg.length;
//                Coverpic=android.util.Base64.encodeToString(byteArrayimg,android.util.Base64.DEFAULT);

                villa.setPic(byteArrayimg);
            }
            else {
                villa.setPic(null);
            }


            AddVilla(villa);

            VTitle.setText("");
            VCost.setText("");
            VRoomCnt.setText("");
            VCapacity.setText("");
            VArea.setText("");
            VAdderss.setText("");
//            imgCapture.setImageBitmap(null);


        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error in Insert Item",Toast.LENGTH_LONG).show();
        }
    }
    public void Onclick_BtnCancle(View view) {

        Intent intent=new Intent(this,MainActivityAdmin.class);
        intent.putExtra("user",CurrentUser);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(resultCode==CAMERA_REQUEST)
            {
                Bitmap photo = (Bitmap)data.getExtras().get("data");
//                Drawable drawable=new BitmapDrawable(photo);
//                backGroundImageLinearLayout.setBackgroundDrawable(drawable);

            }
            else if(resultCode==GALLERY_PICTURE)
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                villaimgBMP= (BitmapFactory.decodeFile(picturePath));
//                Drawable drawable=new BitmapDrawable(thumbnail);
//                backGroundImageLinearLayout.setBackgroundDrawable(drawable);


            }
        }
    }

    //
    //
    /* Get Current Location CallBack*/
    //
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("","");
    }
    @Override
    public void onLocationChanged(Location location) {

        villa_latLng = new LatLng(location.getLatitude(), location.getLongitude());
       // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
       // mMap.animateCamera(cameraUpdate);
      //  locationManager.removeUpdates(this);
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.d("","");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("","");
    }

}



