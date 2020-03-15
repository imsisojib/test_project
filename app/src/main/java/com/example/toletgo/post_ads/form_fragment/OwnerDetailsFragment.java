package com.example.toletgo.post_ads.form_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerDetailsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    private StorageReference storeRef;

    private Bundle mBundle;
    private ProgressDialog pd;

    public OwnerDetailsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_owner_details, container, false);
        final EditText etHomeName,etOwnerName,etMobile,etHolding,etRoad,etProfession,etHomeAddress,etArea;

        etHomeName = view.findViewById(R.id.et_home_name);
        etOwnerName =view.findViewById(R.id.et_owner_name);
        etMobile = view.findViewById(R.id.et_mobile_no);
        etHolding = view.findViewById(R.id.et_holding_no);
        etRoad = view.findViewById(R.id.et_road_name);
        etProfession =view.findViewById(R.id.et_owner_profession);
        etHomeAddress = view.findViewById(R.id.et_home_address);
        etArea = view.findViewById(R.id.et_owner_area);

        view.findViewById(R.id.button_upload_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homeName,ownerName,mobileNo,holdingNo,roadNo,profession,homeAddress,homeArea;
                homeName = ""+etHomeName.getText().toString();
                ownerName = ""+etOwnerName.getText().toString();
                mobileNo = ""+etMobile.getText().toString().trim();
                holdingNo = ""+etHolding.getText().toString();
                roadNo = ""+etRoad.getText().toString();
                profession = ""+etProfession.getText().toString();
                homeAddress = ""+etHomeAddress.getText().toString();
                homeArea = ""+etArea.getText().toString();

                if (homeName.length()>30){
                    etHomeName.setError("Must be under 30 characters.");
                    return;
                }
                if (ownerName.length()>=30){
                    etOwnerName.setError("Must be under 30 characters.");
                    return;
                }
                if (mobileNo.isEmpty() || mobileNo.length()!=11){
                    etMobile.setError("Invalid Mobile No.");
                    return;
                }
                if (holdingNo.length()>=30){
                    etHolding.setError("Must be under 30 characters.");
                    return;
                }
                if (roadNo.length()>=30){
                    etRoad.setError("Must be under 30 characters.");
                    return;
                }
                if (profession.length()>=30){
                    etProfession.setError("Must be under 30 characters.");
                    return;
                }
                if (homeAddress.length()>30){
                    etHomeAddress.setError("Must be under 30 characters.");
                    return;
                }
                if (homeArea.length()>30){
                    etArea.setError("Must be under 30 characters.");
                    return;
                }

                uploadImageFirst(homeName,ownerName,mobileNo,roadNo,holdingNo,profession,homeAddress,homeArea);

            }
        });

        return view;
    }

    private void uploadImageFirst(final String homeName, final String ownerName, final String mobileNo, final String roadNo,
                                  final String holdingNo, final String profession, final String homeAddress, final String homeArea) {

        showProgressDialog();

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


                                uploadPost(postLocation,downloadUrl,homeName,
                                        ownerName,mobileNo,holdingNo,roadNo,profession,homeArea,homeAddress);

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

    private void uploadPost(String postLocation, ArrayList<Uri> downloadUrl,
                            String homeName, String ownerName, String mobileNo, String holdingNo,
                            String roadNo, String profession, String homeArea, String homeAddress) {

        HashMap<String,Object> postData = new HashMap<>();

        postData.put("homeRentMonth",mBundle.getString("post_tittle"));

        postData.put("homePostalCode",mBundle.getString("post_postal_code"));
        postData.put("homeArea",mBundle.getString("post_area"));
        postData.put("homeDivision",mBundle.getString("post_division"));
        postData.put("homeFloor",mBundle.getString("post_floor"));
        postData.put("homePrice",mBundle.getString("post_price"));
        postData.put("homeHolding",mBundle.getString("post_holding"));

        postData.put("homeBed",mBundle.getString("post_bed"));
        postData.put("homeBath",mBundle.getString("post_bath"));
        postData.put("homeKitchen",mBundle.getString("post_kitchen"));
        postData.put("homeBalcony",mBundle.getString("post_balcony"));


        postData.put("homePhoto1",downloadUrl.get(0).toString());
        postData.put("homePhoto2",downloadUrl.get(1).toString());
        postData.put("homePhoto3",downloadUrl.get(2).toString());
        postData.put("homePhoto4",downloadUrl.get(3).toString());
        postData.put("homePhoto5",downloadUrl.get(4).toString());

        postData.put("homeWifi",mBundle.getBoolean("post_wifi"));
        postData.put("homeCleaning",mBundle.getBoolean("post_cleaning"));
        postData.put("homeSecurity",mBundle.getBoolean("security"));
        postData.put("homeGenerator",mBundle.getBoolean("post_generator"));
        postData.put("homeLift",mBundle.getBoolean("post_lift"));


        postData.put("postLive",false);
        postData.put("postSold",false);
        postData.put("searchAddress",mBundle.getString("post_area")+" "+mBundle.getString("post_postal_code")+
                " "+mBundle.getString("post_division")+" "+mBundle.getString("post_division").substring(0,3)+"-"+
                postKeyToUniqueIdGenerator(postLocation));
        postData.put("earningPost",mBundle.getBoolean("userMode"));

        Calendar calendar = Calendar.getInstance();

        postData.put("postTime",calendar.getTimeInMillis());
        postData.put("postOwner",mAuth.getCurrentUser().getUid());

        //owner details
        postData.put("detailsHomeName",homeName);
        postData.put("detailsOwnerName",ownerName);
        postData.put("detailsMobileNo",mobileNo);
        postData.put("detailsHoldingNo",holdingNo);
        postData.put("detailsRoadNo",roadNo);
        postData.put("detailsProfession",profession);
        postData.put("detailsHomeAddress",homeAddress);
        postData.put("detailsHomeArea",homeArea);

        //server location
        postData.put("postLocation",postLocation);
        postData.put("postID",mBundle.getString("post_division").substring(0,3)+"-"+
                postKeyToUniqueIdGenerator(postLocation));


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
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });


    }


    private void startHomePostShowFragment() {
        Toast.makeText(getActivity(), "Your post is pending for approve.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showProgressDialog(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading post...");
        pd.show();
    }

    private String postKeyToUniqueIdGenerator(String postKey){
        char[] uidArray = postKey.toCharArray();
        int totalValue = 0;
        for(int position=0; position<uidArray.length; position++){
            totalValue = totalValue+uidArray[position]*(position+1);
        }
        return String.valueOf(totalValue);
    }

}
