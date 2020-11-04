package com.example.timetree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(), 2000);
    }
    public class splashHandler implements Runnable {
        @Override
        public void run() {
            auth = FirebaseAuth.getInstance();
            Intent intent;
            if (auth.getCurrentUser() == null)
            {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            else
            {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}