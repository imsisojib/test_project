package com.example.toletgo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.toletgo.preferences.AppPreferences;
import com.example.toletgo.registration.UserLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
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

        if(AppPreferences.getSecondTimeLogin(this)){
            findViewById(R.id.button4).setVisibility(View.GONE);
            waitFor2Seconds();

        }else{
            findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkRequestPermission()){
                        mAuth = FirebaseAuth.getInstance();
                        if(mAuth.getCurrentUser() !=null){
                            startMainActivity();
                        }else{
                            startLoginActivity();
                        }
                    }else {
                        Toast.makeText(SplashScreenActivity.this, "Please allow permission.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            });
        }

    }

    private void waitFor2Seconds() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mAuth = FirebaseAuth.getInstance();
                    if(mAuth.getCurrentUser() !=null){
                        startMainActivity();
                    }else{
                        startLoginActivity();
                    }
                }catch (Exception e){

                }
            }
        });
        thread.start();
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
