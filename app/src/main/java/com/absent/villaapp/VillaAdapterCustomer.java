package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class VillaAdapterCustomer extends ArrayAdapter {
    Context context;
    List<Villa> m_Villas;
    public VillaAdapterCustomer(Context context, List<Villa> villas)
    {
        super(context,R.layout.activity_villa_adapter_customer,villas);
        this.context=context;
        this.m_Villas=villas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Villa villa=m_Villas.get(position);
        Activity activity=(Activity)context;

        convertView=activity.getLayoutInflater()
                .inflate(R.layout.activity_villa_adapter_customer,null);

        Bitmap bitmap=villa.getPic();
        if (bitmap!=null) {
            ((ImageView) (convertView.findViewById(R.id.m_adapter_img_Customer)))
                    .setImageBitmap(villa.getPic());
        }

        ((TextView)(convertView.findViewById(R.id.m_adapter_Cost_Customer)))
                .setText(String.valueOf(villa.getCost()));

        ((TextView)(convertView.findViewById(R.id.m_adapter_RoomCnt_Customer)))
                .setText(String.valueOf(villa.getRoomCount()));

        ((TextView)(convertView.findViewById(R.id.m_adapter_Area_Customer)))
                .setText(String.valueOf(villa.getArea()));

        ((LinearLayout)(convertView.findViewById(R.id.layout_villa)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteDialog deleteDialog = new DeleteDialog();
                    }
                });




        return  convertView;
    }

    public void m_clickItem(View view)
    {

    }
}
