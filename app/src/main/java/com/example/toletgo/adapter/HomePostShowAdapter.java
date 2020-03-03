package com.example.toletgo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostShowModel;
import com.example.toletgo.intent.PostDetailsShowActivity;
import com.squareup.picasso.Picasso;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

public class HomePostShowAdapter extends RecyclerView.Adapter<HomePostShowAdapter.MyViewHolder> {
    private ArrayList<HomePostShowModel> dataSet;
    private Context mContext;
    public HomePostShowAdapter(Context mContext,ArrayList<HomePostShowModel> dataSet) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sampleview_home_post_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Picasso.get().load(dataSet.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
        holder.tvRentPrice.setText(dataSet.get(position).getHomePrice()+" TK/Month");
        holder.tvRentDes.setText("Flat Rent: "+dataSet.get(position).getHomeBed()+" Bed "+dataSet.get(position).getHomeBath()+" Bath "+
                dataSet.get(position).getHomeKitchen()+" Kitchen "+
                dataSet.get(position).getHomeBalcony()+" Balcony");
        holder.layoutHomePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostDetailsShowActivity.class);
                intent.putExtra("rent_price",dataSet.get(position).getHomePrice()+" TK/Month");
                intent.putExtra("rent_description","Rent Home "+dataSet.get(position).getHomeBed()
                        +" Bed "+dataSet.get(position).getHomeBath()+" Bath "+
                        dataSet.get(position).getHomeKitchen()+" Kitchen "+
                        dataSet.get(position).getHomeBalcony()+" Balcony");
                intent.putExtra("post_location",dataSet.get(position).getPostLocation());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRentFrom,tvRentDes,tvRentPrice;
        ZoomInImageView imageViewPostPhoto;
        LinearLayout layoutHomePost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutHomePost = itemView.findViewById(R.id.linearlayout_sampleview_post_home);
            tvRentDes = itemView.findViewById(R.id.home_description);
            tvRentFrom = itemView.findViewById(R.id.tv_home_rent_from);
            tvRentPrice = itemView.findViewById(R.id.tv_home_rent_price);
            imageViewPostPhoto = itemView.findViewById(R.id.zoominimageview_item_photo);

        }
    }

}
