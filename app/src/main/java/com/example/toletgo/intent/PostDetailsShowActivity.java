package com.example.toletgo.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toletgo.R;
import com.example.toletgo.adapter.ImageSliderAdapter;
import com.example.toletgo.data_model.HomePostDetailsModel;
import com.example.toletgo.fragments.HomePostShowFragment;
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
    private TextView tvRentDes,tvRentAdd,tvRentFloor,tvRentPrice,tvRentFront;
    private Button buttonCall,buttonDetails;
    private DatabaseReference dataRef;
    HomePostDetailsModel detailsModel;
    private static ImageView imageFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_show);

        tvRentAdd = findViewById(R.id.tv_location_details);
        tvRentDes = findViewById(R.id.textView4);
        tvRentFloor = findViewById(R.id.textView13);
        tvRentPrice = findViewById(R.id.textView15);
        tvRentFront = findViewById(R.id.textView14);
        imageFrame = findViewById(R.id.imageview_post_details);

        buttonCall = findViewById(R.id.button_call_now);
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

        tvRentDes.setText(getIntent().getStringExtra("rent_description"));
        tvRentPrice.setText(getIntent().getStringExtra("rent_price"));

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
