package com.example.timetree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentCalender fragmentCalender = new FragmentCalender();
    private FragmentMemo fragmentMemo = new FragmentMemo();
    private FragmentMore fragmentMore = new FragmentMore();
    private FragmentFeed fragmentFeed = new FragmentFeed();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main_Frame, fragmentCalender).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.memo:
                    transaction.replace(R.id.Main_Frame, fragmentMemo).commitAllowingStateLoss();
                    break;
                case R.id.more:
                    transaction.replace(R.id.Main_Frame, fragmentMore).commitAllowingStateLoss();
                    break;
                case R.id.feed:
                    transaction.replace(R.id.Main_Frame, fragmentFeed).commitAllowingStateLoss();
                    break;
                case R.id.calender:
                    transaction.replace(R.id.Main_Frame, fragmentCalender).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}