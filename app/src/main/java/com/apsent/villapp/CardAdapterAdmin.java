package com.apsent.villapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.absent.villaapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CardAdapterAdmin extends RecyclerView.Adapter<CardAdapterAdmin.MyViewHolder> {

    private ArrayList<Villa> dataSet;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView cost;
        public ImageView imageViewIcon;
        public Button Btn_Edit;
        public Button Btn_Del;

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

        title.setText(dataSet.get(listPosition).getTitle());
        cost.setText(String.valueOf(dataSet.get(listPosition).getCost()));


        if (dataSet.get(listPosition).getCover()!=null) {

            Glide.with(context)
                    .load(dataSet.get(listPosition).getCover())
                    .into(imageViewIcon);
        }


        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog dialog=new EditDialog();
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
