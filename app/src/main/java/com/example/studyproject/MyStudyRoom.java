package com.example.studyproject;

import android.os.Bundle;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//임시 파일
public class MyStudyRoom extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton ibt_info;
    MyStudyFragment fragment_study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudyroom);

        //fragment_study = new MyStudyFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.container_mystudy, fragment_study).commit();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_mystudy, MyStudyFragment.newInstance()).commit();
    }

    public void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.container_mystudy, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}
