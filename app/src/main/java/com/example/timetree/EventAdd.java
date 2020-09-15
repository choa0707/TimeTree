package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Map;

public class EventAdd extends AppCompatActivity {
    DatePicker datePicker;
    EventItem eventItem;
    EditText phone, location;
    ImageButton regist_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        eventItem = new EventItem();
        phone = findViewById(R.id.Phone_Text);
        location = findViewById(R.id.Gps_Text);
        regist_button = findViewById(R.id.regist_button);

        regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventItem.setPhone(phone.getText().toString());
                eventItem.setLocation(location.getText().toString());

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                String eid = date.toString();
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("users").child(uid).child("events").child(eid).setValue(eventItem);
                Toast.makeText(getApplicationContext(), "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
//