package com.example.toletgo.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.toletgo.data_model.UserDataModel;
import com.example.toletgo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName,tvMobile,tvNid,tvAddress,tvProfession;
    private CircularImageView imageProfile;

    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    private ProgressDialog pd;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference("USERS");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showProgressDialog();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
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

        tvName = view.findViewById(R.id.tv_user_name);
        imageProfile = view.findViewById(R.id.circularImageView2);
        tvMobile = view.findViewById(R.id.tv_phone_number);
        tvNid = view.findViewById(R.id.tv_user_nid);
        tvAddress = view.findViewById(R.id.tv_user_address);
        tvProfession = view.findViewById(R.id.tv_user_profession);

        dataRef.orderByChild("userUID").equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    UserDataModel model = data.getValue(UserDataModel.class);
                    updateUI(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void updateUI(UserDataModel model) {
        try{
            Picasso.get().load(model.getUserProPicUrl()).into(imageProfile);
        }catch (Exception e){
           Picasso.get().load(R.drawable.icon_profile).into(imageProfile);
        }

        tvName.setText(model.getUserName());
        tvProfession.setText(model.getUserProfession());
        tvMobile.setText(model.getUserMobile());
        tvNid.setText(model.getUserNID());
        tvAddress.setText(model.getUserHolding()+","+model.getUserAddress()+","+"P.O:"+model.getUserPostalCode()+","+model.getUserDistrict()+","+model.getUserDivision());
        pd.dismiss();
    }

    private void showProgressDialog(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.show();
    }



}
