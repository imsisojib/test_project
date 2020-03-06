package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
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

import java.util.Calendar;
import java.util.HashMap;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private CircularImageView profileImageView;
    private final int PICK_IMAGE=1;
    private Uri profileUri;

    private TextInputEditText etName,etReferenceCode,etNID;
    private TextInputEditText etDistrict,etPostalCode,etProfession,etOwnerAddress,etHolding;
    private AppCompatSpinner spinnerDivision;

    private TextView etMobile;
    private  String MOBILE_NUMBER;

    //server variable
    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    private StorageReference storageRef;


    private ProgressDialog pd;
    private final String[] divisionNames = {"Select Division","DHAKA","CHOTTOGRAM","RANGPUR","SYHLET","KHULNA","BARISHAL","RAJSHAHI"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        MOBILE_NUMBER = getIntent().getStringExtra("mobile_number");

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("USERS");

        //initializing UI
        etName = findViewById(R.id.et_owner_name);
        etMobile = findViewById(R.id.et_mobile_no);
        etMobile.setText(MOBILE_NUMBER);
        etDistrict = findViewById(R.id.et_owner_area);
        etHolding = findViewById(R.id.et_owner_holding_no);
        etPostalCode = findViewById(R.id.et_owner_postal_code);
        etProfession = findViewById(R.id.et_owner_profession);
        etOwnerAddress = findViewById(R.id.et_owner_address);
        etReferenceCode = findViewById(R.id.et_reference_code);
        etNID = findViewById(R.id.et_nid_number);

        //profile image select
        profileImageView = findViewById(R.id.circularImageView);
        profileImageView.setOnClickListener(this);

        //division spinner
        spinnerDivision = findViewById(R.id.spinner_division);
        ArrayAdapter<String> divisonAdapter = new ArrayAdapter<String>(this,R.layout.spinner_sampleview,
                R.id.spinner_sampleview_textview,divisionNames);
        spinnerDivision.setAdapter(divisonAdapter);

        //create account button handler
        findViewById(R.id.button2).setOnClickListener(this);


    }

    private void pushUserDataIntoServer(final String firstName, final String division, final String district, final String profession, final String postalCode,
                                        final String address, final String holding, final String nid, final String referenceCode) {
        storageRef = FirebaseStorage.getInstance().getReference().child("PROFILE_PHOTOS");

        final StorageReference imageName = storageRef.child("pro_pic"+mAuth.getCurrentUser().getUid());

        imageName.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //download url
                        String key = dataRef.push().getKey();

                        HashMap<String,Object> ownerData = new HashMap<>();

                        ownerData.put("userName",firstName);
                        ownerData.put("userMobile",MOBILE_NUMBER);
                        ownerData.put("userDivision",division);
                        ownerData.put("userDistrict",district);
                        ownerData.put("userPostalCode",postalCode);
                        ownerData.put("userProfession",profession);
                        ownerData.put("userAddress",address);
                        ownerData.put("userHolding",holding);
                        ownerData.put("userNID",nid);
                        ownerData.put("userUID",mAuth.getCurrentUser().getUid());
                        ownerData.put("userProPicUrl", String.valueOf(uri));
                        ownerData.put("locationUrl",key);
                        ownerData.put("referenceCode",referenceCode);
                        ownerData.put("accountCreateTime",Calendar.getInstance().getTimeInMillis());

                        dataRef.child(key).setValue(ownerData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //starting main activity
                                    startMainActivity();
                                    pd.dismiss();
                                }else {
                                    pd.dismiss();
                                    Toast.makeText(UserRegistrationActivity.this, "Failed! Try Again...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(UserRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //download url
                        pd.dismiss();
                        Toast.makeText(UserRegistrationActivity.this, "Image Url Problem! Try Again...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UserRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startMainActivity() {
        Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        //create account button
        if(v.getId()==R.id.button2){
            checkUserData();
        }
        //circular imageview
        if(v.getId()==R.id.circularImageView){
            startImageChooseIntent();
        }
    }

    private void checkUserData() {
        showProgressDialog();

        final String name,address,holding,postal,profession,district,division,referenceCode,nidNum;
        name = etName.getText().toString();
        address = etOwnerAddress.getText().toString();
        holding = etHolding.getText().toString();
        postal = etPostalCode.getText().toString();
        profession = etProfession.getText().toString();
        district = etDistrict.getText().toString();
        division = divisionNames[spinnerDivision.getSelectedItemPosition()];
        referenceCode = ""+etReferenceCode.getText().toString();
        nidNum = etNID.getText().toString().trim();

        if(name.isEmpty()){
            pd.dismiss();
            etName.setError("Invalid Name");
            return;
        }
        if(address.isEmpty()){
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
        if(district.isEmpty()){
            pd.dismiss();
            etDistrict.setError("Invalid District Name");
            return;
        }
        if (profileUri==null){
            pd.dismiss();
            Toast.makeText(this, "Please Select Profile Picture", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinnerDivision.getSelectedItemPosition()==0){
            pd.dismiss();
            Toast.makeText(this, "Invalid Division Selected", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(nidNum.length()==10 || nidNum.length()==13 || nidNum.length()==17)){
            etNID.setError("NID number must be 13 or 17 digits!");
            return;

        }

        pushUserDataIntoServer(name,division,district,profession,postal,address,holding,nidNum,referenceCode);


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
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();
    }
}
