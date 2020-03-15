package com.example.toletgo.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toletgo.R;
import com.example.toletgo.adapter.ImageSliderAdapter;
import com.example.toletgo.data_model.HomePostDetailsModel;
import com.example.toletgo.dialog_fragment.CustomDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostDetailsShowActivity extends AppCompatActivity {
    private RecyclerView recyclerViewImage;
    private ArrayList<String> imageUrlLists;
    private TextView tvRentDes,tvRentAddress,tvRentFloor,tvRentPrice;
    private Button buttonDetails;
    private DatabaseReference dataRef;
    HomePostDetailsModel detailsModel;
    private static ImageView imageFrame;
    private  ImageView imgLift,imgGenerator,imgSecurity,imgWifi,imgClean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_show);

        tvRentAddress = findViewById(R.id.tv_location_details);
        tvRentDes = findViewById(R.id.textView4);
        tvRentFloor = findViewById(R.id.textView13);
        tvRentPrice = findViewById(R.id.textView15);

        imgLift = findViewById(R.id.imageview_lift);
        imgWifi = findViewById(R.id.imageview_wifi);
        imgGenerator = findViewById(R.id.imageview_generatior);
        imgSecurity = findViewById(R.id.imageview_security);
        imgClean = findViewById(R.id.imageview_cleaning);

        //updating UI from Intent Extra
        tvRentDes.setText(getIntent().getStringExtra("rent_description"));
        tvRentAddress.setText(getIntent().getStringExtra("rent_address"));
        String floorText = ""+getIntent().getStringExtra("home_floor")+
                getSuffix(getIntent().getStringExtra("home_floor"))+" Floor";
        tvRentFloor.setText(floorText);
        tvRentPrice.setText(getIntent().getStringExtra("rent_price"));

        if (!getIntent().getBooleanExtra("wifi",true)){
            imgWifi.setVisibility(View.GONE);
        }
        if (!getIntent().getBooleanExtra("lift",true)){
            imgLift.setVisibility(View.GONE);
        }
        if (!getIntent().getBooleanExtra("security",true)){
            imgSecurity.setVisibility(View.GONE);
        }
        if (!getIntent().getBooleanExtra("clean",true)){
            imgClean.setVisibility(View.GONE);
        }
        if (!getIntent().getBooleanExtra("generator",true)){
            imgGenerator.setVisibility(View.GONE);
        }


        imageFrame = findViewById(R.id.imageview_post_details);

        buttonDetails = findViewById(R.id.button_details);

        dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
        dataRef.orderByChild("postLocation").equalTo(getIntent().getStringExtra("post_location"))
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    detailsModel = data.getValue(HomePostDetailsModel.class);
                }
                updateUI(detailsModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = CustomDialog.newInstance();
                CustomDialog.setDialogMode("details",getIntent().getStringExtra("ads_code"));
                dialog.show(getSupportFragmentManager(),"details_fragment");
            }
        });

        tvRentDes.setText(getIntent().getStringExtra("rent_description"));
        tvRentPrice.setText(getIntent().getStringExtra("rent_price"));

    }

    private String getSuffix(String home_floor) {
        int floor = Integer.parseInt(home_floor);
        if (floor==1) return "st";
        else if (floor==2) return "nd";
        else if (floor==3) return "rd";
        else return "th";
    }

    private void updateUI(HomePostDetailsModel model) {
        Picasso.get().load(model.getHomePhoto1()).into(imageFrame);
        initializeImageSlider(model.getHomePhoto1(),model.getHomePhoto2(),model.getHomePhoto3(),model.getHomePhoto4(),model.getHomePhoto5());
    }

    private void initializeImageSlider(String pic1,String pic2,String pic3,String pic4, String pic5){
        recyclerViewImage = findViewById(R.id.recyclerview_image_slider);
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        imageUrlLists = new ArrayList<>();

        imageUrlLists.add(pic1);
        imageUrlLists.add(pic2);
        imageUrlLists.add(pic3);
        imageUrlLists.add(pic4);
        imageUrlLists.add(pic5);

        recyclerViewImage.setAdapter(new ImageSliderAdapter(this,imageUrlLists));
    }

    public static void setImageIntoImageFrame(String url){
        Picasso.get().load(url).into(imageFrame);
    }

}
