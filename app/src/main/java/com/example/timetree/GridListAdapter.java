package com.example.timetree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter{

    ArrayList<GridViewListItem> items = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        GridViewListItem gridViewListItem = items.get(i);

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.memo_list_item, viewGroup,false);
        }
        TextView title = view.findViewById(R.id.memo_title);
        TextView date = view.findViewById(R.id.memo_date);

        title.setText(gridViewListItem.getTitle());
        date.setText(gridViewListItem.getDate());
        return view;
    }
    public void addItem(GridViewListItem item)
    {
        items.add(item);
    }
}
