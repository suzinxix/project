package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRoomFragment extends Fragment {
    String roomname;
    public MyRoomFragment(){}
    public RecyclerView recyclerView;
    public ArrayList<UserStudyRoomDB> dataholder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myroom, container, false);

        recyclerView = v.findViewById(R.id.myroom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(new MyRoomFragmentAdapter(dataholder));
        /*

        UserStudyRoomDB ob = new UserStudyRoomDB("아오", 0, 0);
        dataholder.add(ob);
        recyclerView.setAdapter(new MyRoomFragmentAdapter(dataholder));*/

        return v;
    }
}
