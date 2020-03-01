package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toletgo.R;
import com.example.toletgo.adapter.HomePostShowAdapter;
import com.example.toletgo.data_model.HomePostShowModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePostShowFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<HomePostShowModel> postData;

    private DatabaseReference dataRef;
    public HomePostShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postData = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_post_show, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_home_post_show);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    HomePostShowModel model = data.getValue(HomePostShowModel.class);
                    postData.add(model);
                }
                HomePostShowAdapter adapter = new HomePostShowAdapter(getContext(),postData);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
