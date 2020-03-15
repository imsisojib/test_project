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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class ShowPostFotVisitorFragment extends Fragment {

        private RecyclerView recyclerView;
        private EditText etSearchText;
        private TextView tvSearchButton;

    private ArrayList<HomePostShowModel> postData;
    private ArrayList<HomePostShowModel> copyPostData;
    HomePostShowAdapter adapter;

    private DatabaseReference dataRef;
    private Context mContext;

    public ShowPostFotVisitorFragment(Context context) {
        // Required empty public constructor
        this.mContext = context;
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
        View view = inflater.inflate(R.layout.fragment_show_post_fot_visitor, container, false);

        etSearchText = view.findViewById(R.id.textView19);

        view.findViewById(R.id.tv_searchview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryFromEditTextSubmit(""+etSearchText.getText());
            }
        });

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

    private void queryFromEditTextSubmit(String s) {
        dataRef.orderByChild("homeDivision").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff")
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
                                if (model.isPostLive() && !model.isPostSold()){
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

    private void retrieveAllDataFromServer() {
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postData.clear();
                if(dataSnapshot.getChildrenCount()==0){
                    Toast.makeText(mContext, "No Post Found!", Toast.LENGTH_SHORT).show();
                }else{
                    postData.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        HomePostShowModel model = data.getValue(HomePostShowModel.class);
                        if (model.isPostLive() && !model.isPostSold()){
                            postData.add(model);
                        }
                        copyPostData = new ArrayList<>(postData);
                    }
                }

                adapter.notifyDataSetChanged();
                dataRef.removeEventListener(this);

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
                retrieveAllDataFromServer();
            }
        });

        builder.create().show();
    }

}
