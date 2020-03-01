package com.example.toletgo.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {

    private String LOCATION_NAME;
    private ProgressDialog pd;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(String LOCATION_NAME) {
        // Required empty public constructor
        this.LOCATION_NAME = LOCATION_NAME;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        CardView cardHome = view.findViewById(R.id.card_home);
        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                Intent intent = new Intent(getActivity(),PostAdsActivity.class);
                startActivity(intent);
                pd.dismiss();
            }
        });


        CardView cardCar,cardBus,cardMess,cardTruck,cardAmbulance,cardHotel,cardRestaurent;
        cardCar = view.findViewById(R.id.card_car);
        cardTruck = view.findViewById(R.id.card_truck);
        cardMess = view.findViewById(R.id.card_mess);
        cardAmbulance = view.findViewById(R.id.card_ambulance);
        cardHotel = view.findViewById(R.id.card_hotel);
        cardRestaurent = view.findViewById(R.id.card_restaurant);
        cardBus = view.findViewById(R.id.card_bus);


        cardAmbulance.setOnClickListener(this);
        cardHotel.setOnClickListener(this);
        cardBus.setOnClickListener(this);
        cardCar.setOnClickListener(this);
        cardMess.setOnClickListener(this);
        cardTruck.setOnClickListener(this);
        cardRestaurent.setOnClickListener(this);

        TextView locationTv= view.findViewById(R.id.textView10);
        locationTv.setText(getResources().getString(R.string.to_let_category)+" "+LOCATION_NAME);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.card_ambulance){
            showToast();
        }
        if(v.getId()==R.id.card_bus){
            showToast();
        }
        if(v.getId()==R.id.card_car){
            showToast();
        }
        if(v.getId()==R.id.card_truck){
            showToast();
        }
        if(v.getId()==R.id.card_restaurant){
            showToast();
        }
        if(v.getId()==R.id.card_hotel){
            showToast();
        }
        if(v.getId()==R.id.card_mess){
            showToast();
        }


    }

    private void showToast() {
        Toast.makeText(getActivity(), "On Developing State...", Toast.LENGTH_SHORT).show();
    }
    private void showProgress(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.show();
    }

}
