package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private CircularImageView profileImageView;
    private final int PICK_IMAGE=1;
    private Uri profileUri;

    private TextInputEditText etName,etReferenceCode;
    private EditText etMobile;
    private  String MOBILE_NUMBER="";

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
        etReferenceCode = findViewById(R.id.et_reference_code);

        //profile image select
        profileImageView = findViewById(R.id.circularImageView);
        profileImageView.setOnClickListener(this);

        //create account button handler
        findViewById(R.id.button2).setOnClickListener(this);


    }

    private void pushUserDataIntoServer(final String firstName, final String referenceCode, final String mobileNumber) {
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
                        ownerData.put("userMobile",mobileNumber);
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
        intent.putExtra("fragment",getResources().getString(R.string.default_fragment));
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

        final String name,address,holding,postal,profession,district,division,referenceCode,nidNum,mobileNumber;
        name = etName.getText().toString();
        referenceCode = ""+etReferenceCode.getText().toString();
        mobileNumber = etMobile.getText().toString().trim();

        if(name.isEmpty()){
            pd.dismiss();
            etName.setError("Invalid Name");
            return;
        }

        if (profileUri==null){
            pd.dismiss();
            Toast.makeText(this, "Please Select Profile Picture", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mobileNumber.isEmpty()){
            etMobile.setError("Invalid Mobile Number");
            return;
        }

        pushUserDataIntoServer(name,referenceCode,mobileNumber);


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
            Picasso.get().load(profileUri).resize(50,50).into(profileImageView);
        }

    }
    private void showProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
