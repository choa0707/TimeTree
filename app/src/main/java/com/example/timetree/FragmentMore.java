package com.example.timetree;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
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

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentMore extends Fragment {


    private View view;
    String title = "";
    GridView gridView;
    Button login_button;
    String search_id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        FirebaseAuth mAuth;
        // Inflate the layout for this fragment
        ArrayList<String> titles = new ArrayList<>();
        GroupListAdapter adapter = new GroupListAdapter();
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

                        if(search_id.length() > 0){
                            int l = -1, j = 0;
                            do{
                                l = search_id.indexOf("email", l + 1);
                                int e = l;
                                if (l != -1)
                                {
                                    e = search_id.indexOf('}',l);
                                    title += search_id.substring(l+6,e)+" ";

                                }
                            }while(l+1 < search_id.length() && l != -1);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "조회에러", Toast.LENGTH_LONG).show();
                        }

                        if (title.length() != 0)
                        {
                            adapter.addItem(new GroupListItem(title,0));
                            gridView.setAdapter(adapter);
                        }
                    }
                    adapter.addItem(new GroupListItem("추가하기",1));
                    gridView.setAdapter(adapter);
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
