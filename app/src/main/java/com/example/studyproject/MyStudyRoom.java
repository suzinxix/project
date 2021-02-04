package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//임시 파일
public class MyStudyRoom extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton ibt_info;
    MyStudy fragment_study;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudy);

        fragment_study = new MyStudy();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_mystudy, fragment_study).commit();

        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //기본 제목을 없앰
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //뒤로 가기

        View viewToolbar = getLayoutInflater().inflate(R.layout.actionbar_home, null);
        actionBar.setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//
//            ibt_info = (ImageButton) findViewById(R.id.imageButtonNews);
//            ibt_info.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intentInfo = new Intent(MyStudyRoom.this, MainActivity.class); //수정
//                    startActivity(intentInfo);
//                }
//            });



    }
}
