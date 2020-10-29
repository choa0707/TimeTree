package com.example.timetree;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter{

    //OnItemClick mCallback; -> onItemClick을 콜백시켜준다. OnItemClick은 인터페이스스

   ArrayList<GroupListItem> items = new ArrayList<>();
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

            GroupListItem groupListItem = items.get(i);
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.group_list_item, viewGroup,false);
            }
            LinearLayout gridlayout = view.findViewById(R.id.group_layout);
            if (groupListItem.getAdd() == 1)
            {
                TextView title = view.findViewById(R.id.group_title);
                ImageView imageView = view.findViewById(R.id.group_image);
                imageView.setImageResource(R.drawable.ic_group_plus);
                title.setText("그룹 추가하기");
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,AddGroupActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
            else
            {
                TextView title = view.findViewById(R.id.group_title);
                title.setText(groupListItem.getTitle());

                //20201929 , 이미지 뷰를 셋팅하고 FirebbaseStrage를 가져온다.

            }
            gridlayout.getLayoutParams().height = 500;

        return view;
    }
    public void addItem(GroupListItem item)
    {
        items.add(item);
    }
}
