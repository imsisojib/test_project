package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView tvGiveRent,tvTakeRent,tvInvestor,tvEarning,tvComingSoon;
    private String stringRentOption;
    private final String GIVERENT="GIVE_RENT",TAKERENT="TAKE_RENT",INVESTOR="INVESTOR",EARNING="EARNING",COMINGSOON="COMING_SOON";
    public HomeFragment() {

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


        if(v.getId()==R.id.tv_take_rent){
            tvTakeRent.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = TAKERENT;

            setSelectDivisionFragment();

        }
        if(v.getId()==R.id.tv_give_rent){
            tvGiveRent.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = GIVERENT;

            setSelectDivisionFragment();

        }
        if(v.getId()==R.id.tv_investor){
            tvInvestor.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = INVESTOR;

            setSelectDivisionFragment();
        }
        if(v.getId()==R.id.tv_coming_soon){
            tvComingSoon.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = COMINGSOON;
        }
        if(v.getId()==R.id.tv_earning_site){
            tvEarning.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            stringRentOption = EARNING;
        }


    }

    private void setSelectDivisionFragment() {
        SelectDivisionFragment divisionFragment = new SelectDivisionFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,divisionFragment," ");
        fragmentTransaction.commit();
    }

}
