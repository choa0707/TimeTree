package com.example.timetree.group;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.timetree.MyGlobals;
import com.example.timetree.OnItemClick;
import com.example.timetree.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter{
    private OnItemClick mCallback;
    GroupListAdapter(OnItemClick lisntener) {this.mCallback = lisntener;}

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
                StorageReference storageReference = storage.getReference();
                storageReference.child("images/"+items.get(i).getImage_url()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context).load(uri).into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "이미지 로드 실패", Toast.LENGTH_LONG).show();
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, groupListItem.getTitle()+"그룹 일정을 볼러옵니다.", Toast.LENGTH_LONG).show();
                        MyGlobals.getInstance().setgroupKey(groupListItem.getKey());
                        mCallback.onClick();
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("그룹 탈퇴").setMessage("그룹을 탈퇴 하시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref1 = database.getReference().child("Groups").child(groupListItem.getKey()).child("members");

                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot i : snapshot.getChildren())
                                        {
                                            GroupUserEmail groupUserEmail = i.getValue(GroupUserEmail.class);
                                            if (groupUserEmail.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                                            {
                                                ref1.child(i.getKey()).removeValue();
                                                if (i.getKey().equals(MyGlobals.getInstance().getgroupKey()))
                                                {
                                                    MyGlobals.getInstance().setgroupKey("");
                                                }
                                                Toast.makeText(context, "그룹을 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });




                            }
                        });
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                        return true;
                    }
                });
            }
            gridlayout.getLayoutParams().height = 500;

        return view;
    }
    public void addItem(GroupListItem item)
    {
        items.add(item);
    }
    public void clear() {
        items.clear();
    }
}
class GroupUserEmail{
    String email;
    public GroupUserEmail(){

    }
    public String getEmail() {
        return email;
    }
}