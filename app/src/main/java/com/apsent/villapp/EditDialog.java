package com.apsent.villapp;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.absent.villapp.R;

public class EditDialog extends DialogFragment {
    private Context context;

    private Villa villa;
    private Villa villa_edited;
    private VillaListOwner villaListOwner;

    private UploadServer uploadServer;


    private EditText title;
    private EditText address ;
    private EditText lat;
    private EditText lon;
    private EditText cost ;
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
       lat=view.findViewById(R.id.txt_lat_editDlg);
       lon=view.findViewById(R.id.txt_lon_editDlg);
       cost = view.findViewById(R.id.txt_price_editDlg);
       roomcount =view.findViewById(R.id.txt_roomcnt_editDlg);
       Capacity=view.findViewById(R.id.txt_Capacity_editDlg);
       area=view.findViewById(R.id.txt_area_editDlg);

        title.setText(villa.getTitle());
        address.setText(villa.getAddress());
        lat.setText(String.valueOf(villa.getLat()));
        lon.setText(String.valueOf(villa.getLon()));
        cost.setText(String.valueOf(villa.getCost()));
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
                villa_edited.setLat(Float.valueOf(lat.getText().toString()));
                villa_edited.setLon(Float.valueOf(lon.getText().toString()));
                villa_edited.setCost(Integer.valueOf(cost.getText().toString()));
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

}
