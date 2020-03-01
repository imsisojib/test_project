package com.example.toletgo.post_ads.form_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerFormFragment extends Fragment implements View.OnClickListener {

    private CircularImageView profileImageView;
    private final int PICK_IMAGE=1;
    private Uri profileUri;

    private DatabaseReference dataRef;
    private FirebaseAuth mAuth;
    private StorageReference storageRef;

    private ProgressDialog pd;

    private TextInputEditText etName,etMobile,etArea,etPostalCode,etProfession,etOwnerAddress,etHolding;
    private AppCompatSpinner spinnerDivision;
    private final String[] divisionNames = {"Select Division","DHAKA","CHOTTOGRAM","RANGPUR","SYHLET","KHULNA","BARISHAL","RAJSHAHI"};
    public OwnerFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataRef = FirebaseDatabase.getInstance().getReference("HOME_OWNER");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_form, container, false);

        //profile image select
        profileImageView = view.findViewById(R.id.circularImageView);
        profileImageView.setOnClickListener(this);

        //Owner Next Button
        Button buttonNext = view.findViewById(R.id.button_owner_go_next);
        buttonNext.setOnClickListener(this);

        //TextInputEditText Values
        etName = view.findViewById(R.id.et_owner_name);
        etMobile = view.findViewById(R.id.et_owner_mobile_no);
        etArea = view.findViewById(R.id.et_owner_area);
        etHolding = view.findViewById(R.id.et_owner_holding_no);
        etPostalCode = view.findViewById(R.id.et_owner_postal_code);
        etProfession = view.findViewById(R.id.et_owner_profession);
        etOwnerAddress = view.findViewById(R.id.et_owner_address);

        //division spinner
        spinnerDivision = view.findViewById(R.id.spinner_division);
        ArrayAdapter<String> divisonAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_sampleview,
                R.id.spinner_sampleview_textview,divisionNames);
        spinnerDivision.setAdapter(divisonAdapter);


        return view;
    }

    private void gotoPostActivity() {
        Intent intent = new Intent(getActivity(), PostAdsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.circularImageView){
            startImageChooseIntent();
        }
        if(v.getId()==R.id.button_owner_go_next){

            showProgressDialog();

            final String name,mobileNo,address,holding,postal,profession,area,division;
            name = etName.getText().toString();
            mobileNo = etMobile.getText().toString();
            address = etOwnerAddress.getText().toString();
            holding = etHolding.getText().toString();
            postal = etPostalCode.getText().toString();
            profession = etProfession.getText().toString();
            area = etArea.getText().toString();
            division = divisionNames[spinnerDivision.getSelectedItemPosition()];

            if(name.isEmpty()){
                pd.dismiss();
                etName.setError("Invalid Name");
                return;
            }
            if(mobileNo.isEmpty()){
                pd.dismiss();
                etMobile.setError("Invalid Mobile No");
                return;
            }if(address.isEmpty()){
                pd.dismiss();
                etOwnerAddress.setError("Invalid Address");
                return;
            }
            if(holding.isEmpty()){
                pd.dismiss();
                etHolding.setError("Invalid Holding No");
                return;
            }
            if(postal.isEmpty()){
                pd.dismiss();
                etPostalCode.setError("Invalid Postal Code");
                return;
            }
            if(profession.isEmpty()){
                pd.dismiss();
                etPostalCode.setError("Invalid Profession");
                return;
            }
            if(area.isEmpty()){
                pd.dismiss();
                etArea.setError("Invalid Area Address");
                return;
            }
            if (profileUri==null){
                pd.dismiss();
                Toast.makeText(getActivity(), "Please Select Profile Picture", Toast.LENGTH_SHORT).show();
                return;
            }
            if (spinnerDivision.getSelectedItemPosition()==0){
                pd.dismiss();
                Toast.makeText(getActivity(), "Invalid Division Selected", Toast.LENGTH_SHORT).show();
                return;
            }

            storageRef = FirebaseStorage.getInstance().getReference().child("PROFILE_PHOTOS");
            final StorageReference imageName = storageRef.child(mAuth.getCurrentUser().getUid());
            imageName.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //download url
                            String key = dataRef.push().getKey();

                            HashMap<String,String> ownerData = new HashMap<>();

                            ownerData.put("ownerName",name);
                            ownerData.put("ownerMobile",mobileNo);
                            ownerData.put("ownerDivison",division);
                            ownerData.put("ownerArea",area);
                            ownerData.put("ownerPostalCode",postal);
                            ownerData.put("ownerProfession",profession);
                            ownerData.put("ownerAddress",address);
                            ownerData.put("ownerHolding",holding);
                            ownerData.put("userUID",mAuth.getCurrentUser().getUid());
                            ownerData.put("ownderPhotoUrl", String.valueOf(uri));
                            ownerData.put("locationUrl",key);

                            dataRef.child(key).setValue(ownerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //starting post activity
                                        gotoPostActivity();
                                        pd.dismiss();
                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), "Failed! Try Again...", Toast.LENGTH_SHORT).show();
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
                            //download url
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Image Url Problem! Try Again...", Toast.LENGTH_SHORT).show();
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


    private void startImageChooseIntent() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE){
            if (data != null) {
                profileUri = data.getData();
            }
            Picasso.get().load(profileUri).into(profileImageView);
        }

    }

    private void showProgressDialog(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.show();
    }

}
