package com.example.toletgo.user_options;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.toletgo.R;
import com.example.toletgo.user_options.users_options_fragment.UsersOptionFragment;

public class UserOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        addDefaultFragment();

    }

    private void addDefaultFragment() {
        UsersOptionFragment usersOptionFragment = new UsersOptionFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_users_option,usersOptionFragment," ");
        fragmentTransaction.commit();
    }
}
