package com.example.timetree.group;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.timetree.MainActivity;
import com.example.timetree.OnItemClick;
import com.example.timetree.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter {
    private OnItemClick mCallback;
    GroupListAdapter(OnItemClick listener)
    {
        this.mCallback = listener;
    }


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
                ImageView imageView = view.findViewById(R.id.group_image);
                FirebaseStorage storage = FirebaseStorage.getInstance("gs://daltree-d7366.appspot.com/");
                StorageReference storageRef = storage.getReference();
                Log.d("DB", items.get(i).getImage_url());
                storageRef.child("images/"+items.get(i).getImage_url()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //이미지 로드 성공시

                        Glide.with(context)
                                .load(uri)
                                .into(imageView);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //이미지 로드 실패시
                        Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                    }
                });
                //
                imageView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, groupListItem.getTitle()+"그룹 일정을 불러옵니다.", Toast.LENGTH_LONG).show();
                        mCallback.onClick();

                    }
                });
                //
            }
            gridlayout.getLayoutParams().height = 500;

        return view;
    }
    public void addItem(GroupListItem item)
    {
        items.add(item);
    }
}
