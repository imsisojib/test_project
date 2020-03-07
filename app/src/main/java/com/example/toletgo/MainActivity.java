package com.example.toletgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.toletgo.fragments.HomeFragment;
import com.example.toletgo.fragments.HomePostShowFragment;
import com.example.toletgo.fragments.MoreFragment;
import com.example.toletgo.fragments.ProfileFragment;
import com.example.toletgo.fragments.SettingFragment;
import com.example.toletgo.registration.UserLoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mFrameLayout;

    //navigation view
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    private boolean doubleBackToExitPressedOnce= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        mFrameLayout = findViewById(R.id.framelayout);

        if(getIntent().getStringExtra("fragment").equals(getResources().getString(R.string.default_fragment))){
            addDefaultFragment();
        }
        else if (getIntent().getStringExtra("fragment").equals(getResources().getString(R.string.home_post_show_fragment))){
            addHomePostShowFragment();
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

    }

    private void addHomePostShowFragment() {
        HomePostShowFragment postShowFragment = new HomePostShowFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,postShowFragment," ");
        fragmentTransaction.commit();
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

                drawerLayout.openDrawer(Gravity.LEFT);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add_post){
            Toast.makeText(this, "Nav Home", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logout){
            signOut();
            gotoLogInActivity();
        }
        if(id==R.id.wallet){
            Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.user_profile){
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,profileFragment," ");
            fragmentTransaction.commit();

            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        return false;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
    private void gotoLogInActivity() {
        Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Snackbar.make(this.findViewById(R.id.drawer_layout), "Do you really want to exit?", Snackbar.LENGTH_LONG)
                    .setAction("YES", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //button action here
                            finish();
                            System.exit(0);
                        }
                    }).setActionTextColor(Color.YELLOW)
                    .show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }
}
