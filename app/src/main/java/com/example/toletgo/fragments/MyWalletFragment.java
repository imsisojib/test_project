package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostShowModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWalletFragment extends Fragment {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView tvTotalPost;

    private DatabaseReference dataRef;
    private FirebaseAuth mAuth;

    public MyWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_wallet, container, false);

        dataRef.orderByChild("postOwner").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalPost = 0;
                if(dataSnapshot.getChildrenCount()==0){
                    progressBar.setVisibility(View.GONE);
                }else{
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        HomePostShowModel model = data.getValue(HomePostShowModel.class);
                        if(model.isPostLive() && model.isEarningPost()){
                            Log.d("isPostLive", String.valueOf(model.isPostLive()));
                            Log.d("isEarningPost",String.valueOf(model.isEarningPost()));
                            totalPost++;
                            Log.d("totalPost", String.valueOf(totalPost));
                        }
                    }
                    tvTotalPost.setText(String.valueOf(totalPost));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvTotalPost = view.findViewById(R.id.textView18);
        progressBar = view.findViewById(R.id.progressbar_my_wallet);

        toolbar = view.findViewById(R.id.toolbar_my_wallet);
        toolbar.setTitle("My Wallet");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,homeFragment," ");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
