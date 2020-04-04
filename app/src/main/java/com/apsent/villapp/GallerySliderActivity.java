package com.apsent.villapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.absent.villapp.R;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.List;

public class GallerySliderActivity extends AppCompatActivity {
    private Gallery gallery;
    private static ViewPager mPager;
    private List<String> imStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_slider);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle != null) {
            gallery = (Gallery) bundle.get("galleryForSlider");
        }
        if (gallery!=null) {
            imStrings = gallery.getImages();

            if (imStrings!=null) {
                mPager = (ViewPager) findViewById(R.id.gallerySlider_pager);
                mPager.setAdapter(new SlidingImage_Adapter(GallerySliderActivity.this, imStrings));

                CirclePageIndicator indicator = (CirclePageIndicator)
                        findViewById(R.id.gallerySlider_indicator);

                indicator.setViewPager(mPager);

                final float density = getResources().getDisplayMetrics().density;

                indicator.setRadius(5 * density);
            }
        }
    }
}
