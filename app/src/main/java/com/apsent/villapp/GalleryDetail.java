package com.apsent.villapp;

import android.widget.ImageView;
import android.widget.TextView;

public class GalleryDetail {
    private ImageView imageView;
    private TextView imageNum;


    public GalleryDetail(ImageView imageView, TextView imageNum) {
        this.imageView = imageView;
        this.imageNum = imageNum;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getImageNum() {
        return imageNum;
    }

    public void setImageNum(TextView imageNum) {
        this.imageNum = imageNum;
    }
}
