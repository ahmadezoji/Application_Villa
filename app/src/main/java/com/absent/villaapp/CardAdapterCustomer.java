package com.absent.villaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.absent.villaapp.MainActivityAdmin;
import com.absent.villaapp.R;
import com.absent.villaapp.ReservationActivity;
import com.absent.villaapp.UserLoginActivity;
import com.absent.villaapp.Users;
import com.absent.villaapp.Villa;

import java.util.ArrayList;

public class CardAdapterCustomer extends RecyclerView.Adapter<CardAdapterCustomer.MyViewHolder> {
    private Users currentuser;
    private Context context;
    private ArrayList<Villa> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView cost;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.textViewName);
            this.cost = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    public CardAdapterCustomer(ArrayList<Villa> data, Context context, Users currentuser) {
        this.currentuser=currentuser;
        this.context=context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView title = holder.title;
        TextView cost = holder.cost;
        ImageView imageViewIcon = holder.imageViewIcon;


        title.setText(dataSet.get(listPosition).getAddress());
        cost.setText(String.valueOf(dataSet.get(listPosition).getCost()));
//        if (dataSet.get(listPosition).getPic_byte().length!=0) {
//            imageViewIcon.setVisibility(View.VISIBLE);
//            imageViewIcon.setImageBitmap(dataSet.get(listPosition).getPic());
//
//        }


        ((View)holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReservationActivity.class);
                intent.putExtra("user",currentuser);
                intent.putExtra("villa",dataSet.get(listPosition));
                (context).startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
