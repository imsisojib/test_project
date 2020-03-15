package com.example.toletgo.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    //private String LOCATION_NAME;
    private ProgressDialog pd;
    private Bundle bundle;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(String LOCATION_NAME) {
        // Required empty public constructor
       // this.LOCATION_NAME = LOCATION_NAME;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

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
                if(bundle.getBoolean("activity")){

                    Intent intent = new Intent(getActivity(), PostAdsActivity.class);
                    intent.putExtra("userMode",bundle.getString("userMode"));
                    startActivity(intent);

                }
                else{
                    gotoDivisionSelectionFragment();
                }
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
        locationTv.setText(getResources().getString(R.string.to_let_category));

        return view;
    }

    private void gotoDivisionSelectionFragment() {
        SelectDivisionFragment divisionFragment = new SelectDivisionFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,divisionFragment,divisionFragment.getClass().getName());
        fragmentTransaction.addToBackStack(divisionFragment.getClass().getName());
        fragmentTransaction.commit();
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
