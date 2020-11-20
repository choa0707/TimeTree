package com.example.timetree;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class FragmentMemo extends Fragment {
    private View view;
    RecyclerView recyclerView;
    FloatingActionButton memo_fb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memo, container, false);
        memo_fb = view.findViewById(R.id.memo_fb);
        recyclerView = view.findViewById(R.id.memo_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        memo_fb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MemoAddActivity.class);
                startActivity(intent);

            }
        });
        MemoRecyclerAdapter memoRecyclerAdapter = new MemoRecyclerAdapter();

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
            if (!MyGlobals.getInstance().getgroupKey().equals(""))
            {
                myRef = database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("memo");
            }
            else {myRef = database.getReference().child("users").child(uid).child("memo");}


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    memoRecyclerAdapter.clear();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        MemoItem memoItem;
                        Log.i("memo", i.toString());

                        memoItem = i.getValue(MemoItem.class);
                        memoItem.setKey(i.getKey());
                        Log.i("memo", memoItem.getTitle());
                        memoRecyclerAdapter.addItem(memoItem);
                    }
                    recyclerView.setAdapter(memoRecyclerAdapter);

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("read", "Failed to read value.", error.toException());
                }
            });
        }



        return view;
    }
}
