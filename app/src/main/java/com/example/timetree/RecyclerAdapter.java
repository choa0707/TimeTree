package com.example.timetree;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    int position;
    ArrayList<String> arrayList = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemViewHolder holder, int position) {
        this.position = position;
        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    void addItem(String email)
    {
        Log.d("recyler", "success add "+email);
        arrayList.add(email);
    }
    String getItem(int i)
    {
        return arrayList.get(i);
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView text_email;
        Button delete;
        ItemViewHolder(View itemView){
            super(itemView);
            text_email = itemView.findViewById(R.id.text_email);
            delete = itemView.findViewById(R.id.user_delete);
            delete.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    arrayList.remove(position);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                }
            });
        }
        void onBind(String email){
            text_email.setText(email);
        }

    }
}
