package com.example.timetree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.ArrayList;

public class FragmentFeed extends Fragment {
    private View view;
    RecyclerView recyclerView;
    ArrayList<EventItem> eventItems = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Bitmap bit_1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_rad);
        bit_1 = Bitmap.createScaledBitmap(bit_1, 50,50,true);
        Bitmap bit_2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_orange);
        bit_2 = Bitmap.createScaledBitmap(bit_2, 50,50,true);
        Bitmap bit_3 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_green);
        bit_3 = Bitmap.createScaledBitmap(bit_3, 50,50,true);
        Bitmap bit_4 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.feed_yellow);
        bit_4 = Bitmap.createScaledBitmap(bit_4, 50,50,true);



        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = view.findViewById(R.id.feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FeedRecyclerAdapter feedRecyclerAdapter = new FeedRecyclerAdapter();
        FeedItem feedItem = new FeedItem("2020년 11월 23일 10시 10분", "제목", bit_1);
        feedRecyclerAdapter.addItem(feedItem);
        recyclerView.setAdapter(feedRecyclerAdapter);

        //20201123 반복적으로 불러오는것을 어떻게 해야할까요



        return view;
    }
}
