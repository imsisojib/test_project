package com.example.toletgo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toletgo.intent.AdminPostDetailsActivity;
import com.example.toletgo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSliderAdminAdapter extends RecyclerView.Adapter<ImageSliderAdminAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<String> imageUrlList;

    public ImageSliderAdminAdapter(Context mContext, ArrayList<String> imageUrlList) {
        this.mContext = mContext;
        this.imageUrlList = imageUrlList;
    }

    @NonNull
    @Override
    public ImageSliderAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageSliderAdminAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.sampleview_image_slider,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageSliderAdminAdapter.MyViewHolder holder, final int position) {
        try{
            Picasso.get().load(imageUrlList.get(position)).resize(60,60).into(holder.imageView);
            holder.progressBar.setVisibility(View.GONE);
        }catch (Exception e){
            //failed to load photo handle
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                holder.imageBorder.setBackground(mContext.getResources().getDrawable(R.drawable.imageview_border));
                AdminPostDetailsActivity.setImageIntoImageFrame(imageUrlList.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ConstraintLayout imageBorder;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBorder = itemView.findViewById(R.id.linerarlayot_for_image_border);
            imageView = itemView.findViewById(R.id.imageview_slider_item);
            progressBar = itemView.findViewById(R.id.imageview_slider_progressbar);
        }
    }
}
