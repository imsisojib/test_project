package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toletgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectDivisionFragment extends Fragment implements View.OnClickListener {

    private TextView tvDhaka,tvCtg,tvKhulna,tvRang,tvRaj,tvBari,tvSyl;
    private final String DHAKA="DHAKA",CTG="CHOTTOGRAM",KHULNA="KHULNA",BARISHAL="BARISHAL",
            SYL="SYLHET",RANG="RANGPUR",RAJ="RAJSHAHI";
    public SelectDivisionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_division, container, false);


        Toolbar toolbar = view.findViewById(R.id.tolbar_division_selection);
        toolbar.setTitle(getString(R.string.select_location));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment categoryFragment = new CategoryFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,categoryFragment," ");
                fragmentTransaction.commit();
            }
        });

        tvDhaka = view.findViewById(R.id.tv_dhaka);
        tvBari = view.findViewById(R.id.tv_barishal);
        tvCtg = view.findViewById(R.id.tv_chottogram);
        tvRang = view.findViewById(R.id.tv_rangpur);
        tvSyl = view.findViewById(R.id.tv_sylhet);
        tvKhulna = view.findViewById(R.id.tv_khulna);
        tvRaj = view.findViewById(R.id.tv_rajshahi);

        tvDhaka.setOnClickListener(this);
        tvCtg.setOnClickListener(this);
        tvRaj.setOnClickListener(this);
        tvRang.setOnClickListener(this);
        tvKhulna.setOnClickListener(this);
        tvSyl.setOnClickListener(this);
        tvBari.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_dhaka){
            tvDhaka.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(DHAKA);
        }
        if(v.getId()==R.id.tv_chottogram){
            tvCtg.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(CTG);
        }
        if(v.getId()==R.id.tv_khulna){
            tvKhulna.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(KHULNA);
        }
        if(v.getId()==R.id.tv_rajshahi){
            tvRaj.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(RAJ);
        }
        if(v.getId()==R.id.tv_sylhet){
            tvSyl.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(SYL);
        }
        if(v.getId()==R.id.tv_barishal){
            tvBari.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(BARISHAL);
        }
        if(v.getId()==R.id.tv_rangpur){
            tvRang.setBackground(getResources().getDrawable(R.drawable.roundshape_yellow_bg));
            homePostShowFragment(RANG);
        }
    }

    private void homePostShowFragment(String locationName) {
       HomePostShowFragment homePostShowFragment = new HomePostShowFragment(getContext(),locationName);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,homePostShowFragment,homePostShowFragment.getClass().getName());
        fragmentTransaction.addToBackStack(homePostShowFragment.getClass().getName());
        fragmentTransaction.commit();
    }
}
