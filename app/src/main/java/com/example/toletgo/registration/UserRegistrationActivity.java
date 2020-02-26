package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;

public class UserRegistrationActivity extends AppCompatActivity {
    private TextInputEditText etName,etLastName,etReferenceCode;
    private TextView etMobile;

    //private TextInputLayout otpLayout;
    private ConstraintLayout otpVerifyLayout;
    private Button buttonVerify,buttonOTPVerify;
    private TextInputEditText etOTPCode;

    private  String MOBILE_NUMBER;
    private String firstName,lastName,referenceCode="TEST_CODE";

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

        etName = findViewById(R.id.et_full_name);
        etMobile = findViewById(R.id.et_mobile_no);
        etLastName = findViewById(R.id.et_last_name);

        etMobile.setText(MOBILE_NUMBER);


        //otpVerifyCode
        buttonVerify = findViewById(R.id.button_verify);
        buttonOTPVerify = findViewById(R.id.button_verify_otp);
        otpVerifyLayout = findViewById(R.id.contraint_layout_otp);
        etOTPCode = findViewById(R.id.et_otp_code);

        //generating OTP CODe

        etReferenceCode = findViewById(R.id.et_reference_code);


        //create account button handler
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etName.getText().toString();
                lastName = etLastName.getText().toString();

                if(firstName.isEmpty()){
                    pd.dismiss();

                    etName.setError("Please write your first firstName.");
                    return;
                }

                if(lastName.isEmpty()){
                    pd.dismiss();
                    etMobile.setError("Please write your last firstName.");
                    return;
                }

                showProgress();
                pushUserDataIntoDatabase(firstName,lastName,MOBILE_NUMBER,referenceCode);


            }
        });

        findViewById(R.id.textView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    private void pushUserDataIntoDatabase(String firstName, String lastName, String mobile_number, String referenceCode) {
        String userUid = mAuth.getCurrentUser().getUid();
        String dataLocation = dataRef.push().getKey();
        HashMap<String,String> dataMap = new HashMap<>();
        dataMap.put("firstName",firstName);
        dataMap.put("lastName",lastName);
        dataMap.put("mobileNumber",mobile_number);
        dataMap.put("referenceCode",referenceCode);
        dataMap.put("userUID",userUid);
        dataMap.put("thisLocation",dataLocation);

        dataRef.child(dataLocation).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startMainActivity();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UserRegistrationActivity.this, "Account creation failed! Try again...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startMainActivity() {
        Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void gotoUserLoginActivity() {
        Intent intent = new Intent(UserRegistrationActivity.this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showProgress(){
        pd = new ProgressDialog(UserRegistrationActivity.this);
        pd.setMessage("Please wait...");
        pd.show();
    }



}
