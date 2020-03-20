package com.example.toletgo.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toletgo.data_model.UserEarningModel;
import com.example.toletgo.R;
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
    private ProgressBar pbActivity,pbBal;
    private TextView tvTotalAds,tvTotalRef,tvTotalView,tvAdsBal,tvViewBal,tvRefBal,tvTotalBal,tvRemBal,tvPaidBal;

    private DatabaseReference dataRefUsers;
    private FirebaseAuth mAuth;

    public MyWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataRefUsers = FirebaseDatabase.getInstance().getReference("USERS");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_wallet, container, false);

        toolbar = view.findViewById(R.id.toolbar_my_wallet);
        toolbar.setTitle("My Wallet");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        pbActivity = view.findViewById(R.id.progressbar_my_wallet);
        pbBal = view.findViewById(R.id.progressbar_my_balance);

        tvTotalAds = view.findViewById(R.id.textView18);
        tvTotalRef = view.findViewById(R.id.textView24);
        tvTotalView = view.findViewById(R.id.textView26);

        tvAdsBal = view.findViewById(R.id.textView181);
        tvViewBal = view.findViewById(R.id.textView260);
        tvRefBal = view.findViewById(R.id.textView240);
        tvTotalBal = view.findViewById(R.id.textView29);
        tvPaidBal = view.findViewById(R.id.textView34);
        tvRemBal = view.findViewById(R.id.textView35);


        dataRefUsers.orderByChild("userUID").equalTo(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pbActivity.setVisibility(View.VISIBLE);
                        pbBal.setVisibility(View.VISIBLE);
                        for (DataSnapshot data: dataSnapshot.getChildren()){

                            UserEarningModel model = data.getValue(UserEarningModel.class);
                            if (model!=null)  updateUI(model.getTotalPost(),model.getTotalRefer(),
                                    model.getTotalView(),model.getTotalPaid());
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        pbBal.setVisibility(View.GONE);
                        pbActivity.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        return view;
    }

    private void updateUI(String totalPost, String totalRefer, String totalView, String totalPaid) {
        tvTotalRef.setText(totalRefer);
        tvTotalView.setText(totalView);
        tvTotalAds.setText(totalPost);

        tvPaidBal.setText(totalPaid);
        tvAdsBal.setText(String.valueOf(Integer.valueOf(totalPost)*10));
        tvRefBal.setText(String.valueOf(Integer.valueOf(totalRefer)*5));
        tvViewBal.setText(String.valueOf(Integer.valueOf(totalView)*3));

        tvTotalBal.setText(String.valueOf(Integer.valueOf(totalPost)*10+Integer.valueOf(totalRefer)*5
                +Integer.valueOf(totalView)*3));
        int remBal = Integer.valueOf(totalPost)*10+Integer.valueOf(totalRefer)*5
                +Integer.valueOf(totalView)*3-Integer.valueOf(totalPaid);
        tvRemBal.setText(String.valueOf(remBal));

        pbBal.setVisibility(View.GONE);
        pbActivity.setVisibility(View.GONE);



    }

}
