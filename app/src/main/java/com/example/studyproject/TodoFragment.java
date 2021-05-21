package com.example.studyproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoFragment extends Fragment {
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Todo> todo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_todolist, container, false);
        Context context = v.getContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_todo);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        prepareData();
    }
    private void prepareData() {

        adapter.addItem(new Todo(1,"7시에 인나기"));
        adapter.addItem(new Todo(2,"아침에 일어나서 물마시기"));
        adapter.addItem(new Todo(3,"식물에 물주기"));
    }
}
