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

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView tvGiveRent,tvTakeRent,tvInvestor,tvEarning,tvComingSoon;
    private String stringRentOption;
    private final String GIVERENT="GIVE_RENT",TAKERENT="TAKE_RENT",INVESTOR="INVESTOR",EARNING="EARNING",COMINGSOON="COMING_SOON";

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

        tvGiveRent = view.findViewById(R.id.tv_give_rent);
        tvTakeRent = view.findViewById(R.id.tv_take_rent);
        tvInvestor = view.findViewById(R.id.tv_investor);
        tvEarning = view.findViewById(R.id.tv_earning_site);
        tvComingSoon = view.findViewById(R.id.tv_coming_soon);

        tvTakeRent.setOnClickListener(this);
        tvEarning.setOnClickListener(this);
        tvComingSoon.setOnClickListener(this);
        tvInvestor.setOnClickListener(this);
        tvGiveRent.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.tv_give_rent){
            tvGiveRent.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = TAKERENT;

            gotoPostAdsActivity();

        }
        if(v.getId()==R.id.tv_take_rent){
            tvTakeRent.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = GIVERENT;

            //setSelectDivisionFragment();
            gotoHomePostFragment();

        }
        if(v.getId()==R.id.tv_investor){
            tvInvestor.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = INVESTOR;

            InvestorFragment investorFragment = new InvestorFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,investorFragment," ");
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.tv_coming_soon){
            tvComingSoon.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = COMINGSOON;
        }
        if(v.getId()==R.id.tv_earning_site){
            tvEarning.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = EARNING;
            EarningFragment earningFragment = new EarningFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,earningFragment," ");
            fragmentTransaction.commit();
        }


    }

    private void gotoHomePostFragment() {
        HomePostShowFragment homePostShowFragment = new HomePostShowFragment();
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
