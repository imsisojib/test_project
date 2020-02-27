package com.example.toletgo.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toletgo.R;
import com.example.toletgo.post_ads.PostAdsActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private String LOCATION_NAME;

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
                Intent intent = new Intent(getActivity(),PostAdsActivity.class);
                startActivity(intent);
            }
        });
        TextView locationTv= view.findViewById(R.id.textView10);
        locationTv.setText(getResources().getString(R.string.to_let_category)+" "+LOCATION_NAME);

        return view;
    }

}
