package com.example.toletgo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostShowModel;
import com.example.toletgo.intent.PostDetailsShowActivity;

import java.util.ArrayList;

public class HomePostShowAdapter extends RecyclerView.Adapter<HomePostShowAdapter.MyViewHolder>{
    private ArrayList<HomePostShowModel> dataSet;
    private ArrayList<HomePostShowModel> copyDataSet;
    private Context mContext;
    public HomePostShowAdapter(Context mContext,ArrayList<HomePostShowModel> dataSet) {
        this.dataSet = dataSet;
        this.mContext = mContext;
        copyDataSet = new ArrayList<>(dataSet);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sampleview_home_post_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try{
            Glide.with(mContext).load(dataSet.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
            //Picasso.get().load(dataSet.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
            holder.progressBar.setVisibility(View.GONE);
        }catch (Exception e){
            //unable to show photo later
        }
        holder.tvRentPrice.setText(dataSet.get(position).getHomePrice()+" TK/Month");
        holder.tvRentDes.setText("Flat Rent: "+dataSet.get(position).getHomeBed()+" Bed "+dataSet.get(position).getHomeBath()+" Bath "+
                dataSet.get(position).getHomeKitchen()+" Kitchen "+
                dataSet.get(position).getHomeBalcony()+" Balcony");
        holder.cardPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PostDetailsShowActivity.class);
                intent.putExtra("rent_price",dataSet.get(position).getHomePrice()+" TK/Month");
                intent.putExtra("rent_description","Rent Home "+dataSet.get(position).getHomeBed()
                        +" Bed "+dataSet.get(position).getHomeBath()+" Bath "+
                        dataSet.get(position).getHomeKitchen()+" Kitchen "+
                        dataSet.get(position).getHomeBalcony()+" Balcony");
                intent.putExtra("post_location",dataSet.get(position).getPostLocation());
                intent.putExtra("ads_code",dataSet.get(position).getPostID());

                //opportunities
                intent.putExtra("wifi",dataSet.get(position).isHomeWifi());
                intent.putExtra("lift",dataSet.get(position).isHomeLift());
                intent.putExtra("security",dataSet.get(position).isHomeSecurity());
                intent.putExtra("clean",dataSet.get(position).isHomeCleaning());
                intent.putExtra("generator",dataSet.get(position).isHomeGenerator());

                intent.putExtra("home_floor",dataSet.get(position).getHomeFloor());
                intent.putExtra("rent_address",dataSet.get(position).getHomeArea()+", "+dataSet.get(position).getHomeDivision());
                mContext.startActivity(intent);
            }
        });
        holder.tvAddress.setText(dataSet.get(position).getHomeArea()+", "+dataSet.get(position).getHomeDivision());
        holder.tvAdsCode.setText("Ads Code: "+dataSet.get(position).getPostID());
        holder.tvRentFrom.setText(dataSet.get(position).getHomeRentMonth());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvRentFrom,tvRentDes,tvRentPrice,tvAddress,tvAdsCode;
        ImageView imageViewPostPhoto;
        CardView cardPost;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPost = itemView.findViewById(R.id.linearlayout_sampleview_post_home);
            tvRentDes = itemView.findViewById(R.id.home_description);
            tvRentFrom = itemView.findViewById(R.id.tv_home_rent_from);
            tvRentPrice = itemView.findViewById(R.id.tv_home_rent_price);
            imageViewPostPhoto = itemView.findViewById(R.id.zoominimageview_item_photo);
            progressBar = itemView.findViewById(R.id.sampleview_home_post_progressbar);
            tvAddress = itemView.findViewById(R.id.tv_sampleview_address);
            tvAdsCode = itemView.findViewById(R.id.tv_ads_code);
        }
    }

}
