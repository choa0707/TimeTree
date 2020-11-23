package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventEdit extends AppCompatActivity {
    int y = 0, m = 0, d = 0;
    Spinner category, color, alarm;
    DatePicker datePicker;
    LinearLayout start_date, end_date;
    EditText phone, location, title;
    TextView front_date, back_date;
    Button edit_button,  delete_button;
    ImageButton back_button;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String today = date.toString();
    EventItem eventItem;



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

            //20201104 - 날짜 선택 제한을 두는 방법

            front_date.setText(eventItem.getStart_year()+"년 "+eventItem.getStart_month()+"월 "+eventItem.getStart_day()+"일  " +eventItem.getStart_hour()+"시 "+eventItem.getStart_minute()+"분");
        }
    };
    private TimePickerDialog.OnTimeSetListener listner4 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eventItem.setEnd_time(hourOfDay, minute);
            back_date.setText(eventItem.getEnd_year()+"년 "+eventItem.getEnd_month()+"월 "+eventItem.getEnd_day()+"일  " +eventItem.getEnd_hour()+"시 "+eventItem.getEnd_minute()+"분");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        Intent intent = getIntent();
        EventItem eventItem = (EventItem) intent.getExtras().getSerializable("event");

        phone = findViewById(R.id.e_Phone_Text);
        location = findViewById(R.id.e_Gps_Text);
        edit_button = findViewById(R.id.e_event_edit);
        delete_button = findViewById(R.id.e_event_delete);
        back_button = findViewById(R.id.e_back_button);
        start_date = findViewById(R.id.e_start_date);
        end_date = findViewById(R.id.e_end_date);
        front_date = findViewById(R.id.e_frontDate);
        back_date = findViewById(R.id.e_BackDate);
        category = findViewById(R.id.e_Category);
        color = findViewById(R.id.e_Color_Btn);
        alarm = findViewById(R.id.e_alarm);
        title = findViewById(R.id.e_add_title);


        front_date.setText(eventItem.getStart_year()+"년 "+ String.format("%02d",eventItem.getStart_month()) +"월 "+ String.format("%02d",eventItem.getStart_day()) + "일  " + String.format("%02d",eventItem.getStart_hour()) +"시 "+ String.format("%02d",eventItem.getStart_minute()) +"분");
        back_date.setText(eventItem.getEnd_year() +"년 "+ String.format("%02d",eventItem.getEnd_month()) +"월 "+ String.format("%02d",eventItem.getEnd_day()) + "일  " + String.format("%02d",eventItem.getEnd_hour()) +"시 "+ String.format("%02d",eventItem.getEnd_minute()) +"분");



        alarm.setPrompt("알람");
        alarm.setSelection(eventItem.getAlarm());
        alarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventItem.setAlarm(position);
                Toast.makeText(getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventItem.setAlarm(0);
            }
        });

        color.setPrompt("색깔");
        color.setSelection(eventItem.getColor());
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventItem.setColor(position);
                Toast.makeText(getApplicationContext(), ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventItem.setColor(0);
            }
        });
        phone.setText(eventItem.getPhone());
        location.setText(eventItem.getLocation());
        title.setText(eventItem.getTitle());


        category.setPrompt("카테고리");
        category.setSelection(eventItem.getCategory());
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


                TimePickerDialog timePickerDialog= new TimePickerDialog(EventEdit.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listner3, eventItem.getStart_hour(), eventItem.getStart_minute(), false);
                timePickerDialog.show();
                DatePickerDialog dialog = new DatePickerDialog(EventEdit.this, listener1, eventItem.getStart_year(), eventItem.getStart_month(), eventItem.getStart_day());
                dialog.show();



            }
        });
        end_date.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog= new TimePickerDialog(EventEdit.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listner4,eventItem.getEnd_hour(),eventItem.getEnd_minute(), false);
                timePickerDialog.show();
                DatePickerDialog dialog = new DatePickerDialog(EventEdit.this, listener2,  eventItem.getEnd_year(), eventItem.getEnd_month(), eventItem.getEnd_day());
                dialog.show();

            }
        });
        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventItem.setPhone(phone.getText().toString());
                eventItem.setLocation(location.getText().toString());
                eventItem.setTitle(title.getText().toString());

                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (MyGlobals.getInstance().getgroupKey().equals(""))
                {
                    database.getReference().child("users").child(uid).child("events").child(eventItem.getKey()).setValue(eventItem);
                }
                else
                {
                    database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("events").child(eventItem.getKey()).setValue(eventItem);
                }
                Toast.makeText(getApplicationContext(), "등록 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (MyGlobals.getInstance().getgroupKey().equals(""))
                {
                    database.getReference().child("users").child(uid).child("events").child(eventItem.getKey()).removeValue();
                }
                else
                {
                    database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("events").child(eventItem.getKey()).removeValue();
                }
                Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}