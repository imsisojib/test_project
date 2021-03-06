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

import com.example.toletgo.R;
import com.example.toletgo.fragments.HomeFragment;
import com.example.toletgo.fragments.MoreFragment;
import com.example.toletgo.fragments.ProfileFragment;
import com.example.toletgo.fragments.UserLiveHomeAds;
import com.example.toletgo.fragments.UserSoldHomeAds;
import com.example.toletgo.receiver.ConnectivityReceiver;
import com.example.toletgo.registration.UserLoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ConnectivityReceiver.ConnectivityReceiverListener{
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
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        mFrameLayout = findViewById(R.id.framelayout);

        addDefaultFragment();




        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

    }


    private void addDefaultFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,homeFragment,homeFragment.getClass().getName());
        fragmentTransaction.addToBackStack(homeFragment.getClass().getName());
        fragmentTransaction.commit();
    }

    //botto navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==R.id.bottom_menu_home){
                //home fragment
                item.setChecked(true);
                if(!ConnectivityReceiver.isConnected()){
                    showSnackbar("Network is disconnected!");
                    return false;
                }
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,homeFragment,homeFragment.getClass().getName());
                fragmentTransaction.addToBackStack(homeFragment.getClass().getName());
                fragmentTransaction.commit();
            }
            if(item.getItemId()==R.id.bottom_menu_profile){
                //users fragment
                item.setChecked(true);

                drawerLayout.openDrawer(Gravity.LEFT);

            }

            if(item.getItemId()==R.id.bottom_menu_more){
                //post fragment
                item.setChecked(true);

                MoreFragment moreFragment = new MoreFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,moreFragment,moreFragment.getClass().getName());
                fragmentTransaction.addToBackStack(moreFragment.getClass().getName());
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
        if(id==R.id.my_ads_live){
            //live ads post
            //Toast.makeText(this, "Live Ads", Toast.LENGTH_SHORT).show();
            UserLiveHomeAds userLiveHomeAds = new UserLiveHomeAds(MainActivity.this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,userLiveHomeAds,userLiveHomeAds.getClass().getName());
            fragmentTransaction.addToBackStack(userLiveHomeAds.getClass().getName());
            fragmentTransaction.commit();

            drawerLayout.closeDrawer(Gravity.LEFT);

        }
        if (id==R.id.my_ads_sold){
            //sold ads post
            //Toast.makeText(this, "Sold Ads", Toast.LENGTH_SHORT).show();
            UserSoldHomeAds userSoldHomeAds = new UserSoldHomeAds(MainActivity.this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,userSoldHomeAds,userSoldHomeAds.getClass().getName());
            fragmentTransaction.addToBackStack(userSoldHomeAds.getClass().getName());
            fragmentTransaction.commit();

            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        if(id==R.id.logout){
            signOut();
            gotoLogInActivity();
        }

        if(id==R.id.user_profile){


            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,profileFragment,profileFragment.getClass().getName());
            fragmentTransaction.addToBackStack(profileFragment.getClass().getName());
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

        if (getSupportFragmentManager().getBackStackEntryCount()!=1) {
            // I'm viewing Fragment C
            getSupportFragmentManager().popBackStackImmediate();
            //getSupportFragmentManager().popBackStack();

        } else {
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

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) showSnackbar("Network is connected!");
        else showSnackbar("Net is Disconnected!");
    }

    private void showSnackbar(String message) {
        Snackbar.make(this.findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG).show();
    }
}
