package com.example.timetree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentCalender fragmentCalender = new FragmentCalender();
    private FragmentMemo fragmentMemo = new FragmentMemo();
    private FragmentMore fragmentMore = new FragmentMore();
    private FragmentFeed fragmentFeed = new FragmentFeed();
    private DrawerLayout drawerLayout;
    private TextView nav_name;
    private TextView nav_email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_more_horiz_24);
        auth = FirebaseAuth.getInstance(); //인스턴스를 받아옴

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);

        nav_name = (TextView)view.findViewById(R.id.nav_name);
        nav_email = (TextView)view.findViewById(R.id.nav_email);

        if (auth.getCurrentUser()!=null)
        {
            nav_name.setText("DalTree");
            nav_email.setText(auth.getCurrentUser().getEmail());

        } else
        {
            nav_name.setText("로그인을 해주세요");
            nav_email.setText("");

            //로그인이 되어있으면 navi_menu를 로그아웃이 되어있으면 navi_manu를 / 메인엑티비티에서도 만들어준다.
        }


            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    // 아이템이 선택되었을때 실행되는 코딩

                    switch(menuItem.getItemId())
                    {
                        case R.id.account:
                            Toast.makeText(getApplicationContext(), "계정", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.setting:
                            Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.logout:
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                            onResume();
                            finish();
                            break;

                            //로그아웃 버튼을 눌렀는데 네비게이션바 상단에 변화가 없습니다.
                    }

                    return true;
                }
            });





        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main_Frame, fragmentCalender).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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