package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MyStudyFragment extends Fragment {
    Toolbar toolbar_mystudy;

    //Fragment 변경위한 함수
    public static MyStudyFragment newInstance() {
        return new MyStudyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true); // 메뉴가 있음을 알림
        View view = inflater.inflate(R.layout.fragment_mystudy, container, false);

        // 툴바 추가
        toolbar_mystudy = (Toolbar) view.findViewById(R.id.toolbarMystudy);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_mystudy);

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
                ((HomeActivity)getActivity()).replaceFragment(BoardFragment.newInstance()); //Fragment 변경
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
        return super.onOptionsItemSelected(item);
    }


}
