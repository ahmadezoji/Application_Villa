package com.apsent.villapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.apsent.villapp.R;
import com.bumptech.glide.Glide;
import java.util.List;

public class CardAdapterCustomer extends RecyclerView.Adapter<CardAdapterCustomer.MyViewHolder> {
    private Users currentuser;
    private Context context;
    private List<Villa> dataSet;


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

    public CardAdapterCustomer(List<Villa> data, Context context, Users currentuser) {
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


        title.setText(dataSet.get(listPosition).getTitle());
        cost.setText(String.valueOf(dataSet.get(listPosition).getCost()));

        if (dataSet.get(listPosition).getCover()!=null) {

            Glide.with(context)
                    .load(dataSet.get(listPosition).getCover())
                    .into(imageViewIcon);
        }

        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    imageViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    imageViewIcon.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    imageViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    imageViewIcon.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
            }
        });

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
        if (dataSet!=null)
            return dataSet.size();
        else
            return 0;
    }
}
