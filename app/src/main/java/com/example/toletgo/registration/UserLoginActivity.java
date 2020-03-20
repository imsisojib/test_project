package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {

    private TextView tvResendTittle;
    private TextInputEditText etMobile,etOtpCode;

    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken OTP_RESENDING_TOKEN;
    private String OTP_VERIFICATION_CODE;
    private String formatedPhoneNumber;

    private Button buttonVerify,buttonResendOtp;

    private ConstraintLayout constraintLayoutOtp;

    private ProgressDialog pd;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etMobile = findViewById(R.id.textInputEditText);

        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("USERS");

        buttonVerify = findViewById(R.id.button_verify_otp);
        buttonResendOtp = findViewById(R.id.button_resend_otp);
        etOtpCode = findViewById(R.id.et_otp_code);
        constraintLayoutOtp = findViewById(R.id.contraint_layout_otp);
        tvResendTittle = findViewById(R.id.textView11);
        progressBar = findViewById(R.id.progressbar_otp_confirmation);



        //login button
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile="";
                mobile = etMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length()!=11){
                    etMobile.setError("Invalid Phone Number!");
                    return;
                }
                constraintLayoutOtp.setVisibility(View.VISIBLE);

                formatedPhoneNumber = "+88"+mobile;
                sendOtpCodeToPhone(formatedPhoneNumber);

                progressBar.setVisibility(View.VISIBLE);



            }
        });



        //button verify listener
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String code = etOtpCode.getText().toString().trim();

                if(code.isEmpty()){
                    Toast.makeText(UserLoginActivity.this, "Invalid OTP code!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP_VERIFICATION_CODE,code);
                signInWithPhoneAuthCredential(credential);

                progressBar.setVisibility(View.GONE);
            }
        });

        //resend button listener
        buttonResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode(formatedPhoneNumber);
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        showProgressDialog();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userUid = mAuth.getCurrentUser().getUid();
                            dataRef.orderByChild("userUID").equalTo(userUid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getChildrenCount()==0){
                                        pd.dismiss();
                                        constraintLayoutOtp.setVisibility(View.GONE);
                                        startUserRegistrationActivity();
                                        dataRef.removeEventListener(this);
                                    }else{
                                        pd.dismiss();
                                        constraintLayoutOtp.setVisibility(View.GONE);
                                        startMainActivity();
                                        dataRef.removeEventListener(this);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    pd.dismiss();
                                    Toast.makeText(UserLoginActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                pd.dismiss();
                                Toast.makeText(UserLoginActivity.this, "The verification code entered was invalid",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                constraintLayoutOtp.setVisibility(View.VISIBLE);
            }
        });
    }

    private void sendOtpCodeToPhone(String phoneNumber) {

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        Toast.makeText(this, "Verification code is sent", Toast.LENGTH_SHORT).show();

    }


    public void resendCode(String phoneNumber) {

        setUpVerificationCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                30,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                OTP_RESENDING_TOKEN);

        Toast.makeText(this, "Verfication Code Resending...", Toast.LENGTH_SHORT).show();
    }

    private void setUpVerificationCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                OTP_VERIFICATION_CODE = s;
                OTP_RESENDING_TOKEN = forceResendingToken;

            }

        };
    }

    private void startMainActivity() {
        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
        intent.putExtra("fragment",getResources().getString(R.string.default_fragment));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startUserRegistrationActivity() {
        Intent intent = new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mobile_number",formatedPhoneNumber);
        startActivity(intent);
    }

    private void showProgressDialog(){
        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.show();
    }

}
