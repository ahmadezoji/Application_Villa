package com.absent.villaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminInsertActivity extends AppCompatActivity implements LocationListener {
    public Users CurrentUser;

    private Button btnCapture_Cam;
    private Button btnCapture_Gallery;

    private Bitmap coverBmp = null;


    EditText title;
    EditText cost;
    EditText room_count;
    EditText capacity;
    EditText area;
    EditText address;

    TextView UserName;
    ImageView cover;
    LatLng villa_latLng;

    private VillaController villaController;

    private static final int GALLERY_PICTURE = 20;
    private static final int TAKE_PICTURE = 1;

    private GoogleMap mMap;

    public static final String BASE_URL = "http://192.168.1.42:8080/";
    private APIs apIs;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert);
        cast();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CurrentUser = (Users) bundle.get("user");

        villaController = new VillaController();


        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_InsertVilla);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        return false;
                    }
                });
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // Add a marker in Sydney and move the camera
                        mMap.clear();
                        villa_latLng = new LatLng(latLng.latitude, latLng.longitude);
                        mMap.addMarker(new MarkerOptions().position(villa_latLng));
                        mMap.setMyLocationEnabled(true);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(villa_latLng));
                    }
                });


            }
        });

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},123);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



//        /*click Capture By Gallery*/
        btnCapture_Gallery =(Button)findViewById(R.id.m_uploadBtn);
        btnCapture_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(intent, GALLERY_PICTURE);

            }
        });

        /*Click Capture By Camera*/
        btnCapture_Cam =(Button)findViewById(R.id.btnTakePicture);

        btnCapture_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photo));
//                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });
    }

    public void Onclick_addpic(View view)
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_PICTURE);
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


    private void cast()
    {
        title=(EditText)(findViewById(R.id.VTitle));
        cost=(EditText)(findViewById(R.id.VCost));
        room_count=(EditText)(findViewById(R.id.VRoomCnt));
        capacity=(EditText)(findViewById(R.id.VCapacity));
        area=(EditText)(findViewById(R.id.VArea));
        address=(EditText)(findViewById(R.id.VAdderss));
        cover =(ImageView)findViewById(R.id.adminInsert_cover);
    }
    public void Onclick_BtnAdd(View view)
    {
        try{

            Villa villa=new Villa();

            villa.setTitle(title.getText().toString());
            villa.setAddress(address.getText().toString());
            villa.setLat((float)villa_latLng.latitude);
            villa.setLon((float)villa_latLng.longitude);
            villa.setCost(Integer.valueOf(cost.getText().toString()));
            villa.setRoomCount(Integer.valueOf(room_count.getText().toString()));
            villa.setCapacity(Integer.valueOf(capacity.getText().toString()));
            villa.setArea(Integer.valueOf(area.getText().toString()));
            villa.setCover(UploadCoverToServer(coverBmp));
            villa.setAdminUserId(CurrentUser.getId());

            if(villaController.AddVilla(villa))
                Toast.makeText(this,"Insert Success !",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Error in Insert Item",Toast.LENGTH_LONG).show();

            Control_Default();


        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error in Insert Item",Toast.LENGTH_LONG).show();
        }
    }
    private String UploadCoverToServer(Bitmap bitmap)
    {
        return "";
    }
    private void Control_Default()
    {
        title.setText("");
        cost.setText("");
        room_count.setText("");
        capacity.setText("");
        area.setText("");
        address.setText("");
    }
    public void Onclick_BtnCancle(View view) {

        Intent intent=new Intent(this,MainActivityAdmin.class);
        intent.putExtra("user",CurrentUser);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == TAKE_PICTURE) {
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                cover.setImageBitmap(photo);
                coverBmp =photo;

            } else if (requestCode == GALLERY_PICTURE) {
                try{
                    Uri imageUri = data.getData();
                    coverBmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    cover.setImageURI(imageUri);
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Erroe in Image Insert !!", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("","");
    }
    @Override
    public void onLocationChanged(Location location) {

        villa_latLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
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



