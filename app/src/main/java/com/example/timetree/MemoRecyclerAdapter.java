package com.example.timetree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.ItemViewHolder> {
    int position;
    ArrayList<MemoItem> arrayList = new ArrayList<>();

    Context context;
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void addItem(MemoItem memoItem)
    {
        arrayList.add(memoItem);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView memo_title, memo_date, memo_detail;
        MaterialButton edit, delete;
        ItemViewHolder(View itemView){
            super(itemView);
            memo_title = itemView.findViewById(R.id.memo_title);
            memo_date = itemView.findViewById(R.id.memo_date);
            memo_detail = itemView.findViewById(R.id.memo_detail);
            delete = itemView.findViewById(R.id.memo_delete);
            edit = itemView.findViewById(R.id.memo_edit);
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("그룹 탈퇴").setMessage("그룹을 탈퇴 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            notifyItemChanged(position);
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });
        }
        void onBind(MemoItem memoItem){
            memo_title.setText("est");
        }

    }
}
