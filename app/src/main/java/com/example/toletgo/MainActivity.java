package com.example.toletgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.toletgo.fragments.HomeFragment;
import com.example.toletgo.fragments.MoreFragment;
import com.example.toletgo.fragments.ProfileFragment;
import com.example.toletgo.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFrameLayout = findViewById(R.id.framelayout);

        addDefaultFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

    }

    private void addDefaultFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,homeFragment," ");
        fragmentTransaction.commit();
    }

    //botto navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.bottom_menu_home){
                //home fragment
                item.setChecked(true);
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,homeFragment," ");
                fragmentTransaction.commit();
            }
            if(item.getItemId()==R.id.bottom_menu_profile){
                //users fragment
                item.setChecked(true);

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,profileFragment," ");
                fragmentTransaction.commit();
            }
            if(item.getItemId()==R.id.bottom_menu_setting){
                //post fragment
                item.setChecked(true);

                SettingFragment settingFragment = new SettingFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,settingFragment," ");
                fragmentTransaction.commit();

            }

            if(item.getItemId()==R.id.bottom_menu_more){
                //post fragment
                item.setChecked(true);

                MoreFragment moreFragment = new MoreFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,moreFragment," ");
                fragmentTransaction.commit();

            }

            return false;
        }
    };

}
