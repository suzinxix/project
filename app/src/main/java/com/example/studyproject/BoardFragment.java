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
    WeeklyFragment fragmentweekly;
    GalleryFragment fragmentgallery;

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
        setHasOptionsMenu(true);

        fragmentweekly = new WeeklyFragment();
        fragmentgallery = new GalleryFragment();

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentweekly).commit();

        tabs = view.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("주차별 목표"));
        tabs.addTab(tabs.newTab().setText("갤러리"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragmentweekly;
                else if(position == 1)
                    selected = fragmentgallery;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
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
