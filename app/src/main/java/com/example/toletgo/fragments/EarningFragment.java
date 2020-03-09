package com.example.toletgo.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningFragment extends Fragment implements View.OnClickListener {


    public EarningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_earning, container, false);


        //video card
        view.findViewById(R.id.card_car).setOnClickListener(this);
        //home ads card
        view.findViewById(R.id.card_home).setOnClickListener(this);
        //card coming soon
        view.findViewById(R.id.card_truck).setOnClickListener(this);
        //card refer
        view.findViewById(R.id.card_mess).setOnClickListener(this);
        //card payment
        view.findViewById(R.id.card_hotel).setOnClickListener(this);
        //card offer
        view.findViewById(R.id.card_bus).setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.card_car){
            //video card
            YoutubeVideoFragment youtubeVideoFragment = new YoutubeVideoFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,youtubeVideoFragment," ");
            fragmentTransaction.commit();
        }

        if(v.getId()==R.id.card_home){
            //home ads card
            Intent intent = new Intent(getActivity(), PostAdsActivity.class);
            startActivity(intent);

        }
        if (v.getId()==R.id.card_truck){
            //coming soon
        }
        if (v.getId()==R.id.card_mess){
            //card refer

            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            showAlertDialog(userUID.hashCode());

        }
        if (v.getId()==R.id.card_hotel){
            //card payment
        }
        if (v.getId()==R.id.card_bus){
            //card offer
        }


    }

    private void showAlertDialog(int hashCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(String.valueOf(Math.abs(hashCode)));
        builder.setMessage("This is your reference code. Share with others and earn money.");
        builder.setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Will Develop Soon!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
