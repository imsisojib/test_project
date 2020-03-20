package com.example.toletgo.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.preferences.AppPreferences;

public class VideoViewActivity extends AppCompatActivity {

    WebView webView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        imageView = findViewById(R.id.imageview_full_screen_close);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
            }
        },60000);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.setVideoTagAndValue(VideoViewActivity.this,
                        getIntent().getStringExtra("videoTag"),true);
                finish();
            }
        });

        webView = findViewById(R.id.webview_full_screen);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadData(getIntent().getStringExtra("video_link"),"text/html","uft-8");

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
