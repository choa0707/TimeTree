package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Spinner;

public class EventAdd extends AppCompatActivity {
    DatePicker datePicker;
    EventItem eventItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);




    }
}
//