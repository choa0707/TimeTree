package com.example.timetree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddGroupActivity extends AppCompatActivity {
    String add_id;
    String get_id;
    int success = 0;
    RecyclerAdapter recyclerAdapter;
    EditText editText;
    Button searchButton, groupRegist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.user_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(recyclerAdapter);


        editText = findViewById(R.id.user_add_edittext);
        searchButton = findViewById(R.id.user_search_button);
        groupRegist = findViewById(R.id.group_regist);

        groupRegist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                get_id = editText.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                success = 0;
                DatabaseReference myRef = database.getReference().child("users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (DataSnapshot i : dataSnapshot.getChildren()) {
                            add_id = i.getValue().toString();

                            int s = add_id.indexOf("email");
                            int e = s;
                            if (s != -1)
                            {
                                for (int j = s; j < add_id.length(); j++)
                                {
                                    if (add_id.charAt(j) == ',')
                                    {
                                        e = j;
                                        break;
                                    }
                                }
                                add_id = add_id.substring(s+6,e);
                                Log.d("dbtest",add_id);
                                if (get_id.equals(add_id))
                                {
                                    success = 1;
                                }
                            }
                            //eventItems.add(i.getValue(EventItem.class));
                        }
                        if (success == 0)
                        {
                            Toast.makeText(getApplicationContext(), "해당 사용자가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            recyclerAdapter.addItem(get_id);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}