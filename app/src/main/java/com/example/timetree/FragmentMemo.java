package com.example.timetree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMemo extends Fragment {
    private View view;
    GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_memo, container, false);

        gridView = view.findViewById(R.id.gridView);
        GridListAdapter adapter = new GridListAdapter();

        adapter.addItem(new GridViewListItem("점식식사", "2019-03-23"));
        adapter.addItem(new GridViewListItem("피시방", "2019-03-30"));
        adapter.addItem(new GridViewListItem("점식식사", "2019-03-23"));
        adapter.addItem(new GridViewListItem("피시방", "2019-03-30"));
        adapter.addItem(new GridViewListItem("점식식사", "2019-03-23"));
        adapter.addItem(new GridViewListItem("피시방", "2019-03-30"));
        gridView.setAdapter(adapter);

        return view;
    }
}
