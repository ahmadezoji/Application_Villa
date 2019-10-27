package com.absent.villaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class Edit_Dialog extends DialogFragment {
    private Context context;
    private Villa villa;
    private VillaListOwner villaListOwner;
    private Bitmap villaimgBMP = null;
    private static final int GALLERY_PICTURE = 1;
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

    public void setVillaListOwner(VillaListOwner villaListOwner) {
        this.villaListOwner = villaListOwner;
    }

    public void setVilla(Villa villa) {
        this.villa = villa;
    }

    public void setContext(Context context) {
        this.context = context;
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

        ((ImageView)(view.findViewById(R.id.img_villa_editDlg))).setImageBitmap(villa.getPic());

        ((Button)(view.findViewById(R.id.btn_upload_editDlg)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_PICTURE);
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

                villa_edited.setTitle(title.getText().toString());
                villa_edited.setAddress(address.getText().toString());
                villa_edited.setLat(Float.valueOf(lat.getText().toString()));
                villa_edited.setLon(Float.valueOf(lon.getText().toString()));
                villa_edited.setCost(Integer.valueOf(cost.getText().toString()));
                villa_edited.setRoomCount(Integer.valueOf(roomcount.getText().toString()));
                villa_edited.setCapacity(Integer.valueOf(Capacity.getText().toString()));
                villa_edited.setArea(Integer.valueOf(area.getText().toString()));

                /*for image */
                if (villaimgBMP != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    villaimgBMP.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    villa_edited.setPic(stream.toByteArray());
                }
                else
                    villa_edited.setPic(null);
                /*------------------------------------------------*/




                ((VillaListOwner)context).editVilla(villa_edited);

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
            if(resultCode==CAMERA_REQUEST)
            {
                villaimgBMP = (Bitmap)data.getExtras().get("data");

            }
            else if(resultCode==GALLERY_PICTURE)
            {
//                Uri selectedImage = data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                villaimgBMP= (BitmapFactory.decodeFile(picturePath));

            }
        }
    }
}
