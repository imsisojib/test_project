package com.example.toletgo.admin.admin_fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.adapter.AdminYoutubeVideoAdapter;
import com.example.toletgo.data_model.YoutubeVideoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminYoutubeVideosFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference dataRef;
    private ArrayList<YoutubeVideoModel> videosList;
    private AdminYoutubeVideoAdapter adapter;
    private FloatingActionButton fab;
    private AlertDialog alertDialog;

    private Context context;

    public AdminYoutubeVideosFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        videosList = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference("YOUTUBE_VIDEOS");

        //adapter
        adapter = new AdminYoutubeVideoAdapter(context,videosList);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videosList.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    YoutubeVideoModel model = data.getValue(YoutubeVideoModel.class);
                    videosList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin_youtube_videos, container, false);

        recyclerView = view.findViewById(R.id.recycerview_admin_youtube_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = view.findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkAddDialog();
            }
        });

        recyclerView.setAdapter(adapter);


        return view;
    }

    private void linkAddDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.sampleview_link_add,null,false);
        final EditText etLink = dialogView.findViewById(R.id.et_youtube_link);
        Button buttonCancel = dialogView.findViewById(R.id.button_sampleview_link_cancel);
        Button buttonAdd = dialogView.findViewById(R.id.button_sampleview_link_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = etLink.getText().toString();
                if(link.isEmpty()){
                    etLink.setError("No Link Added!");
                    return;
                }
                uploadLinkToServer(link);
                alertDialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }

    private void uploadLinkToServer(String link) {
        String key = dataRef.push().getKey();

        HashMap<String,String> dataMap = new HashMap<>();
        dataMap.put("location",key);
        dataMap.put("videoLink",link);

        dataRef.child(key).setValue(dataMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Upload Failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

}
