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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    private String LOCATION_NAME="";
    private RecyclerView mRecyclerView;
    private ArrayList<HomePostShowModel> postData;
    private EditText etSearchView;
    HomePostShowAdapter adapter;

    private DatabaseReference dataRef;
    private Context mContext;
    public HomePostShowFragment(Context mContext,String locationName) {
        // Required empty public constructor
        this.mContext = mContext;
        this.LOCATION_NAME = locationName;
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

        etSearchView = view.findViewById(R.id.textView19);

        //back button
        view.findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoDivisionFragment();
            }
        });

        //tv searchveiw
        view.findViewById(R.id.tv_searchview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String searchText = ""+etSearchView.getText().toString();
               retrieveDataFromServer(searchText);
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerview_home_post_show);
        mRecyclerView.setHasFixedSize(true);
        //for grid layout
        //mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomePostShowAdapter(getContext(),postData);
        mRecyclerView.setAdapter(adapter);

        retrieveDataFromServer(LOCATION_NAME);

        return view;
    }

    private void gotoDivisionFragment() {
        SelectDivisionFragment divisionFragment = new SelectDivisionFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,divisionFragment," ");
        fragmentTransaction.commit();
    }

    private void retrieveDataFromServer(final String searchText) {
        dataRef.orderByChild("homeDivision").startAt(searchText.toUpperCase()).endAt(searchText+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData.clear();
                if(dataSnapshot.getChildrenCount()==0){
                    showNoPostDialog(searchText);
                }else{
                    postData.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        HomePostShowModel model = data.getValue(HomePostShowModel.class);
                        if (model.isPostLive() && !model.isPostSold()){
                            postData.add(model);
                        }

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
        builder.setMessage("No rent post found using --> "+searchText);
        builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                retrieveDataFromServer(searchText);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                retrieveDataFromServer(LOCATION_NAME);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
