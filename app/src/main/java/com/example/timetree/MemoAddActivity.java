package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MemoAddActivity extends AppCompatActivity {
    String date="";
    EditText title, detail;
    DatePicker datePicker;
    ImageButton back, register;
    MemoItem memoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);
        title = (EditText)findViewById(R.id.memo_add_title);
        detail = findViewById(R.id.memo_add_detail);
        datePicker = findViewById(R.id.memo_add_datepicker);
        back = findViewById(R.id.memo_back_button);
        register = findViewById(R.id.memo_regist_button);

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                date = ""+datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
                memoItem = new MemoItem(title.getText().toString(), date, detail.getText().toString());

                String uid = FirebaseAuth.getInstance().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef;
                    if (!MyGlobals.getInstance().getgroupKey().equals(""))
                    {
                        myRef = database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("memo");
                    }
                    else {myRef = database.getReference().child("users").child(uid).child("memo");}
                    DatabaseReference newRef = myRef.push();
                    newRef.setValue(memoItem);
                Toast.makeText(getApplicationContext(), "메모가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}