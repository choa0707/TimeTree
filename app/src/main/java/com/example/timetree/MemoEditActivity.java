package com.example.timetree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemoEditActivity extends AppCompatActivity {
    String txt_date="";
    String date;
    String txt_title, txt_detail, txt_key;
    EditText title, detail;
    DatePicker datePicker;
    ImageButton back, register;
    MemoItem memoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        intent = getIntent();
        txt_title = intent.getStringExtra("title");
        txt_date = intent.getStringExtra("date");
        txt_detail = intent.getStringExtra("detail");
        txt_key = intent.getStringExtra("key");

        setContentView(R.layout.activity_memo_add);
        title = (EditText)findViewById(R.id.memo_add_title);
        detail = findViewById(R.id.memo_add_detail);
        datePicker = findViewById(R.id.memo_add_datepicker);
        back = findViewById(R.id.memo_back_button);
        register = findViewById(R.id.memo_regist_button);

        title.setHint(txt_title);
        detail.setHint(txt_detail);


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
                        myRef = database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("memo").child(txt_key);
                    }
                    else {myRef = database.getReference().child("users").child(uid).child("memo").child(txt_key);}
                myRef.setValue(memoItem);
                Toast.makeText(getApplicationContext(), "메모가 수정 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}