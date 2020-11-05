package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Map;

public class EventAdd extends AppCompatActivity {
    int y = 0, m = 0, d = 0;
    Spinner category, color, alarm;
    DatePicker datePicker;
    EventItem eventItem = new EventItem();
    LinearLayout start_date, end_date;
    EditText phone, location, title;
    TextView front_date, back_date;
    ImageButton regist_button, back_button;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String today = date.toString();


    private DatePickerDialog.OnDateSetListener listener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            eventItem.setStart_date(year, monthOfYear+1, dayOfMonth);
        }
    };
    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            eventItem.setEnd_date(year, monthOfYear+1, dayOfMonth);
        }
    };
    private TimePickerDialog.OnTimeSetListener listner3 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eventItem.setStart_time(hourOfDay, minute);
            front_date.setText(eventItem.getStart_year()+"년 "+eventItem.getStart_month()+"월 "+eventItem.getStart_day()+"일 / " +eventItem.getStart_hour()+"시 "+eventItem.getStart_minute()+"분");
        }
    };
    private TimePickerDialog.OnTimeSetListener listner4 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eventItem.setEnd_time(hourOfDay, minute);
            back_date.setText(eventItem.getEnd_year()+"년 "+eventItem.getEnd_month()+"월 "+eventItem.getEnd_day()+"일 / " +eventItem.getEnd_hour()+"시 "+eventItem.getEnd_minute()+"분");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        phone = findViewById(R.id.Phone_Text);
        location = findViewById(R.id.Gps_Text);
        regist_button = findViewById(R.id.regist_button);
        back_button = findViewById(R.id.back_button);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        front_date = findViewById(R.id.frontDate);
        back_date = findViewById(R.id.BackDate);
        category = findViewById(R.id.Category);
        color = findViewById(R.id.Color_Btn);
        alarm = findViewById(R.id.alarm);
        title = findViewById(R.id.add_title);


        alarm.setPrompt("알람");
        alarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventItem.setAlarm(position);
                Toast.makeText(getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventItem.setCategory(0);
            }
        });

        color.setPrompt("색깔");
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventItem.setCategory(position);
                Toast.makeText(getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventItem.setCategory(0);
            }
        });
        category.setPrompt("카테고리");
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventItem.setCategory(position);
                Toast.makeText(getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventItem.setCategory(0);
            }
        });
        start_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(EventAdd.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listner3, 0, 0, false);
                timePickerDialog.show();
                DatePickerDialog dialog = new DatePickerDialog(EventAdd.this, listener1, 2020, 9, 17);
                dialog.show();

            }
        });
        end_date.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(EventAdd.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listner4,0, 0, false);
                timePickerDialog.show();
                DatePickerDialog dialog = new DatePickerDialog(EventAdd.this, listener2, 2020, 9, 17);
                dialog.show();

            }
        });
        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        regist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventItem.setPhone(phone.getText().toString());
                eventItem.setLocation(location.getText().toString());
                eventItem.setTitle(title.getText().toString());

                long now = System.currentTimeMillis();
                Date date = new Date(now);

                String eid = date.toString();
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (MyGlobals.getInstance().getgroupKey().equals(""))
                {
                    database.getReference().child("users").child(uid).child("events").child(eid).setValue(eventItem);
                }
                else
                {
                    database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("events").child(eid).setValue(eventItem);
                }
                Toast.makeText(getApplicationContext(), "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
