package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;


public class BoardFragment extends Fragment {
    TabLayout tabs;
    Toolbar toolbar_board;
    WeeklyFragmentTmp fragment_weekly;
    GalleryFragment fragment_gallery;

    //Fragment 변경위해
    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container, false);

        // 툴바 추가
        toolbar_board = (Toolbar) view.findViewById(R.id.toolbarBoard);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_board);

        fragment_weekly = new WeeklyFragmentTmp();
        fragment_gallery = new GalleryFragment();

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.containerTabs, fragment_weekly).commit();

        tabs = view.findViewById(R.id.tabs);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragment_weekly;
                else if(position == 1)
                    selected = fragment_gallery;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerTabs, selected).commit(); //오류-클릭,replace ok. 탭바가 사라짐
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
