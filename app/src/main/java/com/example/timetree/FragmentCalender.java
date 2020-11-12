package com.example.timetree;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentCalender extends Fragment {
    private View view;
    int s_y, s_m, s_d, e_y,e_m,e_d;
    FloatingActionButton fab;
    ArrayList<EventItem> eventItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        String eventcolor = bundle.getString("eventcolor");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        List<EventDay> events = new ArrayList<>();
        String uid = FirebaseAuth.getInstance().getUid();
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        if (uid != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef;
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
                        eventItems.add(i.getValue(EventItem.class));

                        EventItem eventItem = i.getValue(EventItem.class);
                        s_y = eventItem.getStart_year();
                        s_m = eventItem.getStart_month();
                        s_d = eventItem.getStart_day();
                        e_y = eventItem.getEnd_year();
                        e_m = eventItem.getEnd_month();
                        e_d = eventItem.getEnd_day();
                        Log.d("test", s_y + "/" + s_m + "/" + s_d + "/" + e_y + "/" + e_m + "/" + e_d);


                        //20201110 아이콘 색상 지정 수정하기

                        for (int j = s_d; j <= e_d; j++) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(s_y, s_m - 1, j);


                            if (eventcolor.equals("빨강")){
                                events.add(new EventDay(calendar1,R.drawable.ic_baseline_star_rate_rad, Color.parseColor("#228B22")));
                                calendarView.setEvents(events);
                            }else if(eventcolor.equals("주황")){
                                events.add(new EventDay(calendar1,R.drawable.ic_baseline_star_rate_orange, Color.parseColor("#228B22")));
                                calendarView.setEvents(events);
                            }else if(eventcolor.equals("노랑")){
                                events.add(new EventDay(calendar1,R.drawable.ic_baseline_star_rate_yellow, Color.parseColor("#228B22")));
                                calendarView.setEvents(events);
                            }else if(eventcolor.equals("초록")){
                                events.add(new EventDay(calendar1, R.drawable.ic_baseline_star_rate_green, Color.parseColor("#228B22")));
                                calendarView.setEvents(events);
                            }


                            Log.d("test", s_y + "/" + s_m + "/" + j);
                        }

                        //  Log.d("read", eventItem.getTitle());
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("read", "Failed to read value.", error.toException());
                }
            });
        }
        fab = view.findViewById(R.id.fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EventAdd.class);
                startActivity(intent);
            }
        });



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                int year = clickedDayCalendar.get(Calendar.YEAR);
                int month = clickedDayCalendar.get(Calendar.MONTH);
                int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);

                Intent intent = new Intent(getContext(), EventListActivity.class);
                intent.putExtra("caly", year);
                intent.putExtra("calm", month+1);
                intent.putExtra("cald", day);
                startActivity(intent);
            }
        });
        return view;
    }


}
