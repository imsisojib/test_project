package com.example.toletgo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        holder.webView.loadData(urlLists.get(position).getVideoLink(),"text/html","uft-8");
    }

    @Override
    public int getItemCount() {
        return urlLists.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        WebView webView;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());

        }
    }
}
