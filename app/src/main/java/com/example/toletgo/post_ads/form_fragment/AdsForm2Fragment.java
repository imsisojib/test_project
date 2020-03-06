package com.example.toletgo.post_ads.form_fragment;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toletgo.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdsForm2Fragment extends Fragment implements View.OnClickListener {

    String[] selectBed = {"Select BedRoom","1","2","3","4","5","6","7","8","9","10"};
    String[] selectBath = {"Select BathRoom","1","2","3","4","5","6","7","8","9","10"};
    String[] selectKitchen = {"Select Kitchen","1","2","3","4","5","6","7","8","9","10"};
    String[] selectBalcony = {"Select Balcony","1","2","3","4","5","6","7","8","9","10"};
    String[] selectFrontView = {"Select FrontView","1","2","3","4","5","6","7","8","9","10"};

    private TextInputEditText etSize,etHolding,etRentPrice;

    private AppCompatSpinner bedSpinner,bathSpinner,kitchenSpinner,balconySpinner,frontSpinner;
    private Bundle mBundle;
    public AdsForm2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBundle = getArguments();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads_form2,container,false);

        //Back Button
        view.findViewById(R.id.button_previous).setOnClickListener(this);
        //Next Button
        view.findViewById(R.id.bt_go_next).setOnClickListener(this);

        etSize = view.findViewById(R.id.et_size);
        etHolding = view.findViewById(R.id.et_holding_no);
        etRentPrice = view.findViewById(R.id.et_rent_price);

        bedSpinner = view.findViewById(R.id.bed_spinner);
        bathSpinner = view.findViewById(R.id.bath_spinner);
        kitchenSpinner = view.findViewById(R.id.kitchen_spinner);
        balconySpinner = view.findViewById(R.id.balcony_spinner);
        frontSpinner = view.findViewById(R.id.select_front_view);

        ArrayAdapter<String> commonAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBed);
        bedSpinner.setAdapter(commonAdapter);
        bathSpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBath));
        kitchenSpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectKitchen));
        balconySpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectBalcony));
        frontSpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,selectFrontView));

        try {

            etSize.setText(mBundle.getString("post_size"));
            etRentPrice.setText(mBundle.getString("post_price"));
            etHolding.setText(mBundle.getString("post_holding"));


            for(int i=0; i<selectBalcony.length; i++){
                if(mBundle.getString("post_balcony").equals(selectBalcony[i])){
                    balconySpinner.setSelection(i);
                    break;
                }
            }
            for(int i=0; i<selectFrontView.length; i++){
                if(mBundle.getString("post_front").equals(selectFrontView[i])){
                    frontSpinner.setSelection(i);
                    break;
                }
            }

            for(int i=0; i<selectKitchen.length; i++){
                if(mBundle.getString("post_kitchen").equals(selectKitchen[i])){
                    kitchenSpinner.setSelection(i);
                    break;
                }
            }

            for(int i=0; i<selectBed.length; i++){
                if(mBundle.getString("post_bed").equals(selectBed[i])){
                    bedSpinner.setSelection(i);
                    break;
                }
            }
            for(int i=0; i<selectBath.length; i++){
                if(mBundle.getString("post_bath").equals(selectBath[i])){
                    bathSpinner.setSelection(i);
                    break;
                }
            }


        }catch (Exception e){

        }

        return view;
    }

    private void replaceAdsForm1() {
        AdsForm1Fragment adsForm1Fragment = new AdsForm1Fragment(getActivity());
        adsForm1Fragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,adsForm1Fragment," ");
        fragmentTransaction.commit();
    }
    private void replaceAdsForm3() {
        AdsForm3Fragment adsForm3Fragment = new AdsForm3Fragment();
        adsForm3Fragment.setArguments(mBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,adsForm3Fragment," ");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button_previous){

            replaceAdsForm1();
        }
        if (v.getId()==R.id.bt_go_next){
            String price,holding,size,bed,bath,kitchen,front,balcony;
            price = etRentPrice.getText().toString();
            holding = etHolding.getText().toString();
            size = etSize.getText().toString();

            bed = selectBed[bedSpinner.getSelectedItemPosition()];
            bath = selectBath[bathSpinner.getSelectedItemPosition()];
            kitchen = selectKitchen[kitchenSpinner.getSelectedItemPosition()];
            front = selectFrontView[frontSpinner.getSelectedItemPosition()];
            balcony = selectBalcony[balconySpinner.getSelectedItemPosition()];

            if(price.isEmpty()){
                etRentPrice.setError("Please write price.");
                return;
            }
            if(holding.isEmpty()){
                etHolding.setError("Please write holding no.");
                return;
            }
            if(size.isEmpty()){
                etSize.setError("Please write size.");
                return;
            }

            if (bedSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Select Bedroom Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (bathSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Select Bathroom Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (kitchenSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Select Kitchen Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (balconySpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Select Balcony Number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (frontSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Select Front View Number", Toast.LENGTH_SHORT).show();
                return;
            }

            mBundle.putString("post_price",price);
            mBundle.putString("post_size",size);
            mBundle.putString("post_holding",holding);
            mBundle.putString("post_bed",bed);
            mBundle.putString("post_bath",bath);
            mBundle.putString("post_kitchen",kitchen);
            mBundle.putString("post_front",front);
            mBundle.putString("post_balcony",balcony);


            replaceAdsForm3();
        }
    }
}
