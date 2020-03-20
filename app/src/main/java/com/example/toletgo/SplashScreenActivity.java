package com.example.toletgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.toletgo.admin.AdminActivity;
import com.example.toletgo.R;
import com.example.toletgo.preferences.AppPreferences;
import com.example.toletgo.registration.UserLoginActivity;
import com.example.toletgo.registration.UserRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        if(AppPreferences.getSecondTimeLogin(this)){
            findViewById(R.id.button4).setVisibility(View.GONE);
            waitFor2Seconds();

        }else{
            findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkRequestPermission() && checkCallPermission()){
                        if(mAuth.getCurrentUser() !=null){
                            String uid = mAuth.getCurrentUser().getUid();
                            if (uid.equals("rMcKk1qqvZcgBqpValEUkeJtZvz2")){
                                Intent intent = new Intent(SplashScreenActivity.this, AdminActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }else startMainActivity();

                        }
                        else{
                            startLoginActivity();
                            //startUsersOptionsActvity();
                        }
                    }else {
                        Toast.makeText(SplashScreenActivity.this, "Please allow permission.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            });
        }

    }

    private boolean checkCallPermission() {
        final boolean[] permission = new boolean[1];
        Dexter.withActivity(this).withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        permission[0] = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent=new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri=Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                        permission[0] = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();

        return permission[0];
    }


    private void waitFor2Seconds() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mAuth = FirebaseAuth.getInstance();
                    if(mAuth.getCurrentUser() !=null){

                        String uid = mAuth.getCurrentUser().getUid();
                        if (uid.equals("rMcKk1qqvZcgBqpValEUkeJtZvz2")){
                            Intent intent = new Intent(SplashScreenActivity.this, AdminActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else checkRegistrationCompletedOrNot();


                    }else{
                        startLoginActivity();
                        //startUsersOptionsActvity();
                    }
                }catch (Exception e){

                }
            }
        });
        thread.start();
    }

    private void checkRegistrationCompletedOrNot() {
        final DatabaseReference dataRef;
        dataRef = FirebaseDatabase.getInstance().getReference("USERS");
        dataRef.orderByChild("userUID").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0){
                    gotoRegistrationActivity();
                    dataRef.removeEventListener(this);
                }
                else{
                    startMainActivity();
                    dataRef.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gotoRegistrationActivity() {
        AppPreferences.setSecondTimeLogin(this,true);
        Intent intent = new Intent(SplashScreenActivity.this, UserRegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private Boolean checkRequestPermission() {
        final boolean[] permission = new boolean[1];
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        permission[0] = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent=new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri=Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                        permission[0] = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();

        return permission[0];
    }

    private void startMainActivity() {
        AppPreferences.setSecondTimeLogin(this,true);
        Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
        intent.putExtra("fragment",getResources().getString(R.string.default_fragment));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startLoginActivity() {
        AppPreferences.setSecondTimeLogin(this,true);
        Intent intent = new Intent(SplashScreenActivity.this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
