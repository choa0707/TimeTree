package com.example.timetree;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class FragmentMore extends Fragment {
    private View view;
    Button login_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
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
        }



        return view;
    }
}
