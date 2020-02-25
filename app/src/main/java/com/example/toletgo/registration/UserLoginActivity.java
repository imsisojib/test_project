package com.example.toletgo.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.toletgo.MainActivity;
import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {
    private TextInputEditText etMobile,etPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etMobile = findViewById(R.id.textInputEditText);
        etPass = findViewById(R.id.et_login_password);

        mAuth = FirebaseAuth.getInstance();

        //registration activity handler
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserRegistrationActivity();
            }
        });

        //login button
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass="",mobile="";
                pass = etPass.getText().toString().trim();
                mobile = etMobile.getText().toString().trim();

                signInWithMobileAndPassword(mobile+"@gmail.com",pass);
            }
        });

        //forget password activity
        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void signInWithMobileAndPassword(String mobile, String pass) {
        mAuth.signInWithEmailAndPassword(mobile,pass).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startUserRegistrationActivity() {
        Intent intent = new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
        startActivity(intent);
    }
}
