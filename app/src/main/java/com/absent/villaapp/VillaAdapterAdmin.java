package com.absent.villaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VillaAdapterAdmin extends ArrayAdapter {
    Context m_context;
    List<Villa> m_villas;

    public VillaAdapterAdmin(Context context,List<Villa>villas)
    {
        super(context,R.layout.activity_villa_adapter_admin,villas);
        m_villas=villas;
        m_context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Villa villa=m_villas.get(position);
        final Activity activity=(Activity)m_context;
        convertView=activity.getLayoutInflater()
                .inflate(R.layout.activity_villa_adapter_admin,null);

        Bitmap bitmap=villa.getPic();
        if (bitmap!=null) {
            ((ImageView) (convertView.findViewById(R.id.m_adapter_img)))
                    .setImageBitmap(villa.getPic());
        }

        ((TextView)(convertView.findViewById(R.id.m_adapter_Cost)))
                .setText(String.valueOf(villa.getCost()));

        ((TextView)(convertView.findViewById(R.id.m_adapter_RoomCnt)))
                .setText(String.valueOf(villa.getRoomCount()));

        ((TextView)(convertView.findViewById(R.id.m_adapter_Area)))
                .setText(String.valueOf(villa.getArea()));

        ((Button)(convertView.findViewById(R.id.m_EditBtn)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Edit_Dialog dialog=new Edit_Dialog();
                        dialog.setContext(m_context);
                        dialog.setVilla(villa);
                        dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                        dialog.setVillaListOwner((VillaListOwner) activity);
                        FragmentManager fm =((Activity) m_context).getFragmentManager();
                        dialog.show(fm,"");
                    }
                });

        ((Button)(convertView.findViewById(R.id.m_DeleteBtn)))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteDialog deleteDialog = new DeleteDialog();
                        deleteDialog.setContext(m_context);
                        deleteDialog.setVilla(villa);
                        deleteDialog.setVillaListOwner((VillaListOwner) activity);
                        FragmentManager fm =((Activity) m_context).getFragmentManager();
                        deleteDialog.show(fm,"");
                    }
                });


        return convertView;
    }
}
