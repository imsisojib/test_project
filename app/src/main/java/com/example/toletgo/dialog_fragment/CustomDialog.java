package com.example.toletgo.dialog_fragment;

import android.content.Intent;
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
import com.example.toletgo.post_ads.PostAdsActivity;

public class CustomDialog extends DialogFragment {

    private static String dialogMode;
    private static String postID;

    public static void setDialogMode(String dialogMode,String postID) {
        CustomDialog.dialogMode  = dialogMode;
        CustomDialog.postID = postID;
    }

    public static CustomDialog newInstance() {

        return new CustomDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if(dialogMode.equals("details")){
            view = inflater.inflate(R.layout.dialogview_details,container,false);
            TextView tvId;
            tvId = view.findViewById(R.id.tv_dialog_details_post_id);
            tvId.setText("Ads ID: "+postID);
            view.findViewById(R.id.imvageview_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("details_fragment");
                    if(fragment != null) {
                        DialogFragment dialog = (DialogFragment) fragment;
                        dialog.dismiss();
                    }
                }
            });
        }
        else if(dialogMode.equals("investor")){
            view = inflater.inflate(R.layout.dialogview_investor,container,false);
            view.findViewById(R.id.imageivew_closse).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("investor_fragment");
                    if(fragment != null) {
                        DialogFragment dialog = (DialogFragment) fragment;
                        dialog.dismiss();
                    }
                }
            });
        }
        else if (dialogMode.equals("earning")){
            view = inflater.inflate(R.layout.dialogview_category,container,false);
            view.findViewById(R.id.card_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PostAdsActivity.class);
                    intent.putExtra("userMode","earning");
                    startActivity(intent);

                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("earning_fragment");
                    if(fragment != null) {
                        DialogFragment dialog = (DialogFragment) fragment;
                        dialog.dismiss();
                    }

                }
            });
        }

        return view;

    }
}
