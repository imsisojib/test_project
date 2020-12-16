package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toletgo.R;
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
        //comment
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
            gotoCategoryFragment(true,"normal");
            //gotoPostAdsActivity();

        }
        if(v.getId()==R.id.card_mess){
            //take rent

            gotoCategoryFragment(false,"null");
            //setSelectDivisionFragment();
            //gotoHomePostFragment();

        }
        if(v.getId()==R.id.card_bus){
            //investor

            InvestorFragment investorFragment = new InvestorFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,investorFragment,investorFragment.getClass().getName());
            fragmentTransaction.addToBackStack(investorFragment.getClass().getName());
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.card_truck){
            //coming soon
            Toast.makeText(getActivity(), "On Developing State!", Toast.LENGTH_SHORT).show();
        }

        if (v.getId()==R.id.card_hotel){
            //visitor
            ShowPostFotVisitorFragment visitorFragment = new ShowPostFotVisitorFragment(getActivity());
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,visitorFragment,visitorFragment.getClass().getName());
            fragmentTransaction.addToBackStack(visitorFragment.getClass().getName());
            fragmentTransaction.commit();
        }

        if(v.getId()==R.id.card_car){
            //earning site
            EarningFragment earningFragment = new EarningFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,earningFragment,earningFragment.getClass().getName());
            fragmentTransaction.addToBackStack(earningFragment.getClass().getName());
            fragmentTransaction.commit();
        }


    }

    private void gotoCategoryFragment(boolean activity, String userMode) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("activity",activity);
        bundle.putString("userMode",userMode);

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,categoryFragment,categoryFragment.getClass().getName());
        fragmentTransaction.addToBackStack(categoryFragment.getClass().getName());
        fragmentTransaction.commit();
    }



    private void setSelectDivisionFragment() {
        SelectDivisionFragment divisionFragment = new SelectDivisionFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,divisionFragment, divisionFragment.getClass().getName());
        fragmentTransaction.addToBackStack(divisionFragment.getClass().getName());
        fragmentTransaction.commit();
    }

}
