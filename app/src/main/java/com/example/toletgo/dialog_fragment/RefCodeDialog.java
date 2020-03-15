package com.example.toletgo.dialog_fragment;

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

public class RefCodeDialog extends DialogFragment {

    private static String refCode;

    public static void setRefCode(String refCode){
        RefCodeDialog.refCode = ""+refCode;
    }

    public static RefCodeDialog newInstance(){
        return new RefCodeDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogview_reference_code_fragment,container,false);

        //setting reference code to view
        TextView tvRefCode = view.findViewById(R.id.tv_refer_code);
        tvRefCode.setText("Reference Code: "+refCode);

        //invite now button
        view.findViewById(R.id.button_invite_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("refercode_fragment");
                if(fragment != null) {
                    DialogFragment dialog = (DialogFragment) fragment;
                    dialog.dismiss();
                }
            }
        });

        //rate us button
        view.findViewById(R.id.button_rate_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //icon_close
        view.findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("refercode_fragment");
                if(fragment != null) {
                    DialogFragment dialog = (DialogFragment) fragment;
                    dialog.dismiss();
                }
            }
        });

        return view;
    }
}
