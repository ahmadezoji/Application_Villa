package com.apsent.villapp;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EditDialog extends DialogFragment implements LocationListener {
    private Context context;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private SupportMapFragment mapFragment;
    private GoogleMap mapDetail;
    private LatLng villa_latLng;
    private Villa villa;
    private Villa villa_edited;
    private VillaListOwner villaListOwner;

    private UploadServer uploadServer;


    private EditText title;
    private EditText address ;
    private EditText lat;
    private EditText lon;
    private EditText cost ;
    private EditText cost_weekend ;
    private EditText cost_special ;
    private EditText roomcount ;
    private EditText Capacity;
    private EditText area;

    public void setVillaListOwner(VillaListOwner villaListOwner) {
        this.villaListOwner = villaListOwner;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }

    public void setContext(Context context) {
        this.context = context;
        uploadServer =new UploadServer();

    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_dialog,container);

        title=view.findViewById(R.id.txt_title_editDlg);
       address =view.findViewById(R.id.txt_address_editDlg);
       cost = view.findViewById(R.id.txt_price_editDlg);
       cost_weekend = view.findViewById(R.id.txt_price_weekend_editDlg);
       cost_special = view.findViewById(R.id.txt_price_special_editDlg);
       roomcount =view.findViewById(R.id.txt_roomcnt_editDlg);
       Capacity=view.findViewById(R.id.txt_Capacity_editDlg);
       area=view.findViewById(R.id.txt_area_editDlg);

        title.setText(villa.getTitle());
        address.setText(villa.getAddress());
//        lat.setText(String.valueOf(villa.getLat()));
//        lon.setText(String.valueOf(villa.getLon()));
        cost.setText(String.valueOf(villa.getCost()));
        cost_weekend.setText(String.valueOf(villa.getCost_weekend()));
        cost_special.setText(String.valueOf(villa.getCost_special()));

        roomcount.setText(String.valueOf(villa.getRoomCount()));
        Capacity.setText(String.valueOf(villa.getCapacity()));
        area.setText(String.valueOf(villa.getArea()));



        ((Button)(view.findViewById(R.id.btn_gotoGallery)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,GalleryActivity.class);
                        intent.putExtra("villa",villa);
                        context.startActivity(intent);
                    }
                });

        ((Button)view.findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                villa_edited=new Villa();

                villa_edited.setVillaId(villa.getVillaId());
                villa_edited.setAdminUserId(villa.getAdminUserId());
                villa_edited.setGalleryid(villa.getGalleryid());
                villa_edited.setCover(villa.getCover());
                /*Edited*/
                villa_edited.setTitle(title.getText().toString());
                villa_edited.setAddress(address.getText().toString());

//                villa_edited.setLat(Float.valueOf(lat.getText().toString()));
//                villa_edited.setLon(Float.valueOf(lon.getText().toString()));

                villa_edited.setCost(Integer.valueOf(cost.getText().toString()));
                villa_edited.setCost_weekend(Integer.valueOf(cost_weekend.getText().toString()));
                villa_edited.setCost_special(Integer.valueOf(cost_special.getText().toString()));

                villa_edited.setRoomCount(Integer.valueOf(roomcount.getText().toString()));
                villa_edited.setCapacity(Integer.valueOf(Capacity.getText().toString()));
                villa_edited.setArea(Integer.valueOf(area.getText().toString()));

                /*------------------------------------------------*/

                ((VillaListOwner)context).editVilla(villa_edited);
                ((VillaListOwner)context).filllist();

                dismiss();
            }
        });
        ((Button)view.findViewById(R.id.btn_dismiss)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
