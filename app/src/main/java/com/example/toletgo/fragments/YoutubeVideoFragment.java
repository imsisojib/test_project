package com.example.toletgo.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.example.toletgo.adapter.YoutubeVideoAdapter;
import com.example.toletgo.data_model.YoutubeVideoModel;
import com.example.toletgo.R;
import com.example.toletgo.preferences.AppPreferences;
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

        showDialog();

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

        //payment nin button
        view.findViewById(R.id.constraint_layout_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allViewd = true;
                for(int i=1 ; i<7; i++){
                    if(!AppPreferences.getVideoWatchUsingVideoTag(getActivity(),"video-"+String.valueOf(i))){
                        Toast.makeText(getActivity(), "All Videos are not watched!", Toast.LENGTH_SHORT).show();
                        allViewd = false;
                        break;
                    }
                }

                if (allViewd) Toast.makeText(getActivity(), "All Viwed", Toast.LENGTH_SHORT).show();

            }
        });

        recyclerView = view.findViewById(R.id.recycerview_youtube_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        return  view;
    }

    private void showDialog() {
        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.dialogview_youtube_rules);
        myDialog.findViewById(R.id.button_youtube_channel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
