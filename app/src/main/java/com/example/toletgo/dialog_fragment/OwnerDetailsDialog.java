package com.example.toletgo.dialog_fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.toletgo.R;

public class OwnerDetailsDialog extends DialogFragment{

    static String homeName,ownerName,mobileNo,profession,roadName,holdinNo,homeAddress,homeArea;

    public static void setValues(String homeName, String ownerName,
                              String mobileNo, String profession, String roadName, String holdinNo,
                              String homeAddress, String homeArea) {
        OwnerDetailsDialog.homeName = homeName;
        OwnerDetailsDialog.ownerName = ownerName;
        OwnerDetailsDialog.mobileNo = mobileNo;
        OwnerDetailsDialog.profession = profession;
        OwnerDetailsDialog.roadName = roadName;
        OwnerDetailsDialog.holdinNo = holdinNo;
        OwnerDetailsDialog.homeAddress = homeAddress;
        OwnerDetailsDialog.homeArea = homeArea;
    }

    public static OwnerDetailsDialog newInstance(){
        return new OwnerDetailsDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogview_owner_details,container,false);

        TextView tvHomeName,tvOwnerName,tvMobileNo,tvRoadName,tvHolding,tvProfession,tvHomeAdd,tvHomeArea;
        tvHomeName = view.findViewById(R.id.tv_dialogview_home_name);
        tvOwnerName = view.findViewById(R.id.tv_dialogview_owner_name);
        tvMobileNo = view.findViewById(R.id.tv_dialogview_mobile);
        tvRoadName = view.findViewById(R.id.tv_dialogview_road);
        tvHolding = view.findViewById(R.id.tv_dialogview_holding);
        tvProfession = view.findViewById(R.id.tv_dialogview_profession);
        tvHomeAdd = view.findViewById(R.id.tv_dialogview_home_address);
        tvHomeArea = view.findViewById(R.id.tv_dialogview_area);

        tvHomeName.setText("Home Name: "+homeName);
        tvOwnerName.setText("Owner Name: "+ownerName);
        tvMobileNo.setText("Mobile No: "+mobileNo);
        tvRoadName.setText("Road No: "+roadName);
        tvHolding.setText("Holding No: "+holdinNo);
        tvProfession.setText("Profession: "+profession);
        tvHomeAdd.setText("Home Address: "+homeAddress);
        tvHomeArea.setText("Home Area: "+homeArea);

        view.findViewById(R.id.button_upload_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +"+88"+mobileNo));
                startActivity(intent);

                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("owner_details_fragment");
                if(fragment != null) {
                    DialogFragment dialog = (DialogFragment) fragment;
                    dialog.dismiss();
                }
            }
        });

        return view;
    }
}
