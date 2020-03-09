package com.example.toletgo.fragments;


import android.os.Bundle;

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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeVideoFragment extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<String> urlLists;

    public YoutubeVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urlLists = new ArrayList<>();
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
        urlLists.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/i80iSnvlqeQ\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
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

        recyclerView = view.findViewById(R.id.recycerview_youtube_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new YoutubeVideoAdapter(getActivity(),urlLists));

        return  view;
    }

}
