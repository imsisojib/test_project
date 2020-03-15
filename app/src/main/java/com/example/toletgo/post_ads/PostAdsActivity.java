package com.example.toletgo.post_ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.form_fragment.AdsForm1Fragment;

public class PostAdsActivity extends AppCompatActivity {

    boolean userMode = false;

    private static FrameLayout frameLayoutPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ads);

        if(getIntent().getStringExtra("userMode").equals("earning")){
            userMode = true;
        }

        frameLayoutPost = findViewById(R.id.framelayout_post_ads);
        addDefaultFragment();


    }

    private void addDefaultFragment() {
        AdsForm1Fragment adsForm1Fragment = new AdsForm1Fragment(PostAdsActivity.this,userMode);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,adsForm1Fragment," ");
        fragmentTransaction.commit();
    }


}
