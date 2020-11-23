package com.example.timetree;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EventItem> eventItems;
    private ViewHolder viewHolder;

    public EventListAdapter(Context context, ArrayList<EventItem> eventItems) {
        this.context = context;
        this.eventItems = eventItems;
    }

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public Object getItem(int position) {
        return eventItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_event_list_item, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventEdit.class);
                intent.putExtra("event", (Serializable) eventItems.get(position));
                v.getContext().startActivity(intent);
            }
        });
        viewHolder.title.setText(eventItems.get(position).getTitle());
        viewHolder.time.setText(String.format("%02d",eventItems.get(position).getStart_hour())+":"+String.format("%02d",eventItems.get(position).getStart_minute())+"~"+
                String.format("%02d",eventItems.get(position).getEnd_hour())+":"+String.format("%02d",eventItems.get(position).getEnd_minute()));
        return convertView;
    }

    public class ViewHolder{
        TextView title, time;

        public ViewHolder(View convertView)
        {
            title = (TextView)convertView.findViewById(R.id.eve_title);
            time = (TextView)convertView.findViewById(R.id.eve_time);
        }
    }
}
