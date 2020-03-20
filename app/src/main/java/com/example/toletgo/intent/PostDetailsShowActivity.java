package com.example.toletgo.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toletgo.adapter.ImageSliderUserAdapter;
import com.example.toletgo.R;
import com.example.toletgo.data_model.HomePostDetailsModel;
import com.example.toletgo.dialog_fragment.CustomDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostDetailsShowActivity extends AppCompatActivity implements View.OnTouchListener {
    private RecyclerView recyclerViewImage;
    private ArrayList<String> imageUrlLists;
    private TextView tvRentDes,tvRentAddress,tvRentFloor,tvRentPrice;
    private Button buttonDetails;
    private DatabaseReference dataRef;
    HomePostDetailsModel detailsModel;
    private static ImageView imageFrame;
    private  ImageView imgLift,imgGenerator,imgSecurity,imgWifi,imgClean;


    //scrollable imageview
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;


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
        imageFrame.setOnTouchListener(this);

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

        recyclerViewImage.setAdapter(new ImageSliderUserAdapter(this,imageUrlLists));
    }

    public static void setImageIntoImageFrame(String url){
        Picasso.get().load(url).into(imageFrame);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }


    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
    }
}
