package com.absent.villaapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VillaAdapterAdmin2 extends ArrayAdapter {

    private Context context;
    private List<Villa>villas;

    public VillaAdapterAdmin2(Context context,List<Villa> m_villas)
    {
        super(context,R.layout.newlist_adapter_layout,m_villas);
        this.context=context;
        this.villas=m_villas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Villa villa=villas.get(position);
        final Activity activity=(Activity)context;
        convertView=activity.getLayoutInflater()
                .inflate(R.layout.newlist_adapter_layout,null);

        Bitmap bitmap=villa.getPic();
        if (bitmap!=null) {
            ((ImageView) (convertView.findViewById(R.id.m_adapter_pic)))
                    .setImageBitmap(villa.getPic());
        }

        ((TextView)(convertView.findViewById(R.id.m_adapter_cost2)))
                .setText(String.valueOf(villa.getCost()));

        ((TextView)(convertView.findViewById(R.id.m_adapter_location)))
                .setText(String.valueOf(villa.getAddress()));

        return convertView;
    }

}
