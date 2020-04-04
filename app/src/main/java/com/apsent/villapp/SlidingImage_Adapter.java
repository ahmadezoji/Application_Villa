package com.apsent.villapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.absent.villapp.R;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Bitmap> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;
    private Button DelBtn;


    public SlidingImage_Adapter(Context context, ArrayList<Bitmap> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        DelBtn = (Button)imageLayout.findViewById(R.id.slider_btnDel);


        imageView.setImageBitmap(imageModelArrayList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DelBtn.setVisibility(View.INVISIBLE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(DelBtn.getVisibility() == View.INVISIBLE)
                    DelBtn.setVisibility(View.VISIBLE);
                else
                    DelBtn.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageDrawable(context.getDrawable(R.drawable.add_selector));
                DelBtn.setVisibility(View.INVISIBLE);
            }
        });
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
