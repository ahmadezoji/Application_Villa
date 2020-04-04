package com.apsent.villapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.absent.villapp.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private List<String> imStrings;
    private Villa currentVilla;
    private Gallery villaGallery;
    private GridView grid;
    private VillaController villaController;
    private UploadServer uploadServer;
    private Uri currentimagUri;

    private Button save_btn;
    private Button cancle_btn;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        villaController = new VillaController();

        uploadServer = new UploadServer();
        uploadServer.setContext(this);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle != null) {
            currentVilla = (Villa) bundle.get("villa");
            getVillasGallery(currentVilla);

        }

        save_btn = findViewById(R.id.Gallery_SaveBtn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImagesToGallery(imStrings,villaGallery);
                SaveGallery();
            }
        });
        cancle_btn = findViewById(R.id.Gallery_CancleBtn);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setImagesToGallery(List<String> images,Gallery gallery)
    {
//        gallery.setImg1(images.get(0));
//        gallery.setImg2(images.get(1));
//        gallery.setImg3(images.get(2));
//        gallery.setImg4(images.get(3));
//        gallery.setImg5(images.get(4));
//        gallery.setImg6(images.get(5));
//        gallery.setImg7(images.get(6));
//        gallery.setImg8(images.get(7));
//        gallery.setImg9(images.get(8));
//        gallery.setImg10(images.get(9));
        gallery.setImages(images);
    }
    private void SaveGallery(){
        if (villaGallery.getVid()!=null)
            if(villaController.updateGallery(villaGallery))
                Toast.makeText(this, "گالری شما ذخیره شد", Toast.LENGTH_SHORT).show();
    }
   private void getVillasGallery(Villa villa)
   {
       try {
           villaGallery = villaController.getGallery(villa.getVillaId());
           if (villaGallery!=null) {
               imStrings = villaGallery.getImages();
               loadGridView();
           }
       }
       catch (Exception e)
       {
           Toast.makeText(this, "عکسی وجود ندارد", Toast.LENGTH_SHORT).show();
       }

   }

    private void fillListString(){
        imStrings = new ArrayList<>();


        if (villaGallery.getImg1() != null)
            imStrings.add(villaGallery.getImg1());
        if(villaGallery.getImg2()!=null)
            imStrings.add(villaGallery.getImg2());
        if(villaGallery.getImg3()!=null)
            imStrings.add(villaGallery.getImg3());
        if(villaGallery.getImg4()!=null)
            imStrings.add(villaGallery.getImg4());
        if(villaGallery.getImg5()!=null)
            imStrings.add(villaGallery.getImg5());
        if(villaGallery.getImg6()!=null)
            imStrings.add(villaGallery.getImg6());
        if(villaGallery.getImg7()!=null)
            imStrings.add(villaGallery.getImg7());
        if(villaGallery.getImg8()!=null)
            imStrings.add(villaGallery.getImg8());
        if(villaGallery.getImg9()!=null)
            imStrings.add(villaGallery.getImg9());
        if(villaGallery.getImg10()!=null)
            imStrings.add(villaGallery.getImg10());

    }
    private void loadGridView()
    {
        grid = (GridView) findViewById(R.id.Gallery_ImagesGridView);
        //fillListString();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item,
                imStrings)
        {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = getLayoutInflater().inflate(R.layout.grid_item, null);
                }

                ImageView image = (ImageView) convertView.findViewById(R.id.application_icon);
                if (imStrings.get(position)!=null && imStrings.get(position) != "")
                     Glide.with(GalleryActivity.this).load(imStrings.get(position)).into(image);

                ((Button)convertView.findViewById(R.id.GalleryGrid_BtnDel))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteImageFromGallery(position);
                    }
                });
                ((Button)convertView.findViewById(R.id.GalleryGrid_BtnEdit))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showFileChooser(position);
                            }
                        });

                return convertView;
            }
        };
        grid.setAdapter(adapter);
    }
    private void deleteImageFromGallery(Integer position)
    {
        imStrings.set(position,null);
        loadGridView();
    }
    private void UploadImageForGallery(Integer position)
    {
        String url = UploadCoverToServer(currentimagUri);
        imStrings.set(position,url);
        loadGridView();
    }
    private String UploadCoverToServer(Uri uri)
    {
        if(uri!=null) {
            return uploadServer.uploadImage(uri);
        }
        return null;
    }
    private void showFileChooser(Integer position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PackageInfo.REQUESTED_PERMISSION_GRANTED);
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), position);
    }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode >= 0 && requestCode < 10 && data != null && data.getData() != null) {
                currentimagUri = data.getData();
                UploadImageForGallery(requestCode);
            }
        }
    }
}
