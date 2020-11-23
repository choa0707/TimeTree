package com.example.timetree;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.ItemViewHolder> {
    int position;
    Context context;
    ArrayList<FeedItem> arrayList = new ArrayList<>();


    @NonNull
    @Override
    public FeedRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_feed, parent,false);
        context = parent.getContext();

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.ItemViewHolder holder, int position) {
        this.position = position;
        holder.onBind(arrayList.get(position));
    }
    public void addItem(FeedItem feedItem) {arrayList.add(feedItem);}
    public void sort()
    {
        Collections.sort(arrayList, new Comparator<FeedItem>() {
            @Override
            public int compare(FeedItem o1, FeedItem o2) {
                Log.i("sort", Long.toString(o1.getComp())+'/'+Long.toString(o2.getComp()));
                return Long.toString(o1.getComp()).compareTo(Long.toString(o2.getComp()));
            }
        });
    }
    public void clear()
    {
        arrayList.clear();
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView s_date, e_date, title;
        ImageView img;
        ItemViewHolder(View itemView){
            super(itemView);
            s_date = itemView.findViewById(R.id.feed_s_date);
            e_date = itemView.findViewById(R.id.feed_e_date);
            title = itemView.findViewById(R.id.feed_title);
            img = itemView.findViewById(R.id.feed_img);

        }
        void onBind(FeedItem feedItem){
            title.setText(feedItem.getTitle());
            s_date.setText(feedItem.getS_date());
            e_date.setText(feedItem.getE_date());
            img.setImageBitmap(feedItem.getImg());
        }

    }
}
