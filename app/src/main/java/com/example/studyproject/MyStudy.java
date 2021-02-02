package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;


import androidx.appcompat.app.AppCompatActivity;

public class MyStudy extends AppCompatActivity {
    PopupMenu popupMenu;
    ImageButton ibt_popup, ibt_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudy);

        ibt_popup = (ImageButton)findViewById(R.id.imageButtonMenu);
        ibt_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.menu_mystudy, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId( )) {
                            case 0: //리스트 첫번째 클릭시 호출
                                Intent intentWeekly = new Intent(MyStudy.this, MainActivity.class); //수정
                                startActivity(intentWeekly);
                                break;
                            case 1: //리스트 두번째 클릭시 호출
                                break;
                            case 2: //리스트 세번째 클릭시 호출
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        ibt_info = (ImageButton)findViewById(R.id.imageButtonInfo);
        ibt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInfo = new Intent(MyStudy.this, MainActivity.class); //수정
                startActivity(intentInfo);
            }
        });

//        popupMenu = new PopupMenu(this, null, Gravity.END, 0, R.style.MyPopupMenu);
//        popupMenu.getMenu( ).add( 0, 0, 0, "리스트 첫번째" );
//        popupMenu.getMenu( ).add( 0, 1, 0, "리스트 두번째" );
//        popupMenu.getMenu( ).add( 0, 2, 0, "리스트 세번째" );
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId( )) {
//                    case 0: //리스트 첫번째 클릭시 호출
//                        break;
//                    case 1: //리스트 두번째 클릭시 호출
//                        break;
//                    case 2: //리스트 세번째 클릭시 호출
//                        break;
//                }
//
//                return false;
//            }
//        });

    }

}