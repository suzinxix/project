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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment{
    private UserStudyRoomViewModel userStudyRoomViewModel;
    private Toolbar toolbar_home;
    Button bt_temp;

    //Fragment 변경위한 함수
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*
        // 리사이클러뷰 프래그먼트
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.roomrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        UserStudyRoomAdapter adapter = new UserStudyRoomAdapter();
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        userStudyRoomViewModel = ViewModelProviders.of(this).get(UserStudyRoomViewModel.class);
        userStudyRoomViewModel.getAllUserStudyRooms().observe(getViewLifecycleOwner(), new Observer<List<UserStudyRoom>>() {
            @Override
            public void onChanged(List<UserStudyRoom> userStudyRooms) {
                adapter.setUserStudyRooms(userStudyRooms);
            }
        });
        */


        // 툴바 추가
        toolbar_home = (Toolbar) view.findViewById(R.id.toolbarHome);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_home);

        ((AppCompatActivity)getActivity()).setTitle("");
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_notification_icon);

        // 임시 버튼 HomeFragment->MyStudyFragment
        bt_temp = (Button) view.findViewById(R.id.bt_temp);
        bt_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).replaceFragment(MyStudyFragment.newInstance()); //Fragment 변경
            }
        });
        return view;
    }

}
