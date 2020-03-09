package com.example.toletgo.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference dataRef;
    private FirebaseAuth mAuth;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataRef = FirebaseDatabase.getInstance().getReference("HOME_OWNER");
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //give rent
        view.findViewById(R.id.card_home).setOnClickListener(this);
        //take rent
        view.findViewById(R.id.card_mess).setOnClickListener(this);
        //earning site
        view.findViewById(R.id.card_car).setOnClickListener(this);
        //visitor
        view.findViewById(R.id.card_hotel).setOnClickListener(this);
        //investor
        view.findViewById(R.id.card_bus).setOnClickListener(this);
        //coming soon
        view.findViewById(R.id.card_truck).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.card_home){
            //give rent
            gotoPostAdsActivity();

        }
        if(v.getId()==R.id.card_mess){
            //take rent
            //setSelectDivisionFragment();
            gotoHomePostFragment();

        }
        if(v.getId()==R.id.card_bus){
            //investor

            InvestorFragment investorFragment = new InvestorFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,investorFragment," ");
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.card_truck){
            //coming soon
            Toast.makeText(getActivity(), "On Developing State!", Toast.LENGTH_SHORT).show();
        }

        if (v.getId()==R.id.card_hotel){
            //visitor
            CategoryFragment categoryFragment = new CategoryFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,categoryFragment," ");
            fragmentTransaction.commit();
        }

        if(v.getId()==R.id.card_car){
            //earning site
            EarningFragment earningFragment = new EarningFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,earningFragment," ");
            fragmentTransaction.commit();
        }


    }

    private void gotoHomePostFragment() {
        HomePostShowFragment homePostShowFragment = new HomePostShowFragment(getContext());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,homePostShowFragment," ");
        fragmentTransaction.commit();
    }

    private void gotoPostAdsActivity() {
        Intent intent = new Intent(getActivity(), PostAdsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void setSelectDivisionFragment() {
        SelectDivisionFragment divisionFragment = new SelectDivisionFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,divisionFragment," ");
        fragmentTransaction.commit();
    }

}
