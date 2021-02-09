package com.example.studyproject;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment{
    private Toolbar toolbar_home;
    View actionbarView;
    Button bt_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 툴바 추가
        toolbar_home = (Toolbar) view.findViewById(R.id.toolbarHome);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_home);

        // 로그아웃 구현
        bt_logout = (Button) view.findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_mystudy, menu) ;
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weekly:
                // Do onlick on menu action here
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//        //툴바(액션바) 추가
//        toolbar_home = (Toolbar)view.findViewById(R.id.toolbarHome);
//        setSupportActionBar(toolbar_home);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false); //기본 제목을 없앰
//        actionBar.setDisplayHomeAsUpEnabled(false); //뒤로 가기
//
//        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        actionbarView = getLayoutInflater().inflate(R.layout.actionbar_home, null);
//
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(actionbarView, layout);

}
