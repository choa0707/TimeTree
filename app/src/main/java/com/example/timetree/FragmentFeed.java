package com.example.timetree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentFeed extends Fragment {
    private View view;
    RecyclerView recyclerView;
    ArrayList<FeedItem> feedItems = new ArrayList<>();
    int color;
    int s_y, s_d,s_m, s_hour,s_minute;
    int e_y, e_m, e_d, e_hour,e_minute;
    String title;
    Bitmap bit;
    FeedRecyclerAdapter feedRecyclerAdapter = new FeedRecyclerAdapter();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = view.findViewById(R.id.feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        String uid = FirebaseAuth.getInstance().getUid();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        if (!MyGlobals.getInstance().getgroupKey().equals("")) {
            myRef = database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("events");
        } else {
            myRef = database.getReference().child("users").child(uid).child("events");
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedRecyclerAdapter.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot i : dataSnapshot.getChildren()) {

                    EventItem eventItem = i.getValue(EventItem.class);
                    color = eventItem.getColor();
                    s_y = eventItem.getStart_year();
                    s_m = eventItem.getStart_month();
                    s_d = eventItem.getStart_day();
                    e_y = eventItem.getEnd_year();
                    e_m = eventItem.getEnd_month();
                    e_d = eventItem.getEnd_day();
                    e_hour = eventItem.getEnd_hour();
                    e_minute = eventItem.getEnd_minute();
                    s_hour = eventItem.getStart_hour();
                    s_minute = eventItem.getStart_minute();
                    title = eventItem.getTitle();

                    if (color == 0)  bit =BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_rad);
                    else if (color==1) bit = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_orange);
                    else if (color==2) bit = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_yellow);
                    else if (color==3) bit = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_green);
                    bit = Bitmap.createScaledBitmap(bit, 50,50,true);


                    String compare = s_y+String.format("%02d",s_m)+String.format("%02d",s_d)+String.format("%02d",s_hour)+String.format("%02d",s_minute);
                    FeedItem feedItem = new FeedItem(""+s_y+"년 "+String.format("%02d",s_m)+"월 "+String.format("%02d",s_d)+"일 "+String.format("%02d",s_hour)+"시 "+String.format("%02d",s_minute)+"분" , ""+e_y+"년 "+String.format("%02d",e_m)+"월 "+String.format("%02d",e_d)+"일 "+String.format("%02d",e_hour)+"시 "+String.format("%02d",e_minute)+"분",title, bit, Long.parseLong(compare));

                    feedRecyclerAdapter.addItem(feedItem);

                    //  Log.d("read", eventItem.getTitle());
                }
                feedRecyclerAdapter.sort();

                recyclerView.setAdapter(feedRecyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("read", "Failed to read value.", error.toException());
            }
        });



        //20201123 반복적으로 불러오는것을 어떻게 해야할까요



        return view;
    }
}
