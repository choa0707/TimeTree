package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventListActivity extends Activity {

    public ArrayList<EventItem> eventItems;
    public  EventListAdapter eventListAdapter;
    public ListView listView;
    TextView mon, day;

    int caly, calm,cald;
    int date;
    int sdate, edate;
    String s_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        mon = findViewById(R.id.event_list_month);
        day = findViewById(R.id.event_list_day);

        listView = (ListView)findViewById(R.id.event_list_view);
        eventItems = new ArrayList<>();

        Intent intent = getIntent();
        caly = intent.getExtras().getInt("caly");
        calm = intent.getExtras().getInt("calm");
        cald = intent.getExtras().getInt("cald");

        mon.setText(caly+"년 "+calm+"월");
        day.setText(cald+"일");

        date = Integer.parseInt(changeDate(caly, calm, cald));

        Toast.makeText(getApplicationContext(), s_date, Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        String uid = FirebaseAuth.getInstance().getUid();
        if (!MyGlobals.getInstance().getgroupKey().equals(""))
        {
            myRef = database.getReference().child("Groups").child(MyGlobals.getInstance().getgroupKey()).child("events");
        }
        else {myRef = database.getReference().child("users").child(uid).child("events");}

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot i : dataSnapshot.getChildren()) {

                    EventItem eventItem = i.getValue(EventItem.class);

                    sdate = Integer.parseInt(changeDate(eventItem.getStart_year(), eventItem.getStart_month(), eventItem.getStart_day()));
                    edate = Integer.parseInt(changeDate(eventItem.getEnd_year(), eventItem.getEnd_month(), eventItem.getEnd_day()));

                    if (date >= sdate && date <= edate)
                    {
                        eventItems.add(eventItem);
                    }

                }
                eventListAdapter = new EventListAdapter(getApplicationContext(), eventItems);
                Log.i("testt", ""+eventListAdapter.getCount());
                listView.setAdapter(eventListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("read", "Failed to read value.", error.toException());
            }
        });







    }
    String changeDate(int y, int m, int d)
    {
        String ret = "";
        if ( m < 10)
        {
            if (d < 10)
            {
                ret = y+"0"+m+"0"+d;
            }
            else
            {
                ret = y+"0"+m+d;
            }
        }
        else
        {
            if (d < 10)
            {
                ret = y+""+m+"0"+d;
            }
            else
            {
                ret = ""+y+""+m+d;
            }
        }
        return ret;
    }
}