package com.example.timetree;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentMore extends Fragment {
    private View view;
    GridView gridView;
    String title, search_id;
    //String image;
    Button login_button;
    Button logout_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        FirebaseAuth mAuth;
        // Inflate the layout for this fragment

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
                        title = "";
                        search_id = i.getValue().toString();
                        if (search_id.length() > 0){ //20201029
                            int l = -1, j = 0;
                            do {
                                l = search_id.indexOf("email", l+1);
                                int e = 1;
                                if (l != -1)
                                {
                                    e = search_id.indexOf('}', l);
                                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(search_id.substring(l+6, e))) //자신이 속한 그룹과 정보가 같을 때
                                    {

                                    }
                                }
                            }
                            while(l+1 < search_id.length() && l != -1);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            GroupListAdapter adapter = new GroupListAdapter();

            adapter.addItem(new GroupListItem("추가하기",1,""));


            gridView.setAdapter(adapter);
        }
        return view;
    }
}
