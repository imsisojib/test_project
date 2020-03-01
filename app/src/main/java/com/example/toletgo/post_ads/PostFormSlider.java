package com.example.toletgo.post_ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.viewpager.widget.PagerAdapter;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PostFormSlider extends PagerAdapter{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private static final int PICK_IMAGE=1;
    private Uri imageUri;
    private ArrayList<Uri> imageList =new ArrayList<Uri>();
    private View mView;
    private final String PACKAGE_NAME = "com.example.toletgo";

    public PostFormSlider(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //must be cast to ScrollView
        return view == (ScrollView) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(position==0){
            mView = mLayoutInflater.inflate(R.layout.home_rent_post_owner_form,container,false);




            container.addView(mView);
        }
        if(position==1){
            mView = mLayoutInflater.inflate(R.layout.home_rent_post_first_form,container,false);

            AppCompatSpinner spinnerDivision = mView.findViewById(R.id.spinner_division);

            AppCompatSpinner spinnerFloor = mView.findViewById(R.id.spinner_select_floor);


            String[] divisionNames = {"Select Division","DHAKA","CHOTTOGRAM","RANGPUR","SYHLET","KHULNA","BARISHAL","RAJSHAHI"};
            ArrayAdapter<String> divisonAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,divisionNames);
            spinnerDivision.setAdapter(divisonAdapter);

            String[] floorsList = {"Select Floor","1","2","3","4","5","6","7","8","9","10"};
            ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,floorsList);
            spinnerFloor.setAdapter(floorAdapter);

            //Button Choose Images
            mView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);

                }
            });

            //Button Next
            mView.findViewById(R.id.button_go_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //PostAdsActivity.nextPage(position);
                }
            });

            container.addView(mView);
        }
        if(position==2){
            mView = mLayoutInflater.inflate(R.layout.home_rent_post_second_form,container,false);

            AppCompatSpinner spinnerBed=mView.findViewById(R.id.bed_spinner);
            AppCompatSpinner spinnerBath=mView.findViewById(R.id.bath_spinner);
            AppCompatSpinner spinnerKitchen=mView.findViewById(R.id.kitchen_spinner);
            AppCompatSpinner spinnerBalcony=mView.findViewById(R.id.balcony_spinner);
            AppCompatSpinner spinnerFrontView=mView.findViewById(R.id.select_front_view);

            String[] selectBed = {"Select BedRoom","1","2","3","4","5","6","7","8","9","10"};
            String[] selectBath = {"Select BathRoom","1","2","3","4","5","6","7","8","9","10"};
            String[] selectKitchen = {"Select Kitchen","1","2","3","4","5","6","7","8","9","10"};
            String[] selectBalcony = {"Select Balcony","1","2","3","4","5","6","7","8","9","10"};
            String[] selectFrontView = {"Select FrontView","1","2","3","4","5","6","7","8","9","10"};

            ArrayAdapter<String> commonAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBed);
            spinnerBed.setAdapter(commonAdapter);
            spinnerBath.setAdapter(new ArrayAdapter<>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBath));
            spinnerKitchen.setAdapter(new ArrayAdapter<>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectKitchen));
            spinnerBalcony.setAdapter(new ArrayAdapter<>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBalcony));
            spinnerFrontView.setAdapter(new ArrayAdapter<>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectFrontView));

            //Back Button
            mView.findViewById(R.id.button_previous).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ostAdsActivity.previousPage(position);
                }
            });
            //Next Button
            mView.findViewById(R.id.bt_go_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //PostAdsActivity.nextPage(position);
                }
            });

            container.addView(mView);
        }
        if(position==3){
            mView = mLayoutInflater.inflate(R.layout.home_rent_post_third_form,container,false);

            //Post Ads Button
            mView.findViewById(R.id.button_post_ads).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "On Developping State", Toast.LENGTH_SHORT).show();
                }
            });

            //Button Go next
            mView.findViewById(R.id.button_third_form_previous).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //PostAdsActivity.previousPage(position);
                }
            });

            container.addView(mView);
        }

        return mView;
    }

    private void startMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ScrollView) object);
    }


}