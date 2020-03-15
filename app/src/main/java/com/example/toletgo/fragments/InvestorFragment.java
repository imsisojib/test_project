package com.example.toletgo.fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toletgo.R;
import com.example.toletgo.dialog_fragment.CustomDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvestorFragment extends Fragment implements View.OnClickListener {



    public InvestorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_investor, container, false);

        view.findViewById(R.id.card_mess).setOnClickListener(this);
        view.findViewById(R.id.card_restaurant).setOnClickListener(this);
        view.findViewById(R.id.card_bus).setOnClickListener(this);
        view.findViewById(R.id.card_car).setOnClickListener(this);
        view.findViewById(R.id.card_ambulance).setOnClickListener(this);
        view.findViewById(R.id.card_hotel).setOnClickListener(this);
        view.findViewById(R.id.card_truck).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.card_mess) showDialog();
        if (v.getId()==R.id.card_restaurant) showDialog();
        if (v.getId()==R.id.card_bus) showDialog();
        if (v.getId()==R.id.card_car) showDialog();
        if (v.getId()==R.id.card_ambulance) showDialog();
        if (v.getId()==R.id.card_hotel) showDialog();
        if (v.getId()==R.id.card_truck) showDialog();
    }

    private void showDialog() {
        DialogFragment dialog = CustomDialog.newInstance();
        CustomDialog.setDialogMode("investor","null");
        dialog.show(getActivity().getSupportFragmentManager(),"investor_fragment");

    }

}
