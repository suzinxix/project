package com.example.studyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


public class MyStudyFragment extends Fragment {
    Toolbar toolbar_mystudy;
    TextView Roomname;
    String room_name;
    private DatabaseReference mDatabaseRef;

    //Fragment 변경위한 함수
    public static MyStudyFragment newInstance() {
        return new MyStudyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true); // 메뉴가 있음을 알림
        View view = inflater.inflate(R.layout.fragment_mystudy, container, false);
        Roomname = view.findViewById(R.id.study_title);

        Bundle bundle = getArguments();
        if (bundle != null) {
            room_name = bundle.getString("key_roomname");
        }

        // 툴바 추가
        toolbar_mystudy = (Toolbar) view.findViewById(R.id.toolbarMystudy);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_mystudy);

        ImageView iv_graphic = (ImageView)view.findViewById(R.id.imageViewGraphic);

        Picasso.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/fir-test-1-35648.appspot.com/o/level%2Flevel1%20(1).png?alt=media&token=d80a0960-47be-435a-9d90-7511a804120e")
                .resize(1250, 1550)
                .into(iv_graphic);

        Button btn_weekly = (Button) view.findViewById(R.id.buttonWeekly);
        btn_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("key__roomname", room_name);//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                BoardFragment boardFragment = new BoardFragment();//프래그먼트2 선언
                boardFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.container, boardFragment);
                transaction.commit();
                //((HomeActivity)getActivity()).replaceFragment(BoardFragment.newInstance()); //Fragment 변경
            }
        });

        return view;
    }

}
