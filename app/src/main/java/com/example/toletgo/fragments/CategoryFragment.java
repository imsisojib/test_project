package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toletgo.R;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        TextView locationTv= view.findViewById(R.id.textView10);
        locationTv.setText(getResources().getString(R.string.to_let_category)+" "+LOCATION_NAME);

        return view;
    }

}
