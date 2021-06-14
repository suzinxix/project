package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;


public class BoardFragment extends Fragment {
    TabLayout tabs;
    Toolbar toolbar_board;
    WeeklyFragment fragment_weekly;
    GalleryFragment fragment_gallery;
    String room_name;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기

        fragment_weekly = new WeeklyFragment();
        fragment_gallery = new GalleryFragment();


        Bundle bundle = getArguments();
        if(bundle != null) {
            room_name= bundle.getString("key__roomname");
        }

        Bundle bundle2 = new Bundle(); // 번들을 통해 값 전달
        bundle2.putString("key_roomname", room_name);//번들에 넘길 값 저장
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        WeeklyFragment weeklyFragment = new WeeklyFragment();//프래그먼트2 선언
        weeklyFragment.setArguments(bundle2);//번들을 프래그먼트2로 보낼 준비
        transaction.replace(R.id.containerTabs, weeklyFragment);
        transaction.commit();

        tabs = view.findViewById(R.id.tabs);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position == 0) {
                    Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                    bundle.putString("key_roomname", room_name);//번들에 넘길 값 저장
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    WeeklyFragment weeklyFragment = new WeeklyFragment();//프래그먼트2 선언
                    weeklyFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                    transaction.replace(R.id.containerTabs, weeklyFragment);
                    transaction.commit();
                }
                else if(position == 1) {
                    Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                    bundle.putString("roomname_btog", room_name);//번들에 넘길 값 저장
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    GalleryFragment galleryFragment = new GalleryFragment();//프래그먼트2 선언
                    galleryFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                    transaction.replace(R.id.containerTabs, galleryFragment);
                    transaction.commit();
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//toolbar의 back키 눌렀을 때 동작
            ((HomeActivity) getActivity()).replaceFragment(MyStudyFragment.newInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}