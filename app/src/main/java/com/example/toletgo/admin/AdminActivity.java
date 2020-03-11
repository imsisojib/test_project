package com.example.toletgo.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.admin.admin_fragments.AdminLiveHomeAdsFragment;
import com.example.toletgo.admin.admin_fragments.AdminNewHomeAdsFragment;
import com.example.toletgo.admin.admin_fragments.AdminSoldHomeFragment;
import com.example.toletgo.registration.UserLoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout fragmentContainer;

    Toolbar toolbar;
    //navigation view
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    private boolean doubleBackToExitPressedOnce= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(AdminActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView =  findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        toolbar = findViewById(R.id.toolbar_admin);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.icon_navigation_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        addDefaultFragment();


    }

    private void addDefaultFragment() {
        toolbar.setTitle("Live Home Ads");
        AdminLiveHomeAdsFragment activeHomeAdsFragment = new AdminLiveHomeAdsFragment(AdminActivity.this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_admin_activity,activeHomeAdsFragment," ");
        fragmentTransaction.commit();
    }

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

        if (id==R.id.live_home_ads){
            toolbar.setTitle("Live Home Ads");
            addDefaultFragment();
            drawerLayout.closeDrawer(Gravity.LEFT);
        }

        if(id==R.id.new_home_ads){
            //new home ads
            //Toast.makeText(this, "New Home Ads", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("New Home Ads");
            AdminNewHomeAdsFragment newHomeAdsFragment = new AdminNewHomeAdsFragment(AdminActivity.this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout_admin_activity,newHomeAdsFragment," ");
            fragmentTransaction.commit();

            drawerLayout.closeDrawer(Gravity.LEFT);


        }
        if(id==R.id.sold_home_ads){
            //Toast.makeText(this, "Sold Home Ads", Toast.LENGTH_SHORT).show();
            toolbar.setTitle("Sold Home Ads");
            AdminSoldHomeFragment soldHomeFragment = new AdminSoldHomeFragment(AdminActivity.this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout_admin_activity,soldHomeFragment," ");
            fragmentTransaction.commit();

            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        if(id==R.id.payment_request){
            Toast.makeText(this, "Payment Request", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.reports){
            Toast.makeText(this, "Reports", Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.youtube_videos){
            Toast.makeText(this, "Youtube Videos", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logout){
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            gotoLoginActivity();
        }
        return false;
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(AdminActivity.this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
