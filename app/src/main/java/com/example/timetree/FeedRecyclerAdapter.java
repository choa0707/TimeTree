package com.example.timetree;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.ItemViewHolder> {
    int position;
    ArrayList<FeedItem> arrayList = new ArrayList<>();


    @NonNull
    @Override
    public FeedRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_feed, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.ItemViewHolder holder, int position) {
        this.position = position;
        holder.onBind(arrayList.get(position));
    }
    public void addItem(FeedItem feedItem) {arrayList.add(feedItem);}

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView date, title;
        ImageView img;
        ItemViewHolder(View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.feed_date);
            title = itemView.findViewById(R.id.feed_title);
            img = itemView.findViewById(R.id.feed_img);

//            delete.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View view) {
//                    arrayList.remove(position);
//                    notifyDataSetChanged();
//                    notifyItemChanged(position);
//                }
//            });
        }
        void onBind(FeedItem feedItem){
            title.setText(feedItem.getTitle());
            date.setText(feedItem.getDate());
            img.setImageBitmap(feedItem.getImg());
        }

    }
}
