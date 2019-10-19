package com.absent.villaapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminInsertctivity extends AppCompatActivity {
    public Users CurrentUser;
    private Button btnCapture_Cam;
    private Button btnCapture_Gallery;
    private ImageView imgCapture;
    private Bitmap villaimgBMP;
    private static final int Image_Capture_Code = 1;
    public static final int RESULT_GALLERY = 0;
    private GoogleMap mMap;
    LatLng villa_latLng;
    public static final String  BASE_URL = "http://84.241.1.59:9191/";
    private APIs apIs;
//    private static final String[] INITIAL_PERMS={
//            Manifest.permission.ACCESS_FINE_LOCATION
//    };
//    private static final String[] LOCATION_PERMS={
//            Manifest.permission.ACCESS_FINE_LOCATION
//    };
//    private static final int INITIAL_REQUEST=1337;
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
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_InsertVilla);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                mMap = googleMap;
//                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        // Add a marker in Sydney and move the camera
//                        mMap.clear();
//                        villa_latLng = new LatLng(latLng.latitude, latLng.longitude);
//                        mMap.addMarker(new MarkerOptions().position(villa_latLng));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(villa_latLng));
//                    }
//                });
//
//
//            }
//        });



        /*Click Capture By Camera*/
        btnCapture_Cam = (Button) findViewById(R.id.btnTakePicture);

        btnCapture_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, Image_Capture_Code);
            }
        });
    }
    private void Code()
    {
        //        if (!canAccessLocation()) {
//            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
//        }


//        LocationManager lm =   (LocationManager)getSystemService(AdminInsertctivity.this.LOCATION_SERVICE);
//        LocationListener ll = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                double lat = location.getLatitude();
//                double lng = location.getLongitude();
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);


//        imgCapture = (ImageView) findViewById(R.id.capturedImage);
//        /*click Capture By Gallery*/
//        btnCapture_Gallery =(Button)findViewById(R.id.m_uploadBtn);
//        btnCapture_Gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent galleryIntent = new Intent(
//                        Intent.ACTION_PICK,
//                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent , RESULT_GALLERY );
//            }
//        });

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                villaimgBMP = (Bitmap) data.getExtras().get("data");
                imgCapture.setImageBitmap(villaimgBMP);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == RESULT_GALLERY)     {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor == null || cursor.getCount() < 1) {
                    return; // no cursor or no record. DO YOUR ERROR HANDLING
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if(columnIndex < 0) // no column index
                    return; // DO YOUR ERROR HANDLING
                String picturePath = cursor.getString(columnIndex);
                cursor.close(); // close cursor
                /*Set Image Into IMAGE_VIEW*/
                try{
                    File f= new File(picturePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                    villaimgBMP = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                    imgCapture.setImageBitmap(villaimgBMP);
                }
                catch (Exception e)
                {
                    imgCapture.setImageBitmap(null);
                    Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
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

//                villa.setLat((float)villa_latLng.latitude);
//                villa.setLon((float)villa_latLng.longitude);

                villa.setLat((float)63.2);
                villa.setLon((float) 53.6);

                villa.setAdminUserId(CurrentUser.getUserId());


            villaimgBMP = BitmapFactory.decodeResource(getResources(), R.drawable.index1);
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



//            new DatabaseHelper(this).AddVilla(villa);



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



}

