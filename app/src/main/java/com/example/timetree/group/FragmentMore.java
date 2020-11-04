package com.example.timetree.group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.timetree.LoginActivity;
import com.example.timetree.MainActivity;
import com.example.timetree.OnItemClick;
import com.example.timetree.R;
import com.example.timetree.group.GroupListAdapter;
import com.example.timetree.group.GroupListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentMore extends Fragment implements OnItemClick{
    private View view;
    GridView gridView;
    String title, search_id, image;

    Button login_button;
    Button logout_button;
    GroupListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        FirebaseAuth mAuth;
        // Inflate the layout for this fragment
        adapter = new GroupListAdapter(this);
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null)
        {
            view = inflater.inflate(R.layout.fragment_more, container, false);
            login_button = view.findViewById(R.id.more_login_button);

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_more2, container, false);

            gridView = view.findViewById(R.id.group_grid_view);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("Groups");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        search_id = i.getValue().toString();
                        title = search_id.substring(search_id.indexOf("name")+5,search_id.indexOf("}", search_id.indexOf("name")));
                        image = search_id.substring(search_id.indexOf("image")+6,search_id.indexOf(",", search_id.indexOf("image")));
                        if (search_id.length() > 0){
                            int l = -1, j = 0;
                            do {
                                l = search_id.indexOf("email", l+1);
                                int e = 1;
                                if (l != -1)
                                {
                                    e = search_id.indexOf('}', l);
                                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(search_id.substring(l+6, e)))
                                    {
                                        adapter.addItem(new GroupListItem(title,0,image));
                                    }
                                }
                            }
                            while(l+1 < search_id.length() && l != -1);
                        }
                    }
                    adapter.addItem(new GroupListItem("추가하기",1,""));
                    gridView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            });






        }
        return view;
    }
    @Override
    public void onClick() {
        ((MainActivity)getActivity()).replaceFragment();
    }
}
