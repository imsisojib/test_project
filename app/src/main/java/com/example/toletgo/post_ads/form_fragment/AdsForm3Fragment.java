package com.example.toletgo.post_ads.form_fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.toletgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdsForm3Fragment extends Fragment implements View.OnClickListener {


    private boolean wifi=false,lift=false,security=false,generator=false,cleaning=false;
    private Bundle mBundle;
    private ProgressDialog pd;

    public AdsForm3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBundle = getArguments();

        Switch wifiSwitch,liftSwitch,securitySwitch,generatorSwitch,cleaningSwitch;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads_form3, container, false);
        //Post Ads Button
        view.findViewById(R.id.button_post_ads).setOnClickListener(this);

        //Button Go Back
        view.findViewById(R.id.button_third_form_previous).setOnClickListener(this);

        wifiSwitch = view.findViewById(R.id.switch_wifi);
        liftSwitch = view.findViewById(R.id.switch_lift);
        securitySwitch = view.findViewById(R.id.switch_security);
        generatorSwitch = view.findViewById(R.id.switch_generator);
        cleaningSwitch = view.findViewById(R.id.switch_cleaning);

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) wifi = true;
                else wifi = false;
            }
        });
        liftSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) lift = true;
                else lift = false;
            }
        });

        cleaningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cleaning = true;
                else cleaning = false;
            }
        });

        securitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) security = true;
                else security = false;
            }
        });

        generatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) generator = true;
                else generator = false;
            }
        });


        try{
            if(mBundle.getBoolean("post_wifi")) wifiSwitch.setChecked(true);
            else wifiSwitch.setChecked(false);

            if (mBundle.getBoolean("post_security")) securitySwitch.setChecked(true);
            else securitySwitch.setChecked(false);

            if (mBundle.getBoolean("post_cleaning")) cleaningSwitch.setChecked(true);
            else cleaningSwitch.setChecked(false);

            if (mBundle.getBoolean("post_generator")) generatorSwitch.setChecked(true);
            else generatorSwitch.setChecked(false);

            if (mBundle.getBoolean("post_lift")) liftSwitch.setChecked(true);
            else liftSwitch.setChecked(false);
        }catch (Exception e){

        }

        return view;
    }

    private void replaceAdsForm2() {
        AdsForm2Fragment adsForm2Fragment = new AdsForm2Fragment();
        adsForm2Fragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,adsForm2Fragment," ");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button_third_form_previous){

            mBundle.putBoolean("post_wifi",wifi);
            mBundle.putBoolean("post_security",security);
            mBundle.putBoolean("post_cleaning",cleaning);
            mBundle.putBoolean("post_generator",generator);
            mBundle.putBoolean("post_lift",lift);

            replaceAdsForm2();
        }
        if (v.getId()==R.id.button_post_ads){

            mBundle.putBoolean("post_wifi",wifi);
            mBundle.putBoolean("post_security",security);
            mBundle.putBoolean("post_cleaning",cleaning);
            mBundle.putBoolean("post_generator",generator);
            mBundle.putBoolean("post_lift",lift);

            gotoOwnerForm();
        }
    }

    private void gotoOwnerForm() {
        OwnerDetailsFragment ownerDetailsFragment = new OwnerDetailsFragment();
        ownerDetailsFragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,ownerDetailsFragment,"owner_details_fragment");
        fragmentTransaction.commit();
    }


}
