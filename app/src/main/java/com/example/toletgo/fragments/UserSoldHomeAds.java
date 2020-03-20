package com.example.toletgo.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toletgo.data_model.HomePostShowModel;
import com.example.toletgo.R;
import com.example.toletgo.adapter.HomePostShowAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSoldHomeAds extends Fragment {


    private RecyclerView recyclerView;
    private TextView tvSearchButton;

    private ArrayList<HomePostShowModel> postData;
    private ArrayList<HomePostShowModel> copyPostData;
    HomePostShowAdapter adapter;

    private DatabaseReference dataRef;
    private FirebaseAuth mAuth;
    private Context mContext;

    public UserSoldHomeAds(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postData = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_sold_home_ads, container, false);


        recyclerView = view.findViewById(R.id.recyclerview_home_post_show);
        recyclerView.setHasFixedSize(true);
        //for grid layout
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomePostShowAdapter(getContext(),postData);
        recyclerView.setAdapter(adapter);

        retrieveAllDataFromServer();

        return view;
    }



    private void retrieveAllDataFromServer() {
        dataRef.orderByChild("postOwner").equalTo(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount()==0){
                            postData.clear();
                            showNoPostDialog("");
                            adapter.notifyDataSetChanged();
                        }else{
                            postData.clear();
                            for (DataSnapshot data: dataSnapshot.getChildren()){
                                HomePostShowModel model = data.getValue(HomePostShowModel.class);
                                if (!model.isPostLive() && model.isPostSold()){
                                    postData.add(model);
                                }
                                copyPostData = new ArrayList<>(postData);
                            }
                        }

                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void showNoPostDialog(final String searchText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("NO POST FOUND!!!");
        //builder.setMessage("No rent post found!"+searchText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //retrieveAllDataFromServer();
            }
        });

        builder.create().show();
    }

}
