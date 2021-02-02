package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyStudyWeekly extends AppCompatActivity {
    private Toolbar toolbar;
    PopupMenu popupMenu;
    View actionbarView;
    TextView tv_title;
    private ImageButton ibt_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudyweekly);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.commit();

        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowCustomEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false); //기본 제목을 없앰
        //actionBar.setDisplayHomeAsUpEnabled(false); //뒤로 가기

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        actionbarView = getLayoutInflater().inflate(R.layout.actionbar_mystudy, null);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(actionbarView, layout);

        ibt_popup = (ImageButton) actionbarView.findViewById(R.id.imageButtonMenu);
        ibt_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.menu_mystudy, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.weekly:
                                Intent intentWeekly = new Intent(MyStudyWeekly.this, MainActivity.class); //수정
                                startActivity(intentWeekly);
                                break;
                            case R.id.chat:
                                // d
                                break;
                            case R.id.board:
                                break;
                            case R.id.setting:
                                break;
                            case R.id.ranking:
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        tv_title = (TextView) actionbarView.findViewById(R.id.textViewTitle);
        tv_title.setText("내 스터디");


    }
}
