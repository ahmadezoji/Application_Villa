package com.apsent.villapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.absent.villaapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;


public class AdminInsertActivity extends AppCompatActivity implements LocationListener {
    public Users CurrentUser;
    private UploadServer uploadServer;
    private Villa currentVilla;

    public boolean isVilla_register_state() {
        return villa_register_state;
    }

    public void setVilla_register_state(boolean villa_register_state) {
        this.villa_register_state = villa_register_state;
    }

    private boolean villa_register_state;

    private Button btnCapture_Cam;
    private Button btnCapture_Gallery;

    private Bitmap coverBmp = null;
    private Uri coverUri = null
            ;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;


    //Uri to store the image uri
    private Uri filePath;


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

    private APIs apIs;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insert);

        uploadServer = new UploadServer();
        uploadServer.setContext(this);
        cast();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CurrentUser = (Users) bundle.get("user");

        villaController = new VillaController();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVilla_register_state())
                {
                    showFileChooser();
                    /*
                     * upload pic update query in villa added
                     * */
                    //String url =  UploadCoverToServer(coverUri);
                    //update villa set cover =url


                }


            }
        });
        locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_InsertVilla);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
                showFileChooser();

            }
        });

        /*Click Capture By Camera*/
        btnCapture_Cam =(Button)findViewById(R.id.btnTakePicture);

        btnCapture_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });
    }

    public void Onclick_addpic(View view)
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_PICTURE);
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
        addVilla();
    }
    private void updateVilla()
    {
        try
        {
            villaController.EditVilla(currentVilla);
            Toast.makeText(this, "upload complete !", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
    private void addVilla()
    {
        try{

            currentVilla =new Villa();

            currentVilla.setTitle(title.getText().toString());
            currentVilla.setAddress(address.getText().toString());
            currentVilla.setLat((float)villa_latLng.latitude);
            currentVilla.setLon((float)villa_latLng.longitude);
            currentVilla.setCost(Integer.valueOf(cost.getText().toString()));
            currentVilla.setRoomCount(Integer.valueOf(room_count.getText().toString()));
            currentVilla.setCapacity(Integer.valueOf(capacity.getText().toString()));
            currentVilla.setArea(Integer.valueOf(area.getText().toString()));
//            currentVilla.setCover(UploadCoverToServer(coverUri));
            currentVilla.setGalleryid(CurrentUser.getId());
            currentVilla.setAdminUserId(CurrentUser.getId());

            currentVilla = villaController.AddVilla(currentVilla);
            if(currentVilla!=null) {
                Toast.makeText(this, "Insert Success !", Toast.LENGTH_LONG).show();
                setVilla_register_state(true);
            }
            else {
                Toast.makeText(this, "Error in Insert Item", Toast.LENGTH_LONG).show();
                setVilla_register_state(false);
            }

//            Control_Default();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error in Insert Item",Toast.LENGTH_LONG).show();
        }
    }
    private String UploadCoverToServer(Uri uri)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PackageInfo.REQUESTED_PERMISSION_GRANTED);
        }
        return uploadServer.uploadImage(uri);
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

            if (requestCode == PICK_IMAGE_REQUEST  && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    coverUri=filePath;
                    coverBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    cover.setImageBitmap(coverBmp);
                    if (isVilla_register_state())
                    {
                        String image_url = UploadCoverToServer(coverUri);
                        currentVilla.setCover(image_url);
                        if(villaController.EditVilla(currentVilla))
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == GALLERY_PICTURE) {
                try{
                    Uri imageUri = data.getData();
                    coverUri = imageUri;
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
    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}



