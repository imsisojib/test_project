package com.example.toletgo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostShowModel;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(dataSet.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
        holder.tvRentPrice.setText(dataSet.get(position).getHomePrice()+" TK/Month");
        holder.tvRentDes.setText(dataSet.get(position).getHomeBed()+" Bed "+dataSet.get(position).getHomeBath()+" Bath "+
                dataSet.get(position).getHomeKitchen()+" Kitchen "+
                dataSet.get(position).getHomeBalcony()+" Balcony");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRentFrom,tvRentDes,tvRentPrice;
        ZoomInImageView imageViewPostPhoto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRentDes = itemView.findViewById(R.id.home_description);
            tvRentFrom = itemView.findViewById(R.id.tv_home_rent_from);
            tvRentPrice = itemView.findViewById(R.id.tv_home_rent_price);
            imageViewPostPhoto = itemView.findViewById(R.id.zoominimageview_item_photo);

        }
    }
}
