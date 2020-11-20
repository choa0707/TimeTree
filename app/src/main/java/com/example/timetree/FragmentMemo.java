package com.example.timetree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMemo extends Fragment {
    private View view;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memo, container, false);

        recyclerView = view.findViewById(R.id.memo_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MemoRecyclerAdapter memoRecyclerAdapter = new MemoRecyclerAdapter();
        MemoItem memoItem = new MemoItem("test", "est", "test");
        memoRecyclerAdapter.addItem(memoItem);
        memoRecyclerAdapter.addItem(memoItem);

        recyclerView.setAdapter(memoRecyclerAdapter);

        return view;
    }
}
