package com.example.timetree;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class FragmentMore extends Fragment {
    private View view;
    Button login_button;
    Button logout_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        FirebaseAuth mAuth;
        // Inflate the layout for this fragment

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null)
        {
            view = inflater.inflate(R.layout.fragment_more, container, false);
            login_button = view.findViewById(R.id.more_login_button);

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_more2, container, false);
            logout_button = view.findViewById(R.id.more_logout_button);
            logout_button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getContext(), "로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }



        return view;
    }
}
