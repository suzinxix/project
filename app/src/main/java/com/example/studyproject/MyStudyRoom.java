package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.FragmentActivity;
 //미완

public class MyStudyRoom extends FragmentActivity {
    PopupMenu popupMenu;
    ImageButton ibt_popup, ibt_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudy);



        ibt_info = (ImageButton) findViewById(R.id.imageButtonInfo);
        ibt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(MyStudyRoom.this, MainActivity.class); //수정
                startActivity(intentInfo);
            }
        });
    }
}
