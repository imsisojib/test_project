package com.example.toletgo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toletgo.intent.VideoViewActivity;
import com.example.toletgo.R;
import com.example.toletgo.data_model.YoutubeVideoModel;

import java.util.ArrayList;

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.VideoHolder> {

    private Context context;
    private ArrayList<YoutubeVideoModel> urlLists;

    public YoutubeVideoAdapter(Context context, ArrayList<YoutubeVideoModel> urlLists) {
        this.context = context;
        this.urlLists = urlLists;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.sampleview_youtube_video,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, final int position) {

        holder.tvTittle.setText("Video-"+String.valueOf(position+1));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoViewActivity.class);
                intent.putExtra("videoTag","video-"+String.valueOf(position+1));
                intent.putExtra("video_link",urlLists.get(position).getVideoLink());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return urlLists.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        TextView tvTittle;
        CardView cardView;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            tvTittle = itemView.findViewById(R.id.tv_video_tittle);
            cardView = itemView.findViewById(R.id.cardview_youtube_video);

        }
    }
}
