package com.apsent.villapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.apsent.villapp.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements VillaListOwner{

    private List<String> imStrings;
    private Villa currentVilla;
    private Gallery villaGallery;
    private GridView grid;
    private VillaController villaController;
    private UploadServer uploadServer;
    private Uri currentimagUri;

    private Button save_btn;
    private Button cancle_btn;
    private static final int STORAGE_PERMISSION_CODE = 123;
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
                editVilla(currentVilla);
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
        gallery.setImages(images);
    }
    private void SaveGallery(){
        if (villaGallery.getVid()!=null)
            if(villaController.updateGallery(villaGallery)) {
                Toast.makeText(this, "گالری شما ذخیره شد", Toast.LENGTH_SHORT).show();
            }
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
                else
                    image.setImageDrawable(getResources().getDrawable(R.drawable.add_normal));

                convertView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(GalleryActivity.this,GallerySliderActivity.class);
                        intent.putExtra("galleryForSlider",villaGallery);
                        intent.putExtra("galleypos",position);
                        startActivity(intent);
//                        ImageSliderDialog dialog=new ImageSliderDialog();
//                        dialog.setContext(GalleryActivity.this);
//                        dialog.setImagUrl(imStrings.get(position));
//                        dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
//                        FragmentManager fm =(GalleryActivity.this).getFragmentManager();
//                        dialog.show(fm,"");

                        return false;
                    }
                });

                ((Button)convertView.findViewById(R.id.GalleryGrid_BtnDel))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position!=0)
                            deleteImageFromGallery(position);
                        else
                            Toast.makeText(GalleryActivity.this, "عکس کاور قابل حذف نیست", Toast.LENGTH_SHORT).show();
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
    private void deleteImageFromGallery(final Integer position)
    {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(GalleryActivity.this,R.style.myAlert);
        builder.setTitle("حذف عکس")
                .setMessage("آیا با خذف این عکس موافقید ؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        imStrings.set(position,null);
                        loadGridView();

                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        builder.show();


    }
    private void UploadImageForGallery(Integer position)
    {
        String url = UploadCoverToServer(currentimagUri);
        imStrings.set(position,url);
        loadGridView();
    }

    @Override
    public void filllist() {

    }

    @Override
    public void deleteVilla(Villa villa) {

    }

    @Override
    public void editVilla(Villa villa) {
        villa.setCover(imStrings.get(0));
        if(villaController.EditVilla(villa))
            Toast.makeText(this, "ویلای شما بروزرسانی شد", Toast.LENGTH_SHORT).show();
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
