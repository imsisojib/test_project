package com.example.toletgo.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private Context mContext;
    public HomePostShowFragment(Context mContext) {
        // Required empty public constructor
        this.mContext = mContext;
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

        view.findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,homeFragment," ");
                fragmentTransaction.commit();
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerview_home_post_show);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        retrieveDataFromServer();

        return view;
    }

    private void retrieveDataFromServer() {
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData.clear();
                if(dataSnapshot.getChildrenCount()==0){
                    showNoPostDialog();
                }else{
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        HomePostShowModel model = data.getValue(HomePostShowModel.class);
                        postData.add(model);
                    }
                    HomePostShowAdapter adapter = new HomePostShowAdapter(getContext(),postData);
                    mRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showNoPostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("NO POST HERE");
        builder.setMessage("There is no post to show in this segment...");
        builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                retrieveDataFromServer();
            }
        });
        builder.create().show();
    }


}
