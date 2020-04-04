package com.apsent.villapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.absent.villapp.R;
import com.bumptech.glide.Glide;

public class GallerySliderActivity extends AppCompatActivity {
    private String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_slider);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle != null) {
            imageUrl = (String) bundle.get("imageSlider");
        }
        ImageView imageView =(ImageView)findViewById(R.id.GallerySlider_image);
        if (imageUrl!=null)
            Glide.with(this).load(imageUrl).into(imageView);
    }
}
