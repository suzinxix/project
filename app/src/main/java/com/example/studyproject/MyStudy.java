package com.example.studyproject;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MyStudy extends Fragment {
    PopupMenu popupMenu;
    private androidx.appcompat.widget.Toolbar toolbar;
    ImageButton ibt_popup, ibt_info;
    MyStudyRoom studyRoom;
    TextView textViewtextView;
    View viewToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mystudy, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.include_toolbar);
        studyRoom = (MyStudyRoom) getActivity();
        studyRoom.setSupportActionBar(toolbar);
        ActionBar actionBar = ((MyStudyRoom) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.fragment_mystudy, null);
//        ibt_popup = (ImageButton)viewToolbar.findViewById(R.id.imageButtonMenu);
//        ibt_popup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupMenu = new PopupMenu(getApplicationContext(), v);
//                getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId( )) {
//                            case R.id.weekly:
//                                Intent intentWeekly = new Intent(WeeklyPlan.this, MainActivity.class); //수정
//                                startActivity(intentWeekly);
//                                break;
//                            case R.id.chat:
//                                // d
//                                break;
//                            case R.id.board:
//                                break;
//                            case R.id.setting:
//                                break;
//                            case R.id.ranking:
//                                break;
//                        }
//
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

        ibt_info = (ImageButton)viewToolbar.findViewById(R.id.imageButtonInfo);
        ibt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(studyRoom, "hello world!", Toast.LENGTH_SHORT).show();
                textViewtextView.setText("Toolbar 버튼 클릭");
            }
        });

        textViewtextView = (TextView)view.findViewById(R.id.textViewtextView);
        Button buttonbutton = (Button)view.findViewById(R.id.buttonbutton);
        buttonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewtextView.setText("Fragement 버튼 클릭");
            }
        });

//        actionBar.setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        return view;
    }
}
