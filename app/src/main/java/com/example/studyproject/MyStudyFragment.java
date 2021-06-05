package com.example.studyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MyStudyFragment extends Fragment {
    Toolbar toolbar_mystudy;
    TextView Roomname;
    Context context;
    String sendData, receiveData;

    //Fragment 변경위한 함수
    public static MyStudyFragment newInstance() {
        return new MyStudyFragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

        //메시지 송수신에 필요한 객체 초기화
        receiveData = "";
        sendData = "프래그먼트2에서 보낸 데이터입니다.";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true); // 메뉴가 있음을 알림
        View view = inflater.inflate(R.layout.fragment_mystudy, container, false);
        Roomname = view.findViewById(R.id.study_title);

        //Intent intent = new Intent(getActivity(), StudyRoom.class);
        //startActivity(intent);
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.studyView, study).commit();

        // 툴바 추가
        toolbar_mystudy = (Toolbar) view.findViewById(R.id.toolbarMystudy);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_mystudy);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // 스터디 이름 데이터 받기
        if(getArguments() != null) {
            receiveData = getArguments().getString("Roomname");
            Roomname.setText(receiveData);
        }


       

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
            case android.R.id.home:
                ((HomeActivity)getActivity()).replaceFragment(HomeFragment.newInstance()); //Fragment 변경
        }
        return super.onOptionsItemSelected(item);
    }

}
