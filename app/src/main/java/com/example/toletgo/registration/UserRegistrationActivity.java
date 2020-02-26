package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.TimeUnit;

public class UserRegistrationActivity extends AppCompatActivity {
    private TextInputEditText etName,etMobile,etLastName; //etPassword,etConfirmPassword,etReferenceCode;

    //private TextInputLayout otpLayout;
    private ConstraintLayout otpVerifyLayout;
    private Button buttonVerify,buttonOTPVerify;
    private TextInputEditText etOTPCode;


    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    private String name="",mobileNumber="",password="",lastName="",confirmPassword="",refeenceCode="";
    private String verificationid,tempPhoneNumber;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("USERS");

        etName = findViewById(R.id.et_full_name);
        etMobile = findViewById(R.id.et_mobile_no);
        etLastName = findViewById(R.id.et_last_name);


        //otpVerifyCode
        buttonVerify = findViewById(R.id.button_verify);
        buttonOTPVerify = findViewById(R.id.button_verify_otp);
        otpVerifyLayout = findViewById(R.id.contraint_layout_otp);
        etOTPCode = findViewById(R.id.et_otp_code);

        //generating OTP CODe
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = etMobile.getText().toString().trim();
                if(mobileNumber.isEmpty() && mobileNumber.length() !=11 ){
                    etMobile.setError("Invalid Mobile Number!");
                    return;
                }
                sentOTPCode("+88"+mobileNumber);
                otpVerifyLayout.setVisibility(View.VISIBLE);
            }
        });

        buttonOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInUsingCode(etOTPCode.getText().toString().trim());
            }
        });

       // etReferenceCode = findViewById(R.id.et_reference_code);
        /*otpLayout = findViewById(R.id.textInputLayout4);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);*/

        //create account button handler
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                mobileNumber = etMobile.getText().toString().trim();
                lastName = etLastName.getText().toString();
                /*password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                refeenceCode = etConfirmPassword.getText().toString().trim();*/
                if(name.isEmpty()){
                    pd.dismiss();

                    etName.setError("Please write your first name.");
                    return;
                }
                if(mobileNumber.isEmpty()){
                    pd.dismiss();

                    etMobile.setError("Invalid Mobile No.");
                    return;
                }
                if(password.isEmpty() && password.length()<6){
                    etMobile.setError("Invalid Password.");
                    pd.dismiss();

                    return;
                }if(lastName.isEmpty()){
                    pd.dismiss();
                    etMobile.setError("Please write your last name.");
                    return;
                }

                /*if(!password.equals(confirmPassword)){
                    etPassword.setError("Password doesn't match!");
                    etConfirmPassword.setError("Password doesn't match!");
                    return;
                }*/
                tempPhoneNumber = mobileNumber;
                //signInUsingMobileAndPassword(mobileNumber+"@gmail.com",password);
                showProgress();


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

    private void sentOTPCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );
        Toast.makeText(this, "Verification code is sent", Toast.LENGTH_SHORT).show();
    }

    /*private void signInUsingMobileAndPassword(final String mobileNumber, final String password) {
        mAuth.fetchSignInMethodsForEmail(mobileNumber).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(task.getResult().getSignInMethods().size()==0){
                    mAuth.createUserWithEmailAndPassword(mobileNumber,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            String locationKey = dataRef.push().getKey();
                            String userUid = String.valueOf(mAuth.getCurrentUser().getUid());
                            HashMap<String, String> dataMap = new HashMap<>();

                            dataMap.put("userFirstName",name);
                            dataMap.put("userLastName",lastName);
                            dataMap.put("mobileNumber",tempPhoneNumber);
                            dataMap.put("userPassword",password);
                            dataMap.put("userUID",userUid);
                            dataMap.put("locationKey",locationKey);

                            dataRef.child(locationKey).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();

                                        gotoUserLoginActivity();

                                    }else{
                                        pd.dismiss();

                                        Toast.makeText(UserRegistrationActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();

                                    Toast.makeText(UserRegistrationActivity.this, "Failed! Try Again...", Toast.LENGTH_SHORT).show();
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
                }else{
                    pd.dismiss();

                    Toast.makeText(UserRegistrationActivity.this, "Phone Number is already registered.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(UserRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/



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

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider
            .OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String autometicCode = phoneAuthCredential.getSmsCode();
            if(autometicCode != null){
                etOTPCode.setText(autometicCode);
                verifySignInUsingCode(autometicCode);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(UserRegistrationActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;

        }
    };

    private void verifySignInUsingCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(UserRegistrationActivity.this, "Verification Successful!", Toast.LENGTH_SHORT).show();
                    otpVerifyLayout.setVisibility(View.INVISIBLE);
                    buttonOTPVerify.setBackground(getResources().getDrawable(R.drawable.roundshape_green_bg));




                }else{
                    Toast.makeText(UserRegistrationActivity.this, "Verification Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
