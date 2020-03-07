package com.example.toletgo.post_ads.form_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
import com.example.toletgo.fragments.HomePostShowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdsForm3Fragment extends Fragment implements View.OnClickListener {

    private Switch wifiSwitch;
    private Switch liftSwitch;
    private Switch securitySwitch;
    private Switch generatorSwitch;
    private Switch cleaningSwitch;

    private Button goBack;
    private Button postYourAds;

    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    private StorageReference storeRef;

    private String wifi="NO",lift="NO",security="NO",generator="NO",cleaning="NO";
    private Bundle mBundle;
    private ProgressDialog pd;

    public AdsForm3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
        storeRef = FirebaseStorage.getInstance().getReference().child("POST_HOME");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBundle = getArguments();

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
                if(isChecked) wifi = "YES";
                else wifi = "NO";
            }
        });
        liftSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) lift = "YES";
                else lift = "NO";
            }
        });

        cleaningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) cleaning = "YES";
                else cleaning = "NO";
            }
        });

        securitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) security = "YES";
                else security = "NO";
            }
        });

        generatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) generator = "YES";
                else generator = "NO";
            }
        });

        goBack = view.findViewById(R.id.button_third_form_previous);
        postYourAds = view.findViewById(R.id.button_owner_go_next);


        try{
            if(mBundle.getString("post_wifi").equals("YES")) wifiSwitch.setChecked(true);
            else wifiSwitch.setChecked(false);

            if (mBundle.getString("post_security").equals("YES")) securitySwitch.setChecked(true);
            else securitySwitch.setChecked(false);

            if (mBundle.getString("post_cleaning").equals("YES")) cleaningSwitch.setChecked(true);
            else cleaningSwitch.setChecked(false);

            if (mBundle.getString("post_generator").equals("YES")) generatorSwitch.setChecked(true);
            else generatorSwitch.setChecked(false);

            if (mBundle.getString("post_lift").equals("YES")) liftSwitch.setChecked(true);
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
        showProgressDialog();
        if (v.getId()==R.id.button_third_form_previous){

            mBundle.putString("post_wifi",wifi);
            mBundle.putString("post_security",security);
            mBundle.putString("post_cleaning",cleaning);
            mBundle.putString("post_generator",generator);
            mBundle.putString("post_lift",lift);

            replaceAdsForm2();
        }
        if (v.getId()==R.id.button_post_ads){

            final String postLocation = dataRef.push().getKey();
            final ArrayList<Uri> downloadUrl = new ArrayList<>();
            final Uri[] postPhotos = new Uri[]{Uri.parse(mBundle.getString("post_pic_1")),Uri.parse(mBundle.getString("post_pic_2")),
                    Uri.parse(mBundle.getString("post_pic_3")),Uri.parse(mBundle.getString("post_pic_4")),
                    Uri.parse(mBundle.getString("post_pic_5"))};
            for (int i=0 ; i<postPhotos.length; i++){

                final StorageReference imageName = storeRef.child(postLocation+"/"+"image-"+i);
                imageName.putFile(postPhotos[i]).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl.add(uri);
                                if(downloadUrl.size()==5){


                                    uploadPost(postLocation,downloadUrl);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    private void uploadPost(String postLocation, ArrayList<Uri> downloadUrl) {

        HashMap<String,Object> postData = new HashMap<>();

        postData.put("homeTittle",mBundle.getString("post_tittle"));
        postData.put("homePostalCode",mBundle.getString("post_postal_code"));
        postData.put("homeArea",mBundle.getString("post_area"));
        postData.put("homeDivision",mBundle.getString("post_division"));
        postData.put("homeFloor",mBundle.getString("post_floor"));
        postData.put("homePrice",mBundle.getString("post_price"));
        postData.put("homeSize",mBundle.getString("post_size"));
        postData.put("homeHolding",mBundle.getString("post_holding"));
        postData.put("homeBed",mBundle.getString("post_bed"));
        postData.put("homeBath",mBundle.getString("post_bath"));
        postData.put("homeKitchen",mBundle.getString("post_kitchen"));
        postData.put("homeFront",mBundle.getString("post_front"));
        postData.put("homeBalcony",mBundle.getString("post_balcony"));
        postData.put("postLocation",postLocation);
        postData.put("homePhoto1",downloadUrl.get(0).toString());
        postData.put("homePhoto2",downloadUrl.get(1).toString());
        postData.put("homePhoto3",downloadUrl.get(2).toString());
        postData.put("homePhoto4",downloadUrl.get(3).toString());
        postData.put("homePhoto5",downloadUrl.get(4).toString());
        postData.put("homeWifi",wifi);
        postData.put("homeCleaning",cleaning);
        postData.put("homeSecurity",security);
        postData.put("homeGenerator",generator);
        postData.put("homeLift",lift);

        Calendar calendar = Calendar.getInstance();

        postData.put("postTime",calendar.getTimeInMillis());
        postData.put("postOwner",mAuth.getCurrentUser().getUid());

        dataRef.child(postLocation).setValue(postData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    startHomePostShowFragment();
                }else{
                    pd.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
            }
        });


    }

    private void startHomePostShowFragment() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("fragment",getResources().getString(R.string.home_post_show_fragment));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showProgressDialog(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading post...");
        pd.show();
    }

}
