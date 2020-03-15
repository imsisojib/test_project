package com.example.toletgo.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toletgo.R;
import com.example.toletgo.adapter.YoutubeVideoAdapter;
import com.example.toletgo.data_model.YoutubeVideoModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeVideoFragment extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<YoutubeVideoModel> urlLists;
    DatabaseReference dataRef;

    YoutubeVideoAdapter adapter;

    public YoutubeVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urlLists = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference("YOUTUBE_VIDEOS");

        adapter = new YoutubeVideoAdapter(getActivity(),urlLists);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube_video, container, false);

        toolbar = view.findViewById(R.id.toolbar_youtube_fragment);
        toolbar.setTitle("Youtube Videos");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EarningFragment earningFragment = new EarningFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,earningFragment," ");
                fragmentTransaction.commit();
            }
        });

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urlLists.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    YoutubeVideoModel model = data.getValue(YoutubeVideoModel.class);
                    urlLists.add(model);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = view.findViewById(R.id.recycerview_youtube_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        return  view;
    }

}
