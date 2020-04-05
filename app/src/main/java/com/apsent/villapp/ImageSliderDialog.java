package com.apsent.villapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.apsent.villapp.R;
import com.bumptech.glide.Glide;

public class ImageSliderDialog extends  DialogFragment {
    private Context context;
    private String imagUrl;

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.image_slider_dialog,container);


        ImageView imageView = ((ImageView)(view.findViewById(R.id.sliderDlg_imageview)));
        if (getImagUrl()!=null)
            Glide.with(getContext()).load(getImagUrl()).into(imageView);
        return view;
    }
}
