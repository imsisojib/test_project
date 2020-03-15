package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etName,etReferenceCode;
    private EditText etMobile;
    private  String MOBILE_NUMBER="";

    //server variable
    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;


    private ProgressDialog pd;

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

        findViewById(R.id.button2).setOnClickListener(this);


    }

    private void pushUserDataIntoServer(final String firstName, final String referenceCode, final String mobileNumber) {


        String key = dataRef.push().getKey();

        HashMap<String,Object> ownerData = new HashMap<>();

        ownerData.put("userName",firstName);
        ownerData.put("userMobile",mobileNumber);
        ownerData.put("userUID",mAuth.getCurrentUser().getUid());
        ownerData.put("userProPicUrl","null");
        ownerData.put("locationUrl",key);
        ownerData.put("yourRefCode",referenceCode);
        ownerData.put("accountCreateTime",Calendar.getInstance().getTimeInMillis());
        ownerData.put("myRefCode",uidToReferenceCodeGenerator(mAuth.getCurrentUser().getUid()));

        dataRef.child(key).setValue(ownerData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //starting main activity
                    startMainActivity();
                    pd.dismiss();
                }else {
                    pd.dismiss();
                    Toast.makeText(UserRegistrationActivity.this, "Failed! Try Again...", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UserRegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    }

    private void checkUserData() {
        showProgressDialog();

        String name,referenceCode,mobileNumber;
        name = etName.getText().toString();
        referenceCode = ""+etReferenceCode.getText().toString().trim();
        mobileNumber = etMobile.getText().toString().trim();

        if(name.isEmpty()){
            pd.dismiss();
            etName.setError("Invalid Name");
            return;
        }

        if(mobileNumber.isEmpty()){
            etMobile.setError("Invalid Mobile Number");
            return;
        }

        pushUserDataIntoServer(name,referenceCode,mobileNumber);


    }
    private void showProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();
    }

    private String uidToReferenceCodeGenerator(String postKey){
        char[] uidArray = postKey.toCharArray();
        int totalValue = 0;
        for(int position=0; position<uidArray.length; position++){
            totalValue = totalValue+uidArray[position]*(position+1);
        }
        return String.valueOf(totalValue);
    }


}
