package com.example.toletgo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toletgo.data_model.YoutubeVideoModel;
import com.example.toletgo.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminYoutubeVideoAdapter extends RecyclerView.Adapter<AdminYoutubeVideoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<YoutubeVideoModel> videoLists;
    private AlertDialog dialogOptions,dialogEdit;

    public AdminYoutubeVideoAdapter(Context context, ArrayList<YoutubeVideoModel> videoLists) {
        this.context = context;
        this.videoLists = videoLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sampleview_youtube_video_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvLink.setText(videoLists.get(position).getVideoLink());
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionChooseDialog(holder.getAdapterPosition());
            }
        });
    }

    private void showOptionChooseDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Option");
        String options[] = {"Edit","Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==1){
                    DatabaseReference daRef = FirebaseDatabase.getInstance().getReference("YOUTUBE_VIDEOS");
                    daRef.child(videoLists.get(position).getLocation()).removeValue().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Delete Failed!", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogOptions.dismiss();
                }
                else if (which==0){
                    showEitedDialog(videoLists.get(position).getLocation(),videoLists.get(position).getVideoLink());
                    dialogOptions.dismiss();
                }
            }
        });

        dialogOptions = builder.create();
        dialogOptions.show();


    }

    private void showEitedDialog(final String location, String videoLink) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.sampleview_link_add,null,false);
        final EditText etLink = dialogView.findViewById(R.id.et_youtube_link);
        etLink.setText(videoLink);
        Button buttonCancel = dialogView.findViewById(R.id.button_sampleview_link_cancel);
        Button buttonAdd = dialogView.findViewById(R.id.button_sampleview_link_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = etLink.getText().toString();
                if(link.isEmpty()){
                    etLink.setError("No Link Added!");
                    return;
                }
                uploadLinkToServer(location,link);
                dialogEdit.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.setCancelable(false);
        dialogEdit = builder.create();
        dialogEdit.show();
    }

    private void uploadLinkToServer(String location,String link) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("YOUTUBE_VIDEOS");

        dataRef.child(location).child("videoLink").setValue(link).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to Upload!", Toast.LENGTH_LONG).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(context, "Failed to Upload!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLink;
        ImageView imageEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLink = itemView.findViewById(R.id.tv_sampleview_tv_youtube_link);
            imageEdit = itemView.findViewById(R.id.imageview_sampleview_youtube_videos_edit);

        }
    }
}
