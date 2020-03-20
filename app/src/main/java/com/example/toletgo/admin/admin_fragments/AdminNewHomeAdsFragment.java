package com.example.toletgo.admin.admin_fragments;


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
import android.widget.EditText;
import android.widget.TextView;

import com.example.toletgo.adapter.AdminNewHomeAdsAdapter;
import com.example.toletgo.R;
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
public class AdminNewHomeAdsFragment extends Fragment {

    private EditText etSearchText;
    private TextView tvSearchView;
    private RecyclerView recyclerView;

    //data retrieve
    private ArrayList<HomePostShowModel> postData;
    private ArrayList<HomePostShowModel> searchData;
    private ArrayList<HomePostShowModel> tempPostData;
    private DatabaseReference dataRef;

    //adapter
    private AdminNewHomeAdsAdapter adapter;
    private Context context;

    public AdminNewHomeAdsFragment(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postData = new ArrayList<>();
        searchData = new ArrayList<>();
        tempPostData = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");

        retrieveDataFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_new_home_ads, container, false);
        etSearchText = view.findViewById(R.id.textView19);
        tvSearchView = view.findViewById(R.id.tv_searchview);

        //search button
        tvSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = ""+etSearchText.getText().toString();
                getDataFromServer(searchText);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerview_admin_live_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AdminNewHomeAdsAdapter(context,postData);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void retrieveDataFromServer() {
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData.clear();
                if(dataSnapshot.getChildrenCount()==0){
                    showNoPostDialog("text");
                }else{
                    postData.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        HomePostShowModel model = data.getValue(HomePostShowModel.class);
                        if (!model.isPostLive() && !model.isPostSold()){
                            postData.add(model);
                        }
                    }

                    if (postData.size()==0) showNoPostDialog("text");
                    postData.clear();
                    postData.addAll(searchData);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataFromServer(String toString) {
        dataRef.orderByChild("postID").startAt(toString.toUpperCase()).endAt(toString.toLowerCase()+"\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        searchData.clear();
                        if(dataSnapshot.getChildrenCount()==0){
                            showNoPostDialog("text");
                        }else{
                            searchData.clear();
                            for (DataSnapshot data: dataSnapshot.getChildren()){
                                HomePostShowModel model = data.getValue(HomePostShowModel.class);
                                if (model.isPostLive() && !model.isPostSold()){
                                    searchData.add(model);
                                }
                            }
                            if (searchData.size()==0) showNoPostDialog("text");
                            postData.clear();
                            postData.addAll(searchData);
                            adapter.notifyDataSetChanged();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void showNoPostDialog(String searchText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("NO POST FOUND!!!");
        //builder.setMessage("No rent post found!"+searchText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                postData.clear();
                postData.addAll(tempPostData);
                adapter.notifyDataSetChanged();
            }
        });

        builder.create().show();
    }

}
