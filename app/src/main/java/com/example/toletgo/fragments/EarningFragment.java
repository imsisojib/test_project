package com.example.toletgo.fragments;



import android.os.Bundle;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toletgo.dialog_fragment.CustomDialog;
import com.example.toletgo.dialog_fragment.RefCodeDialog;
import com.example.toletgo.R;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarningFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;

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
            fragmentTransaction.replace(R.id.framelayout,youtubeVideoFragment,youtubeVideoFragment.getClass().getName());
            fragmentTransaction.addToBackStack(youtubeVideoFragment.getClass().getName());
            fragmentTransaction.commit();
        }

        if(v.getId()==R.id.card_home){
            //home ads card
            //SHOWING CATEGORY DIALOGVIEW
            DialogFragment dialog = CustomDialog.newInstance();
            CustomDialog.setDialogMode("earning","null");
            dialog.show(getActivity().getSupportFragmentManager(),"earning_fragment");

        }
        if (v.getId()==R.id.card_truck){
            //coming soon
            Toast.makeText(getActivity(), "On Developing State!", Toast.LENGTH_SHORT).show();
        }
        if (v.getId()==R.id.card_mess){
            //card refer
            mAuth = FirebaseAuth.getInstance();
            DialogFragment dialog = RefCodeDialog.newInstance();
            RefCodeDialog.setRefCode(uidToReferenceCodeGenerator(mAuth.getCurrentUser().getUid()));
            dialog.show(getActivity().getSupportFragmentManager(),"refercode_fragment");

        }
        if (v.getId()==R.id.card_hotel){
            //card payment
            //Toast.makeText(getActivity(), "On Developing State!", Toast.LENGTH_SHORT).show();

            MyWalletFragment walletFragment = new MyWalletFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout,walletFragment,walletFragment.getClass().getName());
            fragmentTransaction.addToBackStack(walletFragment.getClass().getName());
            fragmentTransaction.commit();

        }
        if (v.getId()==R.id.card_bus){
            //card offer
            Toast.makeText(getActivity(), "On Developing State!", Toast.LENGTH_SHORT).show();
        }

    }

    private String uidToReferenceCodeGenerator(String postKey){
        char[] uidArray = postKey.toCharArray();
        int totalValue = 0;
        for(int position=0; position<uidArray.length; position++){
            totalValue = totalValue+uidArray[position]*(position+1);
        }
        return String.valueOf(totalValue);
    }

}
