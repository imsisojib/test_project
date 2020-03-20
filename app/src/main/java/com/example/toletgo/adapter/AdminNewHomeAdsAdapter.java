package com.example.toletgo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostShowModel;
import com.example.toletgo.data_model.UserEarningModel;
import com.example.toletgo.intent.AdminPostDetailsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminNewHomeAdsAdapter extends RecyclerView.Adapter<AdminNewHomeAdsAdapter.ViewHolder> {

    private ArrayList<HomePostShowModel> dataSets;
    private Context context;
    DatabaseReference dataRefUser;
    boolean aproveFlag = false;

    public AdminNewHomeAdsAdapter(Context context,ArrayList<HomePostShowModel> dataSets) {
        this.dataSets = dataSets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sampleview_admin_home_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        try{
            Glide.with(context).load(dataSets.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
            //Picasso.get().load(dataSet.get(position).getHomePhoto1()).into(holder.imageViewPostPhoto);
            holder.progressBar.setVisibility(View.GONE);
        }catch (Exception e){
            //unable to show photo later
        }
        holder.tvRentPrice.setText(dataSets.get(position).getHomePrice()+" TK/Month");
        holder.tvRentDes.setText("Flat Rent: "+dataSets.get(position).getHomeBed()+" Bed "+dataSets.get(position).getHomeBath()+" Bath "+
                dataSets.get(position).getHomeKitchen()+" Kitchen "+
                dataSets.get(position).getHomeBalcony()+" Balcony");
        holder.cardPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminPostDetailsActivity.class);
                intent.putExtra("rent_price",dataSets.get(position).getHomePrice()+" TK/Month");
                intent.putExtra("rent_description","Rent Home "+dataSets.get(position).getHomeBed()
                        +" Bed "+dataSets.get(position).getHomeBath()+" Bath "+
                        dataSets.get(position).getHomeKitchen()+" Kitchen "+
                        dataSets.get(position).getHomeBalcony()+" Balcony");
                intent.putExtra("post_location",dataSets.get(position).getPostLocation());
                intent.putExtra("ads_code",dataSets.get(position).getPostID());

                //opportunities
                intent.putExtra("wifi",dataSets.get(position).isHomeWifi());
                intent.putExtra("lift",dataSets.get(position).isHomeLift());
                intent.putExtra("security",dataSets.get(position).isHomeSecurity());
                intent.putExtra("clean",dataSets.get(position).isHomeCleaning());
                intent.putExtra("generator",dataSets.get(position).isHomeGenerator());

                intent.putExtra("home_floor",dataSets.get(position).getHomeFloor());
                intent.putExtra("rent_address",dataSets.get(position).getHomeArea()+", "+dataSets.get(position).getHomeDivision());
                context.startActivity(intent);
            }
        });
        holder.tvAddress.setText(dataSets.get(position).getHomeArea()+","+dataSets.get(position).getHomeDivision());
        holder.buttonSold.setText(context.getResources().getString(R.string.approve));
        holder.buttonSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataRefUser = FirebaseDatabase.getInstance().getReference("USERS");
                dataRefUser.orderByChild("userUID").equalTo(dataSets.get(position)
                        .getPostOwner()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            UserEarningModel model = data.getValue(UserEarningModel.class);
                            DatabaseReference refIncPost = FirebaseDatabase.getInstance().getReference("USERS");
                            refIncPost.child(model.getLocationUrl())
                                    .child("totalPost").setValue(String.valueOf(Integer.valueOf(model.getTotalPost())+1))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                           approvePost(position);
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Please Try Again...", Toast.LENGTH_LONG).show();
                                }
                            });
                            break;
                        }
                        dataRefUser.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
                dataRef.child(dataSets.get(position).getPostLocation()).removeValue();
            }
        });
        holder.tvPostId.setText("Ads Code: "+dataSets.get(position).getPostID());

    }

    private void approvePost(int position) {

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("POST_HOME");
        dataRef.child(dataSets.get(position).getPostLocation()).child("postLive").setValue(true);

    }

    @Override
    public int getItemCount() {
        return dataSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRentFrom,tvRentDes,tvRentPrice,tvAddress,tvPostId;
        ImageView imageViewPostPhoto;
        CardView cardPost;
        ProgressBar progressBar;
        Button buttonDelete,buttonSold;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardPost = itemView.findViewById(R.id.linearlayout_sampleview_post_home);
            tvRentDes = itemView.findViewById(R.id.home_description);
            tvRentFrom = itemView.findViewById(R.id.tv_home_rent_from);
            tvRentPrice = itemView.findViewById(R.id.tv_home_rent_price);
            imageViewPostPhoto = itemView.findViewById(R.id.zoominimageview_item_photo);
            progressBar = itemView.findViewById(R.id.sampleview_home_post_progressbar);
            tvAddress = itemView.findViewById(R.id.tv_sampleview_address);

            buttonSold = itemView.findViewById(R.id.button_sold);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            tvPostId = itemView.findViewById(R.id.textView14);
        }
    }
}
