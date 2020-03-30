package com.apsent.villapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.absent.villaapp.R;
import com.bumptech.glide.Glide;

import java.io.IOException;

public class EditDialog extends DialogFragment {
    private Context context;

    private Villa villa;
    private VillaListOwner villaListOwner;

    private Bitmap coverBmp = null;
    private Uri coverUri = null;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 0;
    private static final int RESULT_OK = -1;

    private EditText title;
    private EditText address ;
    private EditText lat;
    private EditText lon;
    private EditText cost ;
    private EditText roomcount ;
    private EditText Capacity;
    private EditText area;
    private ImageView imageView_villa;

    public void setVillaListOwner(VillaListOwner villaListOwner) {
        this.villaListOwner = villaListOwner;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
       imageView_villa =(ImageView)view.findViewById(R.id.img_villa_editDlg);


        title.setText(villa.getTitle());
        address.setText(villa.getAddress());
        lat.setText(String.valueOf(villa.getLat()));
        lon.setText(String.valueOf(villa.getLon()));
        cost.setText(String.valueOf(villa.getCost()));
        roomcount.setText(String.valueOf(villa.getRoomCount()));
        Capacity.setText(String.valueOf(villa.getCapacity()));
        area.setText(String.valueOf(villa.getArea()));
        Glide.with(context).load(villa.getCover()).into(imageView_villa);

        ((Button)(view.findViewById(R.id.btn_upload_editDlg)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       showFileChooser();
                    }
                });

        ((Button)(view.findViewById(R.id.btn_camera_editDlg)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,CAMERA_REQUEST);
                    }
                });


        ((Button)view.findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Villa villa_edited=new Villa();

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
//                villa_edited.setCover(((VillaListOwner)context).UploadCoverToServer(coverUri));

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if (requestCode == PICK_IMAGE_REQUEST  && data != null && data.getData() != null) {
                try {
                    coverUri=data.getData();
                    coverBmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), coverUri);
//                    cover.setImageBitmap(coverBmp);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
