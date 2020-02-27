package com.example.toletgo.post_ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.toletgo.R;

public class PostAdsActivity extends AppCompatActivity {

    private static ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ads);


        mViewPager = findViewById(R.id.viewpager);
        PostFormSlider slider = new PostFormSlider(this);
        mViewPager.setAdapter(slider);

    }

    public static void nextPage(int position){
        mViewPager.setCurrentItem(position+1);
    }
    public static void previousPage(int position){
        mViewPager.setCurrentItem(position-1);
    }


}
