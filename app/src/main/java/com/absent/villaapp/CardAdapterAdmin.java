package com.absent.villaapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapterAdmin extends RecyclerView.Adapter<CardAdapterAdmin.MyViewHolder> {

    private ArrayList<Villa> dataSet;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView cost;
        ImageView imageViewIcon;
        Button Btn_Edit;
        Button Btn_Del;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.textViewName_admin);
            this.cost = (TextView) itemView.findViewById(R.id.textViewVersion_admin);
            this.imageViewIcon = (ImageView)itemView.findViewById(R.id.imageView_admin);
            this.Btn_Edit = (Button) itemView.findViewById(R.id.m_btn_cardEdit);
            this.Btn_Del = (Button) itemView.findViewById(R.id.m_btn_cardDel);

        }
    }

    public CardAdapterAdmin(ArrayList<Villa> data ,Context context) {

        this.dataSet = data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_admin, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView title = holder.title;
        TextView cost = holder.cost;
        ImageView imageViewIcon = holder.imageViewIcon;
        Button btn_Edit = holder.Btn_Edit;
        Button btn_Del = holder.Btn_Del;

        title.setText(dataSet.get(listPosition).getAddress());
        cost.setText(String.valueOf(dataSet.get(listPosition).getCost()));
        if (dataSet.get(listPosition).getPic_byte().length!=0) {
            imageViewIcon.setVisibility(View.VISIBLE);
            imageViewIcon.setImageBitmap(dataSet.get(listPosition).getPic());

        }

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Dialog dialog=new Edit_Dialog();
                dialog.setContext(context);
                dialog.setVilla(dataSet.get(listPosition));
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                dialog.setVillaListOwner((VillaListOwner) context);
                FragmentManager fm =((Activity) context).getFragmentManager();
                dialog.show(fm,"");
            }
        });
        btn_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int i=dataSet.get(listPosition).getVillaId();
//                ((VillaListOwner)context).deleteVilla(i);
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.setContext(context);
                deleteDialog.setVilla(dataSet.get(listPosition));
                deleteDialog.setVillaListOwner((VillaListOwner)context);
                FragmentManager fm =((Activity) context).getFragmentManager();
                deleteDialog.show(fm,"");

            }
        });



    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
