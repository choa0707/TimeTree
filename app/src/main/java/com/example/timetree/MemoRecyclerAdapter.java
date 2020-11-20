package com.example.timetree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.ItemViewHolder>{
    int position;
    Context context;
    ArrayList<MemoItem> arrayList = new ArrayList<>();


    @NonNull
    @Override
    public MemoRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_memo, parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoRecyclerAdapter.ItemViewHolder holder, int position) {
        this.position = position;
        holder.onBind(arrayList.get(position));
    }
    public void addItem(MemoItem memoItem) {arrayList.add(memoItem);}
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void clear()
    {
        arrayList.clear();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView memo_title, memo_date, memo_detail;
        Button edit, delete;
        ItemViewHolder(View itemView){
            super(itemView);
            memo_title = itemView.findViewById(R.id.memo_title);
            memo_date = itemView.findViewById(R.id.memo_date);
            memo_detail = itemView.findViewById(R.id.memo_detail);
            delete = itemView.findViewById(R.id.memo_delete);
            edit = itemView.findViewById(R.id.memo_edit);
            edit.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, MemoEditActivity.class);
                    intent.putExtra("title", arrayList.get(position).getTitle());
                    intent.putExtra("date", arrayList.get(position).getDate());
                    intent.putExtra("detail", arrayList.get(position).getDetail());
                    intent.putExtra("key", arrayList.get(position).getKey());
                    context.startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("메모 삭제").setMessage("메모를 삭제 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String uid = FirebaseAuth.getInstance().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            if (!MyGlobals.getInstance().getgroupKey().equals(""))
                            {
                                database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("memo").child(arrayList.get(position).getKey()).removeValue();
                            }
                            else {database.getReference().child("users").child(uid).child("memo").child(arrayList.get(position).getKey()).removeValue();}
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            notifyItemChanged(position);
                        }
                    });
                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });
        }
        void onBind(MemoItem memoItem){
            memo_title.setText(memoItem.getTitle());
            memo_date.setText(memoItem.getDate());
            memo_detail.setText(memoItem.getDetail());
        }

    }

}