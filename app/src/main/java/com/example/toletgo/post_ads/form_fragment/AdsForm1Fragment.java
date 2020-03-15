package com.example.toletgo.post_ads.form_fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.toletgo.R;
import com.example.toletgo.background_threads.ImageCompressThread;
import com.example.toletgo.interfaces.ImageCompressTaskListener;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdsForm1Fragment extends Fragment implements View.OnClickListener {

    private final int PICK_IMAGE=1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private static final int REQUEST_PICK_PHOTO = 101;
    private ZoomInImageView[] zoomImage = new ZoomInImageView[5];
    private ArrayList<Uri> imageList = new ArrayList<Uri>();
    private Bundle mBundle,getBundle;
    private int i=0;

    private TextInputEditText etRentFrom,etArea,etPostalCode;
    private AppCompatSpinner divisionSpinner,floorSpinner;
    private String[] floorsList = {"Select Floor","1","2","3","4","5","6","7","8","9","10"};
    private String[] divisionNames = {"Select Division","DHAKA","CHOTTOGRAM","RANGPUR","SYHLET","KHULNA","BARISHAL","RAJSHAHI"};

    //create a single thread pool to our image compression class.
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    private ImageCompressThread imageCompressThread;
    private Context mContext;
    private ProgressDialog pd;

    boolean userMode;

    public AdsForm1Fragment(Context mContext,boolean userMode) {
        // Required empty public constructor
        this.mContext = mContext;
        this.userMode = userMode;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getBundle = getArguments();
        if (getBundle!=null){
            mBundle = getBundle;
        }else mBundle = new Bundle();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads_from1, container, false);

        //Button Choose Images
        view.findViewById(R.id.button3).setOnClickListener(this);
        //Button Next
        view.findViewById(R.id.button_go_next).setOnClickListener(this);
        //Button Go Back

        etArea = view.findViewById(R.id.et_post_area);
        etRentFrom = view.findViewById(R.id.et_post_tittle);
        etPostalCode = view.findViewById(R.id.et_postal_code);

        zoomImage[0] = view.findViewById(R.id.zoom_image_1);
        zoomImage[1] = view.findViewById(R.id.zoom_image_2);
        zoomImage[2] = view.findViewById(R.id.zoom_image_3);
        zoomImage[3] = view.findViewById(R.id.zoom_image_4);
        zoomImage[4] = view.findViewById(R.id.zoom_image_5);

        divisionSpinner = view.findViewById(R.id.spinner_division);
        ArrayAdapter<String> divisonAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,divisionNames);
        divisionSpinner.setAdapter(divisonAdapter);

        floorSpinner = view.findViewById(R.id.spinner_select_floor);
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_sampleview,R.id.spinner_sampleview_textview,floorsList);
        floorSpinner.setAdapter(floorAdapter);

        try {

            etRentFrom.setText(mBundle.getString("post_tittle"));
            etPostalCode.setText(mBundle.getString("post_postal_code"));
            etArea.setText(mBundle.getString("post_area"));

            imageList.add(Uri.parse(mBundle.getString("post_pic_1")));
            imageList.add(Uri.parse(mBundle.getString("post_pic_2")));
            imageList.add(Uri.parse(mBundle.getString("post_pic_3")));
            imageList.add(Uri.parse(mBundle.getString("post_pic_4")));
            imageList.add(Uri.parse(mBundle.getString("post_pic_5")));

            Picasso.get().load(Uri.parse(mBundle.getString("post_pic_1"))).resize(50,50).into(zoomImage[0]);
            Picasso.get().load(Uri.parse(mBundle.getString("post_pic_2"))).resize(50,50).into(zoomImage[1]);
            Picasso.get().load(Uri.parse(mBundle.getString("post_pic_3"))).resize(50,50).into(zoomImage[2]);
            Picasso.get().load(Uri.parse(mBundle.getString("post_pic_4"))).resize(50,50).into(zoomImage[3]);
            Picasso.get().load(Uri.parse(mBundle.getString("post_pic_5"))).resize(50,50).into(zoomImage[4]);

            for(int i=0; i<floorsList.length; i++){
                if(mBundle.getString("post_floor").equals(floorsList[i])){
                    floorSpinner.setSelection(i);
                    break;
                }
            }
            for(int i=0; i<divisionNames.length; i++){
                if(mBundle.getString("post_division").equals(divisionNames[i])){
                    divisionSpinner.setSelection(i);
                    break;
                }
            }


        }catch (Exception e){

        }

        return view;
    }

    private void replaceAdsForm2() {
        AdsForm2Fragment adsForm2Fragment = new AdsForm2Fragment();
        adsForm2Fragment.setArguments(mBundle);        //storing filled data
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_post_ads,adsForm2Fragment," ");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button3){
            requestPermission();
            //startImageChoosingOption();
        }
        if (v.getId() == R.id.button_go_next) {

            String rentFrom,postalcode,area,division,floor;
            rentFrom = etRentFrom.getText().toString();
            postalcode = etPostalCode.getText().toString();
            area = etArea.getText().toString();
            floor = floorsList[floorSpinner.getSelectedItemPosition()];
            division = divisionNames[divisionSpinner.getSelectedItemPosition()];

            if(rentFrom.isEmpty()){
                etRentFrom.setError("Please write post tittle.");
                return;
            }
            if(postalcode.isEmpty()){
                etPostalCode.setError("Invalid Postal Code.");
                return;
            }
            if(area.isEmpty()){
                etArea.setError("Please write area details.");
                return;
            }
            if(divisionSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Please select division.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(floorSpinner.getSelectedItemPosition()==0){
                Toast.makeText(getActivity(), "Please select floor.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (imageList.size()<5){
                Toast.makeText(getActivity(), "Please add photos!", Toast.LENGTH_SHORT).show();
                return;
            }

            mBundle.putString("post_tittle",rentFrom);
            mBundle.putString("post_postal_code",postalcode);
            mBundle.putString("post_area",area);
            mBundle.putString("post_division",division);
            mBundle.putString("post_floor",floor);
            mBundle.putString("post_pic_1",imageList.get(0).toString());
            mBundle.putString("post_pic_2",imageList.get(1).toString());
            mBundle.putString("post_pic_3",imageList.get(2).toString());
            mBundle.putString("post_pic_4",imageList.get(3).toString());
            mBundle.putString("post_pic_5",imageList.get(4).toString());
            mBundle.putBoolean("userMode",userMode);

            replaceAdsForm2();
        }
    }

    private void startImageChoosingOption() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            showProgressbar();
            if (data.getClipData() != null) {
                int countClipData = data.getClipData().getItemCount();
                if (countClipData == 5) {
                    int currentImageSelect = 0;
                    while (currentImageSelect < countClipData) {
                        Uri image;
                        image = data.getClipData().getItemAt(currentImageSelect).getUri();

                        imageList.add(image);

                        Picasso.get().load(image).resize(50,50).centerCrop().into(zoomImage[currentImageSelect]);
                        currentImageSelect++;
                    }
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Photos is added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please select exact 5 photos.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "Please Select Photos!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void requestPermission () {

        if (PackageManager.PERMISSION_GRANTED !=
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                //Yeah! I want both block to do the same thing, you can write your own logic, but this works for me.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            }
        } else {
            //Permission Granted, lets go pick photo
            /*Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_PHOTO);*/
            startImageChoosingOption();
        }

    }

    private void showProgressbar(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please wait...");
        pd.show();
    }

}
